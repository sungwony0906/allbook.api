package com.starsource.allbook.config;

import com.starsource.allbook.config.auth.CustomOAuth2UserService;
import com.starsource.allbook.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .headers().frameOptions().disable() // for h2-console
        .and()
            .authorizeRequests()
                .antMatchers("/", "/error", "/webjars/**", "/h2-console/**", "/swagger*/**", "/v3/api-docs").permitAll()
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
