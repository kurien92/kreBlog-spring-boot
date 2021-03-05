package net.kurien.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecutiryConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;

    public SecutiryConfiguration(AuthenticationProvider authenticationProvider, UserDetailsService userDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(authenticationProvider)
            .userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder(12));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .authorizeRequests()
                .antMatchers(
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/.well-known/**",
                    "/robots.txt",
                    "/ads.txt").permitAll()
                .antMatchers(
                    "/admin/**"
                ).hasRole("ADMIN")
                .antMatchers("/**")
                .permitAll()
            .and()
            .formLogin()
                .usernameParameter("acntId")
                .passwordParameter("acntPassword")
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signinCheck")
                .failureUrl("/auth/signin?fail=true")
//                .successHandler(customAuthenticationSuccessHandler())
//                .failureHandler(customAuthenticationFailureHandler())
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/auth/signout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            .and()
                .exceptionHandling()
                .accessDeniedPage("/error/forbidden");
    }



//	<http auto-config="true" use-expressions="true">
//		<form-login
//
//    default-target-url="/"
//    authentication-failure-url="/auth/signin?fail=true" />
//
//		<anonymous granted-authority="ROLE_ANONYMOUS" />
//
//	</http>
//
}
