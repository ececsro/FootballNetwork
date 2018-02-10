package AlvaroBarroso.Football_Network;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Contract {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int years;
	private int money;
	@OneToOne (mappedBy="contract")
	private Player player;
	
	public Contract(int years, int money, Player player) {
		super();
		this.years = years;
		this.money = money;
		this.player = player;
	}

	public long getId() {
		return id;
	}

	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Contract [id=" + id + ", years=" + years + ", money=" + money + "]";
	}
	public Contract(int years, int money) {
		super();
		this.years = years;
		this.money = money;
	}
	public Contract() {
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
