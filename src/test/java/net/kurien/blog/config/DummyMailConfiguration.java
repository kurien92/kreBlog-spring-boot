package net.kurien.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class DummyMailConfiguration {
    @Bean
    public JavaMailSenderImpl mailSender() {
        return new JavaMailSenderImpl();
    }
}
