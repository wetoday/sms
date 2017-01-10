package app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import app.spring.aop.LoggingAspect;
import app.spring.aop.PointCutDefinition;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    public PointCutDefinition pointCutDefinition() {
        return new PointCutDefinition();
    }
}