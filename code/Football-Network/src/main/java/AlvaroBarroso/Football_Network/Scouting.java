package AlvaroBarroso.Football_Network;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Scouting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@OneToOne (cascade = CascadeType.MERGE)
	private User user;
	@OneToMany
	private List<Player> players = new ArrayList<>();
	
	public void addPlayer(Player p) {
		if(!playerContains(p.getId())) {
			players.add(p);
		}else {
			System.out.println(p.getId() + " already in this Scouting list");
		}
	}
	private boolean playerContains(long id) {
		for(Player p:players) {
			if(p.getId()==id)
				return true;
		}
		return false;
	}
	public Scouting(User user, List<Player> players) {
		super();
		this.user = user;
		this.players = players;
	}
	public Scouting() {
		super();
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	@Override
	public String toString() {
		return "Scouting [id=" + id + ", user=" + user + ", players=" + players + "]";
	}

}
