package app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 * Sevlet 3.0 이하 버전은 multipart 지원이 안되므로
 * StandardServletMultipartResolver가 아닌
 * commons-fileupload 라이브러리를 사용한다.
 */
@Configuration
public class MultipartConfig {

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
