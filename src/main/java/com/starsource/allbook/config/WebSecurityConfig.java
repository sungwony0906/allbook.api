package com.starsource.allbook.config;

import com.starsource.allbook.config.auth.CustomOAuth2UserService;
import com.starsource.allbook.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = getPasswordEncoder();
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("spring")
                .password(encoder.encode("secret"))
                .roles("MEMBER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .headers().frameOptions().disable() // for h2-console
        .and()
            .authorizeRequests()
                .antMatchers("/", "/error", "/webjars/**", "/h2-console/**", "/swagger*/**", "/v3/api-docs", "/api/v1/members").permitAll()
                .antMatchers("/api/**").hasAnyRole(Role.MEMBER.name())
                .antMatchers("/admin/api/**").hasAnyRole(Role.ADMIN.name())
            .anyRequest()
                .authenticated()
        .and()
            .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
            .logout()
                .logoutSuccessUrl("/")
        .and()
            .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
