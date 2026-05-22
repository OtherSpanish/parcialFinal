package co.edu.unbosque.miprimerspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.unbosque.miprimerspring.repository.UserRepository;


/**
 * Servicio encargado de cargar usuarios
 * para Spring Security.
 */
@Service
public class UserDetailsServiceImpl
		implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Busca un usuario por username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		return userRepository.findByUsername(username)
				.orElseThrow(() ->
						new UsernameNotFoundException(
								"Usuario no encontrado: " + username));
	}
}