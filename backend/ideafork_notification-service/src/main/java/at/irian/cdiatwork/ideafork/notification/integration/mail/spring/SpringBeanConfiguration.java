package at.irian.cdiatwork.ideafork.notification.integration.mail.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanConfiguration {
    @Bean
    public SpringMailSender ideaForkMailSender() {
        return new SpringMailSender();
    }
}
