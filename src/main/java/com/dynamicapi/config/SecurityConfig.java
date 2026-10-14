package com.dynamicapi.config;

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
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // إعدادات الحماية والمسارات
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // تعطيل مؤقت لتسهيل تجربة الـ API
            .authorizeHttpRequests(auth -> auth
                // حماية جميع المسارات الخاصة بلوحة التحكم
                .requestMatchers("/api/admin/**").hasRole("ADMIN") 
                .anyRequest().authenticated()
            )
            // تفعيل شاشة تسجيل الدخول الافتراضية الأنيقة من Spring
            .formLogin(withDefaults()) 
            .httpBasic(withDefaults());

        return http.build();
    }

    // إنشاء حساب مدير النظام (للتجربة حالياً)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username("admin") // اسم المستخدم
            .password(passwordEncoder().encode("admin123")) // كلمة المرور
            .roles("ADMIN") // الصلاحية: مدير
            .build();
            
        return new InMemoryUserDetailsManager(admin);
    }

    // لتشفير كلمة المرور بشكل آمن
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}