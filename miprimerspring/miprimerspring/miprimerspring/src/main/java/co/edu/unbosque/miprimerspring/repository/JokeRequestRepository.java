package co.edu.unbosque.miprimerspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.miprimerspring.entity.JokeRequest;
import co.edu.unbosque.miprimerspring.entity.User;

/**
 * Repositorio encargado de las operaciones de base de datos para el historial
 * de solicitudes de chistes.
 */
@Repository
public interface JokeRequestRepository extends JpaRepository<JokeRequest, Long> {

	/**
	 * Retorna todas las solicitudes realizadas por un usuario específico,
	 * ordenadas de más reciente a más antigua.
	 *
	 * @param username nombre del usuario
	 * @return lista de solicitudes del usuario
	 */
	List<JokeRequest> findByUsernameOrderByRequestDateDesc(String username);

	/**
	 * Retorna todas las solicitudes realizadas por usuarios con un rol específico,
	 * ordenadas de más reciente a más antigua.
	 *
	 * @param role rol a filtrar
	 * @return lista de solicitudes del rol
	 */
	List<JokeRequest> findByRoleOrderByRequestDateDesc(User.Role role);

	/**
	 * Retorna todas las solicitudes ordenadas de más reciente a más antigua.
	 *
	 * @return lista completa de solicitudes
	 */
	List<JokeRequest> findAllByOrderByRequestDateDesc();
}