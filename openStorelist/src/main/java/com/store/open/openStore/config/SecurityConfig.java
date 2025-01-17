package com.store.open.openStore.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.store.open.openStore.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	@Bean
	DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("schema.sql")
			.build();
	}
	*/
	
	@Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
		.antMatchers("/category/**").hasAnyRole("ADMIN", "BUYER_OR_SELLER")
		.antMatchers("/product/**").hasAnyRole("ADMIN", "BUYER_OR_SELLER")
        .antMatchers("/**").permitAll()
        .and().httpBasic(Customizer.withDefaults())
		.formLogin(Customizer.withDefaults());
        http.cors().disable().csrf().disable();
        http.headers().frameOptions().disable();

		return http.build();
	}
*/
	
	
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
		.antMatchers("/category/**").authenticated()
		.antMatchers("/product/**").authenticated()
		.antMatchers("/login").permitAll()
		.antMatchers("/user/**").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers("/**").permitAll()
        .and().httpBasic(Customizer.withDefaults())
		.formLogin(Customizer.withDefaults());
        http.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());
        http.cors().and().csrf().disable();
        http.headers().frameOptions().disable();

    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
