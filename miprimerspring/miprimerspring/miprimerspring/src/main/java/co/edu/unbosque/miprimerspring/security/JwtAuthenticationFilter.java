package co.edu.unbosque.miprimerspring.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import co.edu.unbosque.miprimerspring.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro encargado de validar JWT en cada petición.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private co.edu.unbosque.miprimerspring.service.JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	/**
	 * Filtra cada request HTTP para validar JWT.
	 */
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");

		final String jwt;

		final String username;

		/**
		 * Verifica si existe header Authorization
		 * y si empieza por Bearer.
		 */
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);

			return;
		}

		/**
		 * Extrae token.
		 */
		jwt = authHeader.substring(7);

		/**
		 * Extrae username desde JWT.
		 */
		username = jwtService.extractUsername(jwt);

		/**
		 * Si el usuario no está autenticado aún.
		 */
		if (username != null
				&& SecurityContextHolder
						.getContext()
						.getAuthentication() == null) {

			UserDetails userDetails =
					userDetailsService.loadUserByUsername(username);

			/**
			 * Valida token.
			 */
			if (jwtService.validateToken(jwt, userDetails)) {

				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(
								userDetails,
								null,
								userDetails.getAuthorities());

				authToken.setDetails(
						new WebAuthenticationDetailsSource()
								.buildDetails(request));

				SecurityContextHolder
						.getContext()
						.setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}