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
@RequestMapping("/ninyo")
@CrossOrigin(origins = { "http://localhost:4200", "*" })
public class NinyoController {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Retorna un chiste sin contenido inapropiado.
	 * Solo accesible para usuarios con rol NINYO.
	 */
	@GetMapping("/chiste")
	public ResponseEntity<JokeDTO> getChiste() {

		String url = "https://v2.jokeapi.dev/joke/Any"
				+ "?blacklistFlags=nsfw,religious,political,racist,sexist,explicit";

		JokeDTO joke = restTemplate.getForObject(url, JokeDTO.class);

		return ResponseEntity.ok(joke);
	}
}