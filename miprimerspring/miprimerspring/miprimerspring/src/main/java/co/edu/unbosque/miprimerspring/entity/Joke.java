package co.edu.unbosque.miprimerspring.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "joke")
public class Joke {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String category;
	private String type;
	private String setup;
	private String delivery;
	private String joke;

	public Joke() {
		// TODO Auto-generated constructor stub
	}

	public Joke(String category, String type, String setup, String delivery, String joke) {
		super();
		this.category = category;
		this.type = type;
		this.setup = setup;
		this.delivery = delivery;
		this.joke = joke;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getJoke() {
		return joke;
	}

	public void setJoke(String joke) {
		this.joke = joke;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joke other = (Joke) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "JokeDTO [category=" + category + ", type=" + type + ", setup=" + setup + ", delivery=" + delivery
				+ ", joke=" + joke + "]";
	}

}
