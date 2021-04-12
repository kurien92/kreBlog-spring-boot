package net.kurien.blog.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Configuration
@EncryptablePropertySource("classpath:application.yml")
@Profile("!test")
public class MailConfiguration {
    @Value("${mail.email}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setDefaultEncoding("utf-8");
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.auth", true);

        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
}
