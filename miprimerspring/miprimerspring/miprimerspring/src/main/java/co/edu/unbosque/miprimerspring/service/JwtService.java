package co.edu.unbosque.miprimerspring.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import co.edu.unbosque.miprimerspring.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio encargado de la generación y validación de JWT.
 */
@Service
public class JwtService {

	/**
	 * Tiempo de validez del token: 24 horas.
	 */
	private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000;

	/**
	 * Clave secreta para firmar tokens.
	 */
	@Value("${jwt.secret:claveSuperSeguraJWT123456789123456}")
	private String secret;

	/**
	 * Obtiene la clave de firma.
	 * 
	 * @return clave
	 */
	private Key getSigningKey() {

		byte[] keyBytes = secret.getBytes();

		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Extrae el username del token.
	 * 
	 * @param token JWT
	 * @return username
	 */
	public String extractUsername(String token) {

		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extrae la fecha de expiración.
	 * 
	 * @param token JWT
	 * @return fecha de expiración
	 */
	public Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extrae el rol del token.
	 * 
	 * @param token JWT
	 * @return rol
	 */
	public String extractRole(String token) {

		return extractClaim(token, claims -> claims.get("role", String.class));
	}

	/**
	 * Extrae un claim específico.
	 * 
	 * @param token JWT
	 * @param claimsResolver función
	 * @return claim
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

		final Claims claims = extractAllClaims(token);

		return claimsResolver.apply(claims);
	}

	/**
	 * Extrae todos los claims.
	 * 
	 * @param token JWT
	 * @return claims
	 */
	private Claims extractAllClaims(String token) {

		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * Verifica si el token expiró.
	 * 
	 * @param token JWT
	 * @return true si expiró
	 */
	private Boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());
	}

	/**
	 * Genera un token JWT.
	 * 
	 * @param userDetails usuario
	 * @return token generado
	 */
	public String generateToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<>();

		claims.put("authorities", userDetails.getAuthorities());

		if (userDetails instanceof User) {

			User user = (User) userDetails;

			claims.put("role", user.getRole().name());
		}

		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * Crea el token JWT.
	 * 
	 * @param claims claims
	 * @param subject username
	 * @return token
	 */
	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(
						new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * Valida un token JWT.
	 * 
	 * @param token JWT
	 * @param userDetails usuario
	 * @return true si es válido
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {

		final String username = extractUsername(token);

		return username.equals(userDetails.getUsername())
				&& !isTokenExpired(token);
	}
}