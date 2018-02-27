package AlvaroBarroso.Football_Network;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	private String email;
	
	public User(String email, String name, String passwordHash, List<String> roles) {
		super();
		this.email = email;
		this.name = name;
		this.passwordHash = new BCryptPasswordEncoder().encode(passwordHash);
		this.roles = roles;
		this.players = new LinkedList<Player>();
	}
	@OneToMany
	private List<Player> players;
	
	public void addPlayer(Player p) {
		if(!playerContains(p.getId())) {
			players.add(p);
		}else {
			System.out.println(p.getId() + " already in this Scouting list");
		}
	}
	public void removePlayer(Player p) {
		if(!playerContains(p.getId())) {
			System.out.println(p.getId() + " not in this Scouting list");
		}else {
			players.remove(p);
		}
	}
	private boolean playerContains(long id) {
		for(Player p:players) {
			if(p.getId()==id)
				return true;
		}
		return false;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	private String name;

	private String passwordHash;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	public User() {
		this.players = new LinkedList<Player>();
	}

	public User(String name, String password, String... roles) {
		this.name = name;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		this.roles = new ArrayList<>(Arrays.asList(roles));
		this.players = new LinkedList<Player>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", passwordHash=" + passwordHash + ", roles=" + roles + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
