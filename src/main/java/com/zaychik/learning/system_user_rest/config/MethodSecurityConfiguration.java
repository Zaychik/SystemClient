package com.zaychik.learning.system_user_rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class MethodSecurityConfiguration
        extends GlobalMethodSecurityConfiguration {
}
