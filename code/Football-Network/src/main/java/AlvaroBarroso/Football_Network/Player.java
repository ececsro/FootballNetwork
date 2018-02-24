package AlvaroBarroso.Football_Network;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	
	@OneToMany (cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@OneToOne (cascade = CascadeType.ALL) 
	private Contract contract;

	@OneToOne //(cascade=CascadeType.ALL)
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public Player(String img, String name, String surname, String position, int rating, String team,
			List<Comment> comments, Contract contract) {
		super();
		this.img = img;
		this.name = name;
		this.surname = surname;
		this.position = position;
		this.rating = rating;
		this.team = team;
		this.comments = comments;
		this.contract = contract;
	}
	public void addComment(Comment c) {
		comments.add(c);
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Player() {
		this.comments=new LinkedList<Comment>();
	}
	public Player(String name, String surname, String position, int rating, String team) {
		super();
		this.comments=new LinkedList<Comment>();
		this.img= "null";
		this.name = name;
		this.surname = surname;
		this.position = position;
		this.rating = rating;
		this.team = team;
	}
	public Player(String img, String name, String surname, String position, int rating, String team) {
		super();
		this.comments=new LinkedList<Comment>();
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
