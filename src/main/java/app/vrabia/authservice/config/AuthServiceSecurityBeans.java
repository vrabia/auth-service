package app.vrabia.authservice.config;

import app.vrabia.vrcommon.models.security.PublicEndpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AuthServiceSecurityBeans {

    @Bean
    @Primary
    public PublicEndpoints authServicePublicEndpoints() {
        PublicEndpoints publicEndpoints = new PublicEndpoints();
        publicEndpoints.getEndpoints().add("/auth/login");
        publicEndpoints.getEndpoints().add("/auth/register");
        publicEndpoints.getEndpoints().add("/auth/device");
        publicEndpoints.getEndpoints().add("/auth/device/step2");
        return publicEndpoints;
    }
}
