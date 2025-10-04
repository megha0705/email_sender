package com.example.email.config;

import com.verifalia.api.rest.security.BearerAuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.verifalia.api.VerifaliaRestClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerifaliaConfig {
    @Value("${verifalia.username}")
    private String username;

    @Value("${verifalia.password}")
    private String password;

    @Bean
    public VerifaliaRestClient verifaliaRestClient() {
        return new VerifaliaRestClient(
                new BearerAuthenticationProvider(username, password)
        );
    }


}
