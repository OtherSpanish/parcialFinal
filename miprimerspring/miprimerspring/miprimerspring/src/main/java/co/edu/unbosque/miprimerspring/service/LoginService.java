package co.edu.unbosque.miprimerspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.miprimerspring.dto.UserDTO;
import co.edu.unbosque.miprimerspring.entity.User;
import co.edu.unbosque.miprimerspring.repository.UserRepository;


@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	
	public String register(UserDTO userDTO) throws Exception {

		if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
			throw new Exception("El usuario ya existe");
		}

		User user = new User();

		user.setUsername(userDTO.getUsername());

		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		user.setFechaNacimiento(userDTO.getFechaNacimiento());

		char mayorDeEdad = user.esMayorDeEdad();

		if (mayorDeEdad == 't') {
			user.setRole(User.Role.ADULTO);
		} else if (mayorDeEdad == 'f') {
			user.setRole(User.Role.NINYO);
		} else {
			user.setRole(User.Role.NOAUTH);
		}

		userRepository.save(user);

		return jwtService.generateToken(user);
	}


	public String login(UserDTO userDTO) throws Exception {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userDTO.getUsername(),
						userDTO.getPassword()));

		User user = userRepository.findByUsername(userDTO.getUsername())
				.orElseThrow(() -> new Exception("Usuario no encontrado"));

		return jwtService.generateToken(user);
	}
}