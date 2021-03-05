package net.kurien.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class TilesConfiguration {
    @Bean
    public ViewResolver tilesViewResolver() {
        TilesViewResolver viewResolver = new TilesViewResolver();

        viewResolver.setViewClass(TilesView.class);
        viewResolver.setOrder(1);

        return viewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configurer = new TilesConfigurer();

        configurer.setDefinitions("classpath:/tiles.xml");
        configurer.setCheckRefresh(true);

        return configurer;
    }
}