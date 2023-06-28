package com.repository.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:${property-prefix}/application.properties")
public class ApplicationConfig
{
}
