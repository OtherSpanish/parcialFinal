package co.edu.unbosque.miprimerspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import co.edu.unbosque.miprimerspring.dto.JokeDTO;

@RestController
@RequestMapping("/adulto")
@CrossOrigin(origins = { "http://localhost:4200", "*" })
public class AdultoController {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Retorna un chiste de cualquier tipo, sin restricciones.
	 * Solo accesible para usuarios con rol ADULTO.
	 */
	@GetMapping("/chiste")
	public ResponseEntity<JokeDTO> getChiste() {

		String url = "https://v2.jokeapi.dev/joke/Any";

		JokeDTO joke = restTemplate.getForObject(url, JokeDTO.class);

		return ResponseEntity.ok(joke);
	}
}