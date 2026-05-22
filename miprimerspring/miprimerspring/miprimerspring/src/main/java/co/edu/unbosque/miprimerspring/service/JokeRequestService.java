package co.edu.unbosque.miprimerspring.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.miprimerspring.dto.JokeDTO;
import co.edu.unbosque.miprimerspring.dto.JokeRequestDTO;
import co.edu.unbosque.miprimerspring.entity.JokeRequest;
import co.edu.unbosque.miprimerspring.entity.User;
import co.edu.unbosque.miprimerspring.repository.JokeRequestRepository;

/**
 * Servicio encargado de persistir y consultar el historial de solicitudes de
 * chistes. Cada vez que un usuario (ADULTO o NINYO) consulta un chiste, se
 * registra: nombre de usuario, rol, contenido del chiste y fecha/hora exacta.
 */
@Service
public class JokeRequestService {

	@Autowired
	private JokeRequestRepository jokeRequestRepository;

	// -------------------------------------------------------------------------
	// Persistencia
	// -------------------------------------------------------------------------

	/**
	 * Guarda en la base de datos el registro de una solicitud de chiste.
	 *
	 * Para chistes de tipo "twopart" el contenido se almacena como:
	 * "[setup] || [delivery]"
	 * Para chistes de tipo "single" se almacena directamente el campo "joke".
	 *
	 * @param username nombre del usuario autenticado
	 * @param role     rol del usuario al momento de la solicitud
	 * @param joke     objeto DTO con el chiste retornado por la API externa
	 */
	public void saveRequest(String username, User.Role role, JokeDTO joke) {

		String jokeContent;

		// Determinar el contenido según el tipo de chiste
		if ("twopart".equalsIgnoreCase(joke.getType())) {
			jokeContent = joke.getSetup() + " || " + joke.getDelivery();
		} else {
			jokeContent = joke.getJoke();
		}

		JokeRequest request = new JokeRequest(
				username,
				role,
				jokeContent,
				joke.getCategory(),
				LocalDateTime.now());

		jokeRequestRepository.save(request);
	}

	// -------------------------------------------------------------------------
	// Consultas (solo para ADMIN)
	// -------------------------------------------------------------------------

	/**
	 * Retorna el historial completo de solicitudes de chistes ordenado de más
	 * reciente a más antiguo.
	 *
	 * @return lista de DTOs con todos los registros
	 */
	public List<JokeRequestDTO> getAllRequests() {

		return jokeRequestRepository.findAllByOrderByRequestDateDesc()
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Retorna el historial de solicitudes de un usuario específico ordenado de más
	 * reciente a más antiguo.
	 *
	 * @param username nombre del usuario a consultar
	 * @return lista de DTOs con los registros del usuario
	 */
	public List<JokeRequestDTO> getRequestsByUsername(String username) {

		return jokeRequestRepository.findByUsernameOrderByRequestDateDesc(username)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Retorna el historial de solicitudes filtrado por rol de usuario, ordenado de
	 * más reciente a más antiguo.
	 *
	 * @param role rol a filtrar (ADULTO o NINYO)
	 * @return lista de DTOs con los registros del rol
	 */
	public List<JokeRequestDTO> getRequestsByRole(User.Role role) {

		return jokeRequestRepository.findByRoleOrderByRequestDateDesc(role)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	// -------------------------------------------------------------------------
	// Mapper interno: entidad -> DTO
	// -------------------------------------------------------------------------

	/**
	 * Convierte una entidad {@link JokeRequest} en su {@link JokeRequestDTO}
	 * equivalente.
	 *
	 * @param entity entidad a convertir
	 * @return DTO resultante
	 */
	private JokeRequestDTO toDTO(JokeRequest entity) {

		return new JokeRequestDTO(
				entity.getId(),
				entity.getUsername(),
				entity.getRole(),
				entity.getJokeContent(),
				entity.getJokeCategory(),
				entity.getRequestDate());
	}
}