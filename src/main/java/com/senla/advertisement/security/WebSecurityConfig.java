package com.senla.advertisement.security;

//import com.senla.advertisement.security.filters.JwtExceptionFilter;
import com.senla.advertisement.security.filters.JwtExceptionFilter;
import com.senla.advertisement.security.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtExceptionFilter exceptionFilter;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    @Qualifier("delegatedAccessDenied")
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Set permissions on endpoints
        http.authorizeHttpRequests((authorize) -> authorize
                .mvcMatchers("/users/login", "/users/register").permitAll()
                .mvcMatchers("/users/", "/users/count", "/users/{username}/roles", "/users/{username}/comments",
                        "/advertisements/whole-list", "/advertisements/{id}/change-status",
                        "/advertisement-rates",
                        "/products",
                        "/purchases",
                        "/comments").hasAnyAuthority("MODERATOR", "ADMIN")
                .mvcMatchers("/users/{username}/add-role", "/users/{username}/delete-role",
                        "/users/{username}/change-status").hasAuthority("ADMIN")
                .anyRequest().authenticated());


        // disable CSRF
        http = http.csrf().disable();

//        disable CORS
        http = http.cors().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and();


//        jwt filter
        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        http.addFilterBefore(exceptionFilter, JwtFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
