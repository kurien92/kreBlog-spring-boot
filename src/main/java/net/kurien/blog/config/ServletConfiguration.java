package net.kurien.blog.config;

import com.google.gson.Gson;
import net.kurien.blog.interceptor.TemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebMvc
public class ServletConfiguration implements WebMvcConfigurer {
    private final TemplateInterceptor templateInterceptor;

    @Autowired
    public ServletConfiguration(TemplateInterceptor templateInterceptor) {
        this.templateInterceptor = templateInterceptor;
    }
    @Bean
    public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter(){
        MappingJackson2XmlHttpMessageConverter converter = new MappingJackson2XmlHttpMessageConverter();
        converter.setPrettyPrint(true);
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        MappingJackson2XmlHttpMessageConverter jacksonXmlMessageConverter = new MappingJackson2XmlHttpMessageConverter();
        jacksonXmlMessageConverter.setPrettyPrint(true);

        converters.add(gsonHttpMessageConverter);
        converters.add(jacksonXmlMessageConverter);
    }

    @Bean
    public HttpMessageConverters customConverters() {
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        messageConverters.add(gsonHttpMessageConverter);

        return new HttpMessageConverters(true, messageConverters);
    }

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);

        return converter;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePatterns = new ArrayList<>();

        excludePatterns.add("/comment/**");
        excludePatterns.add("/visitor/**");
        excludePatterns.add("/autosave/**");
        excludePatterns.add("/admin/post/deleteFile/*");
        excludePatterns.add("/admin/content/deleteFile/*");
        excludePatterns.add("/account/sendCertKey");
        excludePatterns.add("/account/checkCertKey");
        excludePatterns.add("/account/signupCheck");
        excludePatterns.add("/account/checkId");
        excludePatterns.add("/account/checkEmail");
        excludePatterns.add("/account/checkNickname");
        excludePatterns.add("/account/sendFindCertKey");
        excludePatterns.add("/account/checkFindCertKey");
        excludePatterns.add("/account/findCheck");
        excludePatterns.add("/rss");
        excludePatterns.add("/sitemap");
        excludePatterns.add("/css/**");
        excludePatterns.add("/js/**");
        excludePatterns.add("/img/**");
        excludePatterns.add("/font/**");
        excludePatterns.add("/file/**");
        excludePatterns.add("/admin/file/**");
        excludePatterns.add("/.well-known/**");
        excludePatterns.add("/robots.txt");
        excludePatterns.add("/ads.txt");

        registry.addInterceptor(templateInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(excludePatterns);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("css/**")
            .addResourceLocations("classpath:/static/css/")
            .setCachePeriod(3600);

        registry
            .addResourceHandler("js/**")
            .addResourceLocations("classpath:/static/js/")
            .setCachePeriod(3600);

        registry
            .addResourceHandler("img/**")
            .addResourceLocations("classpath:/static/img/")
            .setCachePeriod(3600);

        registry
            .addResourceHandler("robots.txt")
            .addResourceLocations("classpath:/static/main/robots.txt")
            .setCachePeriod(3600);

        registry
            .addResourceHandler("ads.txt")
            .addResourceLocations("classpath:/static/main/ads.txt")
            .setCachePeriod(3600);
    }
}
