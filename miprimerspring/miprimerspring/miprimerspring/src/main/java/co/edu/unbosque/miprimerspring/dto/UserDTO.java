package co.edu.unbosque.miprimerspring.dto;

import java.time.LocalDate;
import java.util.Objects;

import co.edu.unbosque.miprimerspring.entity.User.Role;

/**
 * Clase DTO para transferencia de datos de usuario.
 */
public class UserDTO {

	/**
	 * Identificador único del usuario.
	 */
	private Long id;

	/**
	 * Nombre de usuario.
	 */
	private String username;

	/**
	 * Contraseña del usuario.
	 */
	private String password;

	/**
	 * Fecha de nacimiento del usuario.
	 */
	private LocalDate fechaNacimiento;

	/**
	 * Rol del usuario.
	 */
	private Role role;

	/**
	 * Constructor por defecto.
	 */
	public UserDTO() {
	}

	/**
	 * Constructor con username y password.
	 */
	public UserDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor completo.
	 */
	public UserDTO(String username, String password, Role role, LocalDate fechaNacimiento) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Obtiene el id del usuario.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el id del usuario.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Establece el username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Obtiene el password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Establece el password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene la fecha de nacimiento.
	 */
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Establece la fecha de nacimiento.
	 */
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Obtiene el rol.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Establece el rol.
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password, role, username, fechaNacimiento);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		UserDTO other = (UserDTO) obj;

		return Objects.equals(id, other.id)
				&& Objects.equals(password, other.password)
				&& role == other.role
				&& Objects.equals(username, other.username)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento);
	}

	@Override
	public String toString() {
		return "UserDTO [id="
				+ id
				+ ", username="
				+ username
				+ ", role="
				+ role
				+ ", fechaNacimiento="
				+ fechaNacimiento
				+ "]";
	}
}