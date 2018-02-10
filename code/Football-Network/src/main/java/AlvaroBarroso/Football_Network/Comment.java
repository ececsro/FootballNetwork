package AlvaroBarroso.Football_Network;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String author;
	
	private int karma;
	private String text;
	
	
	public Comment() {}
	public Comment(String author, int karma, String text) {
		super();
		this.author = author;
		this.karma = karma;
		this.text = text;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", author=" + author +", karma=" + karma + ", text=" + text
				+ "]";
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getKarma() {
		return karma;
	}
	public void setKarma(int karma) {
		this.karma = karma;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getId() {
		return id;
	}
}
