package co.edu.unbosque.miprimerspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.miprimerspring.dto.UserDTO;
import co.edu.unbosque.miprimerspring.service.LoginService;

/**
 * Controlador encargado de la autenticación de usuarios. Maneja login y
 * registro utilizando JWT.
 */
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = { "http://localhost:4200", "*" })
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * Endpoint para registrar usuarios.
	 * 
	 * @param userDTO datos del usuario
	 * @return token JWT generado
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {

		try {

			String token = loginService.register(userDTO);

			return ResponseEntity.ok(token);

		} catch (Exception e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Endpoint para login.
	 * 
	 * @param userDTO credenciales del usuario
	 * @return token JWT
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {

		try {

			String token = loginService.login(userDTO);

			return ResponseEntity.ok(token);

		} catch (Exception e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
