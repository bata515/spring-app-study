package com.example.springapp.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/users/create")  // CSRFを無効化するURLを指定
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/users/create","/register","/error").permitAll()  // 指定のリクエストは認証なしで許可します
                        .anyRequest().authenticated()  // 他のページは認証が必要
                )
                .formLogin(form -> form
                        .loginPage("/login")  // カスタムログインページを指定
                        .permitAll()  // ログインページは認証なしで許可します
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // ログアウトURL
                        .permitAll()
                );
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean // このメソッドの返り値をSpringコンテナにBeanとして登録する
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoderをインスタンス化して返す
    }

}
