package co.edu.unbosque.miprimerspring.security;

<<<<<<< Updated upstream
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

=======
>>>>>>> Stashed changes
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro de autenticación JWT que intercepta las solicitudes HTTP. Valida los
 * tokens JWT en las solicitudes y establece la autenticación en el contexto de
 * seguridad. Se ejecuta una vez por cada solicitud.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/** Utilidad para operaciones con tokens JWT. */
	private final JwtUtil jwtUtil;

	/** Servicio para cargar los detalles del usuario. */
	private final UserDetailsService userDetailsService;

	/**
	 * Constructor que inicializa las dependencias necesarias para el filtro.
	 *
	 * @param jwtUtil            Utilidad para operaciones con tokens JWT
	 * @param userDetailsService Servicio para cargar los detalles del usuario
	 */
	public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Método principal del filtro que se ejecuta para cada solicitud HTTP. Extrae y
	 * valida el token JWT del encabezado de autorización. Si el token es válido,
	 * establece la autenticación en el contexto de seguridad.
	 *
	 * @param request     Solicitud HTTP entrante
	 * @param response    Respuesta HTTP saliente
	 * @param filterChain Cadena de filtros para continuar el procesamiento
	 * @throws ServletException Si ocurre un error durante el procesamiento del
	 *                          servlet
	 * @throws IOException      Si ocurre un error de entrada/salida
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		String username = null;
		String jwt = null;

<<<<<<< Updated upstream
		final String username;

		/**
		 * Verifica si existe header Authorization y si empieza por Bearer.
		 */
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);

			return;
=======
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			try {
				username = jwtUtil.extractUsername(jwt);
			} catch (Exception e) {
				logger.error("Error extracting username from token", e);
			}
>>>>>>> Stashed changes
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

<<<<<<< Updated upstream
		/**
		 * Extrae username desde JWT.
		 */
		username = jwtService.extractUsername(jwt);

		/**
		 * Si el usuario no está autenticado aún.
		 */
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			/**
			 * Valida token.
			 */
			if (jwtService.validateToken(jwt, userDetails)) {

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
=======
			if (jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
>>>>>>> Stashed changes
			}
		}

		filterChain.doFilter(request, response);
	}
}
