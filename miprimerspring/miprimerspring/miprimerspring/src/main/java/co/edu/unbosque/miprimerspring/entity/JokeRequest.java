package co.edu.unbosque.miprimerspring.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa el registro histórico de cada solicitud de chiste
 * realizada por un usuario. Se persiste en la tabla "joke_request".
 */
@Entity
@Table(name = "joke_request")
public class JokeRequest {

	/** Identificador único del registro. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre del usuario que realizó la solicitud. */
	@Column(nullable = false)
	private String username;

	/** Rol del usuario al momento de la solicitud. */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private User.Role role;

	/**
	 * Contenido del chiste solicitado. Para chistes de tipo "twopart" se almacena
	 * "setup || delivery"; para tipo "single" se almacena directamente el campo
	 * "joke".
	 */
	@Column(nullable = false, length = 1000)
	private String jokeContent;

	/** Categoría del chiste (Programming, Misc, Dark, etc.). */
	private String jokeCategory;

	/** Fecha y hora exacta en la que se realizó la solicitud. */
	@Column(nullable = false)
	private LocalDateTime requestDate;

	/** Constructor por defecto requerido por JPA. */
	public JokeRequest() {
	}

	/**
	 * Constructor completo.
	 *
	 * @param username     nombre del usuario
	 * @param role         rol del usuario
	 * @param jokeContent  contenido del chiste
	 * @param jokeCategory categoría del chiste
	 * @param requestDate  fecha y hora de la solicitud
	 */
	public JokeRequest(String username, User.Role role, String jokeContent, String jokeCategory,
			LocalDateTime requestDate) {
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
		JokeRequest other = (JokeRequest) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "JokeRequest [id=" + id + ", username=" + username + ", role=" + role + ", jokeCategory=" + jokeCategory
				+ ", requestDate=" + requestDate + "]";
	}
}