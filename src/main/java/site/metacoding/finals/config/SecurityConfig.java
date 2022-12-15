package site.metacoding.finals.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import site.metacoding.finals.config.exception.JwtExceptionHandler;
import site.metacoding.finals.config.jwt.JwtAutenticationFilter;
import site.metacoding.finals.config.jwt.JwtAuthorizationFilter;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.handler.LoginHandler;

@Configuration
// @EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private UserRepository userRepository;

    // JWT 기반 로그인 시큐리티 설정, 주석은 폼 로그인 기반

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable(); // 포스트맨 임시

        http.exceptionHandling().authenticationEntryPoint(
                (request, response, authException) -> {
                    JwtExceptionHandler.sendError(HttpStatus.BAD_REQUEST, "만료된 토큰 재요청 필요", response);
                });

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl());
        http.authorizeRequests()
                .antMatchers("/auth/**").authenticated() // .access("hasRole('USER')")
                .antMatchers("/user/**").hasAuthority("USER")
                .antMatchers("/shop/**").hasAuthority("SHOP")
                .anyRequest().permitAll();
        // http.logout()
        // .logoutSuccessUrl("/");

        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAutenticationFilter(authenticationManager, loginHandler))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }

}
