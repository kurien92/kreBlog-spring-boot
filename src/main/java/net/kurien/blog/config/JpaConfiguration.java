package net.kurien.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

@Configuration
@EnableTransactionManagement
public class JpaConfiguration {
    @Autowired
    private LocalContainerEntityManagerFactoryBean emf;

    @Bean
    public EntityManager entityManager() {
        EntityManager em = emf.getNativeEntityManagerFactory().createEntityManager();
        em.setFlushMode(FlushModeType.AUTO);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf.getObject());

        return transactionManager;
    }
}
