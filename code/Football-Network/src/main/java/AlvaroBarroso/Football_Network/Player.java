package AlvaroBarroso.Football_Network;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String img;
	private String name;
	private String surname;
	private String position;
	private int rating;
	private String team;
	
	
	public Player() {}
	public Player(String name, String surname, String position, int rating, String team) {
		super();
		this.img= "null";
		this.name = name;
		this.surname = surname;
		this.position = position;
		this.rating = rating;
		this.team = team;
	}
	public Player(String img, String name, String surname, String position, int rating, String team) {
		super();
		this.img = img;
		this.name = name;
		this.surname = surname;
		this.position = position;
		this.rating = rating;
		this.team = team;
	}

	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}



	public String getTeam() {
		return team;
	}



	public void setTeam(String team) {
		this.team = team;
	}







	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", position=" + position + ", rating="
				+ rating + ", team=" + team + "]";
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
}
