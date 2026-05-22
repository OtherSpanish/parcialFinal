package co.edu.unbosque.miprimerspring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración principal de Spring Security.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * Configuración de seguridad HTTP.
	 * 
	 * @param http configuración HTTP
	 * @return filtro de seguridad
	 * @throws Exception excepción
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http

				/**
				 * Deshabilita CSRF.
				 */
				.csrf(csrf -> csrf.disable())

				/**
				 * Configura permisos.
				 */
				.authorizeHttpRequests(auth -> auth

						/**
						 * Auth libre.
						 */
						.requestMatchers("/login/**").permitAll()

						/**
						 * ADMIN: ADMIN.
						 */
						.requestMatchers("/admin/**").hasAnyRole("ADMIN")

						/**
						 * Ninyo: NINYO
						 */
						.requestMatchers("/ninyo/**").hasAnyRole("NINYO")

						/**
						 * Adulto: ADULTO.
						 */
						.requestMatchers("/adulto/**").hasAnyRole("ADULTO")

						/**
						 * Todo lo demás requiere auth.
						 */
						.anyRequest().authenticated())

				/**
				 * Stateless JWT.
				 */
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				/**
				 * Agrega filtro JWT.
				 */
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Bean de PasswordEncoder.
	 * 
	 * @return encoder BCrypt
	 */
	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	/**
	 * Bean AuthenticationManager.
	 * 
	 * @param config configuración auth
	 * @return AuthenticationManager
	 * @throws Exception excepción
	 */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();
	}
}