package org.jmp.spring.crud.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class BeanConfiguration
{

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public SimpleDateFormat formatter() {
        return new SimpleDateFormat(DATE_PATTERN);
    }
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder ->
                jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }
}
