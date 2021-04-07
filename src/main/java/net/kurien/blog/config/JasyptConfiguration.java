package net.kurien.blog.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfiguration {
    @Value("${KRE_ENC_KEY}")
    private String kreEncKey;

    @Value("${jasypt.encryptor.key-obtention-iterations}")
    private Integer keyObtentionIterations;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setAlgorithm("PBEWithHmacSHA512AndAES_256");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setPoolSize("1");
        config.setPassword(kreEncKey);
        config.setKeyObtentionIterations(keyObtentionIterations);

        encryptor.setConfig(config);

        return encryptor;
    }
}
