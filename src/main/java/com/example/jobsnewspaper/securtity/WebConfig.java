package com.example.jobsnewspaper.securtity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.AuthorizeRequestsDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebConfig {

    private AuthenticationManager authenticationManager;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    @DependsOn("authenticationManager")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new AuthFilter(authenticationManager))
                .addFilterBefore(new JwtFilter(authenticationManager), AuthFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/api/register", "/api/check-if-registered", "/api/interest/get-all").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().successForwardUrl("/api/interests/get-all");


        return httpSecurity.build();

//        httpSecurity.
//                cors().and().
//                csrf().disable().
//                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilter(new AuthFilter(authenticationManager))
//                .addFilterAfter(new JwtFilter(), AuthFilter.class)
//                .authorizeHttpRequests()
//                .requestMatchers("/api/register").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin().loginPage("/login").successForwardUrl("/api/check-token").permitAll();
//
//        return httpSecurity.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
                registry.addMapping("/**").allowedHeaders("*").exposedHeaders("*");
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.builder().username("NikitaAdmin").password(passwordEncoder.encode("1234")).roles("ADMIN").build();
        UserDetails user1 = User.builder().username("NikolaiAdmin").password(passwordEncoder.encode("iYurEhH5v3ybg9X")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, user1);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return authenticationManager = configuration.getAuthenticationManager();
    }

}
