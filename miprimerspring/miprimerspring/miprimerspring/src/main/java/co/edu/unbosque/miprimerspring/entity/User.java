package co.edu.unbosque.miprimerspring.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase que representa un usuario en el sistema.
 * Implementa UserDetails para integrarse con Spring Security.
 * 
 * Esta entidad se almacena en la tabla "useraccount" en la base de datos.
 */
@Entity
@Table(name = "useraccount")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador único del usuario.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de usuario único.
	 */
	@Column(unique = true)
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
	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * Estado de la cuenta.
	 */
	private boolean accountNonExpired;

	private boolean accountNonLocked;

	private boolean credentialsNonExpired;

	private boolean enabled;

	/**
	 * Constructor por defecto.
	 */
	public User() {
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.role = Role.NOAUTH;
	}

	/**
	 * Constructor con username y password.
	 */
	public User(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor completo.
	 */
	public User(String username, String password, Role role, LocalDate fechaNacimiento) {
		this();
		this.username = username;
		this.password = password;
		this.role = role;
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Roles disponibles.
	 */
	public enum Role {
		NOAUTH, NINYO, ADULTO, ADMIN
	}

	/**
	 * Retorna las autoridades del usuario.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Obtiene el signo zodiacal del usuario.
	 */
	public String obtenerSigno() {

		if (fechaNacimiento == null) {
			return "Sin fecha de nacimiento";
		}

		int dia = fechaNacimiento.getDayOfMonth();
		int mes = fechaNacimiento.getMonthValue();

		if ((mes == 3 && dia >= 21) || (mes == 4 && dia <= 19))
			return "Aries";

		if ((mes == 4 && dia >= 20) || (mes == 5 && dia <= 20))
			return "Tauro";

		if ((mes == 5 && dia >= 21) || (mes == 6 && dia <= 20))
			return "Geminis";

		if ((mes == 6 && dia >= 21) || (mes == 7 && dia <= 22))
			return "Cancer";

		if ((mes == 7 && dia >= 23) || (mes == 8 && dia <= 22))
			return "Leo";

		if ((mes == 8 && dia >= 23) || (mes == 9 && dia <= 22))
			return "Virgo";

		if ((mes == 9 && dia >= 23) || (mes == 10 && dia <= 22))
			return "Libra";

		if ((mes == 10 && dia >= 23) || (mes == 11 && dia <= 21))
			return "Escorpio";

		if ((mes == 11 && dia >= 22) || (mes == 12 && dia <= 21))
			return "Sagitario";

		if ((mes == 12 && dia >= 22) || (mes == 1 && dia <= 19))
			return "Capricornio";

		if ((mes == 1 && dia >= 20) || (mes == 2 && dia <= 18))
			return "Acuario";

		return "Piscis";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		User other = (User) obj;

		return Objects.equals(id, other.id)
				&& Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id
				+ ", username=" + username
				+ ", role=" + role
				+ ", fechaNacimiento=" + fechaNacimiento + "]";
	}
}