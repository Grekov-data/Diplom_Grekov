package org.datko.diplom_grekov.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// Класс конфигурации SpringSecurity
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Конфигурация управляющая доступом к обработчикам (запросам)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "", "company/new", "user/new", "webjars/**", "css/**", "images/**").permitAll()
                        .anyRequest().authenticated())

                .formLogin((form) -> form
                        .loginPage("/log-in").permitAll())

                .logout((customizer) -> customizer
                        .logoutSuccessUrl("/log-in"));
        return http.build();
    }

    // расскажу позже
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Конфигурация UserDetailsService - провайдер работы с пользователями
    // версия только для тестирования
    // (in memory develop only version)
    @Bean
    public UserDetailsService userDetailsService() {
        // ЗАГЛУШКА (ПОЗЖЕ ВМЕСТО ЭТОГО БУДЕТ RdbUserDetailsService)
        // сконфигурируем пользователей в контексте ОЗУ

        // 1. создать пользователей
        UserDetails user = User.builder()
                .username("test")
                .password("test")
                .passwordEncoder(passwordEncoder()::encode)
                .build();

        UserDetails admin = User.builder()
                .username("admin")                .password("admin")
                .passwordEncoder(passwordEncoder()::encode)
                .build();

        // 2. вернуть пользователей в виде хранилища в памяти
        return new InMemoryUserDetailsManager(user, admin);
    }
}
