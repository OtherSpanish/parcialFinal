package co.edu.unbosque.miprimerspring.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.miprimerspring.entity.User;

/**
 * DTO utilizado para transferir la información de una solicitud de chiste
 * hacia el cliente. Solo el ADMINISTRADOR puede acceder a estos datos.
 */
public class JokeRequestDTO {

	/** Identificador único del registro. */
	private Long id;

	/** Nombre del usuario que realizó la solicitud. */
	private String username;

	/** Rol del usuario al momento de la solicitud. */
	private User.Role role;

	/** Contenido del chiste tal como fue entregado por la API externa. */
	private String jokeContent;

	/** Categoría del chiste. */
	private String jokeCategory;

	/** Fecha y hora de la solicitud. */
	private LocalDateTime requestDate;

	/** Constructor por defecto. */
	public JokeRequestDTO() {
	}

	/**
	 * Constructor completo.
	 *
	 * @param id           identificador
	 * @param username     nombre del usuario
	 * @param role         rol del usuario
	 * @param jokeContent  contenido del chiste
	 * @param jokeCategory categoría del chiste
	 * @param requestDate  fecha y hora de la solicitud
	 */
	public JokeRequestDTO(Long id, String username, User.Role role, String jokeContent, String jokeCategory,
			LocalDateTime requestDate) {
		this.id = id;
		this.username = username;
		this.role = role;
		this.jokeContent = jokeContent;
		this.jokeCategory = jokeCategory;
		this.requestDate = requestDate;
	}

	// -------------------------------------------------------------------------
	// Getters y Setters
	// -------------------------------------------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User.Role getRole() {
		return role;
	}

	public void setRole(User.Role role) {
		this.role = role;
	}

	public String getJokeContent() {
		return jokeContent;
	}

	public void setJokeContent(String jokeContent) {
		this.jokeContent = jokeContent;
	}

	public String getJokeCategory() {
		return jokeCategory;
	}

	public void setJokeCategory(String jokeCategory) {
		this.jokeCategory = jokeCategory;
	}

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	// -------------------------------------------------------------------------
	// equals / hashCode / toString
	// -------------------------------------------------------------------------

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JokeRequestDTO other = (JokeRequestDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "JokeRequestDTO [id=" + id + ", username=" + username + ", role=" + role + ", jokeCategory="
				+ jokeCategory + ", requestDate=" + requestDate + "]";
	}
}