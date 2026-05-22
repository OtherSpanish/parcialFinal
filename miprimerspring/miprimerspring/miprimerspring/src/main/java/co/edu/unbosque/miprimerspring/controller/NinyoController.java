package co.edu.unbosque.miprimerspring.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import co.edu.unbosque.miprimerspring.dto.JokeDTO;
import co.edu.unbosque.miprimerspring.entity.User;
import co.edu.unbosque.miprimerspring.repository.UserRepository;
import co.edu.unbosque.miprimerspring.service.JokeRequestService;


@RestController
@RequestMapping("/ninyo")
@CrossOrigin(origins = { "http://localhost:4200", "*" })
public class NinyoController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JokeRequestService jokeRequestService;

	@Autowired
	private UserRepository userRepository;


	@GetMapping("/chiste")
	public ResponseEntity<JokeDTO> getChiste(Principal principal) {

		String url = "https://v2.jokeapi.dev/joke/Any"
				+ "?blacklistFlags=nsfw,religious,political,racist,sexist,explicit";

		JokeDTO joke = restTemplate.getForObject(url, JokeDTO.class);

		if (joke != null && principal != null) {

			userRepository.findByUsername(principal.getName()).ifPresent(user -> {
				jokeRequestService.saveRequest(user.getUsername(), user.getRole(), joke);
			});
		}

		return ResponseEntity.ok(joke);
	}
}