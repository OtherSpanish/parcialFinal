package co.edu.unbosque.miprimerspring.service;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.Gson;

import co.edu.unbosque.miprimerspring.dto.JokeDTO;

public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public static JokeDTO doGetChiste(String url) {
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.setHeader("User.Agent", "Java 11 HttpClient Bot").build();
		HttpResponse<String> respuesta = null;
		try {
			respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("status code -> " + respuesta.statusCode());
		Gson gson = new Gson();
		return gson.fromJson(respuesta.body(), JokeDTO.class);
	}

<<<<<<< Updated upstream
=======
//	public static void main(String[] args) {
//		MultipleJokeDTO chistes = doGetListaChiste("https://v2.jokeapi.dev/joke/Programming?lang=es&amount=10");
//		for (JokeDTO c : chistes.getJokes()) {
//			System.out.println(c.toString());
//		}
>>>>>>> Stashed changes
}
