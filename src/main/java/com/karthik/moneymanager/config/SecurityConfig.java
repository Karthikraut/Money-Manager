package com.karthik.moneymanager.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.karthik.moneymanager.security.JwtRequestFilter;
import com.karthik.moneymanager.service.AppUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration // ✅ Marks this class as a configuration class for Spring
@RequiredArgsConstructor // ✅ Generates a constructor for final fields (not used here but useful)
public class SecurityConfig {
    
    private final AppUserDetailsService appUserDetailsService ;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean // ✅ This method defines a bean for the application's security filter chain
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        
        /* 
         * 1️⃣ Enable CORS (Cross-Origin Resource Sharing)
         * Allows the backend to accept requests from other domains (like React/Next.js frontend).
         * Customizer.withDefaults() -> Uses default CORS settings.
         */
        httpSecurity.cors(Customizer.withDefaults())
        
        /*
         * 2️⃣ Disable CSRF (Cross-Site Request Forgery protection)
         * Since this app is likely using REST APIs with tokens (like JWT),
         * we disable CSRF as it's mainly needed for stateful session-based apps.
         */
        .csrf(AbstractHttpConfigurer::disable)
        
        /*
         * 3️⃣ Define Authorization Rules
         * - "/api/v1/register" and "/api/v1/activate" -> accessible by everyone (no authentication required)
         * - Any other request -> must be authenticated (user must provide valid token or credentials)
         */
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/status","/register","/health","/activate").permitAll()
            .anyRequest().authenticated())
        
        /*
         * 4️⃣ Set Session Management Policy
         * - STATELESS -> The server does NOT store any user session.
         *   Every request must carry its own authentication token (like JWT).
         *   This is best practice for REST APIs.
         */
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        /*
         * 5️⃣ Build the Security Filter Chain
         * - After configuring all security rules, we build the filter chain
         *   which Spring Security will use to secure incoming HTTP requests.
         */
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // This method can be used to define a PasswordEncoder bean if needed
        return new BCryptPasswordEncoder(); // Example using BCrypt for password hashing
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Allow all origins (for development, restrict in
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow common HTTP methods
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Allow specific headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all endpoints
        return source; // Return the configured CORS source
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);

    }
}
