package me.kycho.demo.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html#authentication-password-storage-dpe
    // https://docs.spring.io/spring-security/reference/5.6.0-RC1/servlet/authentication/passwords/password-encoder.html#servlet-authentication-password-storage
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
