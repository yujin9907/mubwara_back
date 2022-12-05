package site.metacoding.finals.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import site.metacoding.finals.config.jwt.JwtAutenticationFilter;
import site.metacoding.finals.config.jwt.JwtAuthorizationFilter;
import site.metacoding.finals.handler.LoginHandler;

@Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private CorsConfig corsConfig;

    // JWT 기반 로그인 시큐리티 설정, 주석은 폼 로그인 기반

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable(); // 포스트맨 임시

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                // .successHandler(ㅠㅠ)
                .disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl());
        http
                .authorizeRequests()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/auth/user/**").access("hasRole('USER')")
                .antMatchers("/auth/shop/**").access("hasRole('SHOP')")
                .anyRequest().permitAll();
        http.logout()
                .logoutSuccessUrl("/");

        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAutenticationFilter(authenticationManager, loginHandler))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager));
        }
    }

}
