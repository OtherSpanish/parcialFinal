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
 * Clase que representa un usuario en el sistema. Implementa UserDetails para
 * integrarse con Spring Security.
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

	//Returna el valor si es mayor menor de edad invalido o error
	public char esMayorDeEdad() {

		// i = invalid f = false t = true e = error
		if (fechaNacimiento == null) {
			return 'i';
		}

		int dia = fechaNacimiento.getDayOfMonth();
		int mes = fechaNacimiento.getMonthValue();
		int anyo = fechaNacimiento.getYear();

		if ((anyo >= 2008 && anyo <= 2026) && (mes <= 05 && mes > 0) && (dia <= 22 && dia > 0 && dia <= 31)) {
			return 'f';
		}

		if ((anyo < 2008 && anyo > 1926) && (mes > 0) && (dia > 0 && dia <= 31)) {
			return 't';
		}

		return 'e';
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

		return Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", role=" + role + ", fechaNacimiento=" + fechaNacimiento
				+ "]";
	}
}