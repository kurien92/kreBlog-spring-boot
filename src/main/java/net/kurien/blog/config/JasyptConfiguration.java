package net.kurien.blog.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfiguration {
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(System.getenv("KRE_ENC_KEY"));
        config.setAlgorithm("PBEWithHmacSHA512AndAES_256");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setKeyObtentionIterations(2000000);
        config.setPoolSize("1");

        encryptor.setConfig(config);

        return encryptor;
    }
}
