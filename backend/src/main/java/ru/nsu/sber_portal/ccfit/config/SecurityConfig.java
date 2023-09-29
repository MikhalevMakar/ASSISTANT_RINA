package ru.nsu.sber_portal.ccfit.config;

//import org.jetbrains.annotations.NotNull;
//import org.springframework.context.annotation.*;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;

//@Configuration
//
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//            .cors(cors -> cors.configurationSource(
//                request -> {
//                    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
//                    configuration.addAllowedMethod (HttpMethod.POST);
//                    configuration.addAllowedMethod (HttpMethod.GET);
//                    configuration.addAllowedMethod (HttpMethod.DELETE);
//                    configuration.addAllowedOriginPattern ("*");
//                    configuration.addAllowedHeader("*");
//                    return configuration;
//                }
//            ));
//
//        return http.authorizeHttpRequests(auth ->
//            auth.anyRequest().permitAll()).build();
//    }
//
//}
