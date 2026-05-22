package co.edu.unbosque.miprimerspring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.miprimerspring.entity.User;

/**
 * Repositorio encargado de las operaciones de base de datos relacionadas con
 * los usuarios.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Busca un usuario por username.
	 * 
	 * @param username nombre de usuario
	 * @return Optional con el usuario encontrado
	 */
	Optional<User> findByUsername(String username);
}
