package co.edu.unbosque.miprimerspring.dto;

import java.util.ArrayList;

public class MultipleJokeDTO {

	private ArrayList<JokeDTO> jokes;
	private int amount;
	
	
	public MultipleJokeDTO() {
		// TODO Auto-generated constructor stub
	}


	public MultipleJokeDTO(ArrayList<JokeDTO> jokes, int amount) {
		super();
		this.jokes = jokes;
		this.amount = amount;
	}


	public ArrayList<JokeDTO> getJokes() {
		return jokes;
	}


	public void setJokes(ArrayList<JokeDTO> jokes) {
		this.jokes = jokes;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "MultipleJokeDTO [jokes=" + jokes + ", amount=" + amount + "]";
	}
	
	
	
}
