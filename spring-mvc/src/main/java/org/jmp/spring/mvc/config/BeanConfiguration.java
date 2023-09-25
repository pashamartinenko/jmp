package org.jmp.spring.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class BeanConfiguration
{
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public SimpleDateFormat formatter() {
        return new SimpleDateFormat(DATE_PATTERN);
    }
}
