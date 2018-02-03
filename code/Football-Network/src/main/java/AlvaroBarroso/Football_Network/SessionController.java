package AlvaroBarroso.Football_Network;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class SessionController {
	@Autowired
	private Usuario usuario;
	
	boolean logged = false;
	
	@Autowired
	private PlayerRepository repository;

	@PostConstruct
	public void init() {
		repository.save(new Player("Cristiano", "Ronaldo", "LW", 95, "Real"));
		repository.save(new Player("Lionel", "Messi", "RW", 93, "Barca"));
		repository.save(new Player("Gareth", "Bale", "RW", 89, "Real"));
	}
	@RequestMapping(value = "/home")
	public String getHome(Model model) {
		System.out.println("Home");
		//model.addAttribute("main", "Principal");
		return "index";
	}
	
	@RequestMapping(value = "/user")
	public String getUser(Model model) {
		System.out.println("User");
		/*
		//Check Login
		if(!logged) {
			model.addAttribute("main", "paco");
			return "login";
		}*/
		System.out.println(repository.findAll().toString());
		model.addAttribute("name", "Nombre");
		model.addAttribute("password", "Apellido");
		return "user";
	}
	@GetMapping(value = "/players")
	public String getPlayers(Model model) {
		System.out.println("Players");
		model.addAttribute("players", repository.findAll());
		return "players";
	}
	@RequestMapping("/user/new")
	public String newPlayer(Model model, Player player) {
		System.out.println(player.toString());
		repository.save(player);

		return "index";
	}
	/*
	@GetMapping(value = "/players")
	public String getPlayers(Model model) {
		System.out.println("Players");
		
		return "players";
	}*/
	/*
	@GetMapping(value = "/scouting")
	public String getScouting(Model model) {
		System.out.println("Scouting");
		return "scouting";
	}
	
	@RequestMapping(value = "/checkLogin")
	public String checkLogin(@RequestParam String user, String password) {
		usuario.setUser(user);
		usuario.setPassword(password);
		System.out.println("Succesfull login : " + "Usuario: " + usuario.getUser() + " - Password: " + usuario.getPassword());
		logged = true;
		return "user";
	}*/
	
}
