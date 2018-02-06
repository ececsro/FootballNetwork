package AlvaroBarroso.Football_Network;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class SessionController {
	@Autowired
	private Usuario usuario;
	
	boolean logged = false;
	
	@Autowired
	private PlayerRepository 	playerRepository;
	@Autowired
	private UserRepository 		userRepository;
	@Autowired
	private ScoutingRepository 	scoutingRepository;
	
	@PostConstruct
	public void init() {
		Player cr7 = new Player("Cristiano", "Ronaldo", "LW", 95, "Real");
		Player m10 = new Player("Lionel", "Messi", "RW", 93, "Barca");
		playerRepository.save(cr7);
		playerRepository.save(m10);
		playerRepository.save(new Player("Gareth", "Bale", "RW", 89, "Real"));
		playerRepository.save(new Player("https://realsport101.com/wp-content/uploads/2017/10/Isco-NIF.png","Francisco", "Alarcón", "CAM", 86, "Real"));
		
		userRepository.save(new User("alvaro", "12345"));
		List<Player> lista = new LinkedList<Player>();
		lista.add(cr7);
		lista.add(m10);
		scoutingRepository.save(new Scouting(userRepository.getOne("alvaro"), lista ));
		
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
		System.out.println(playerRepository.findAll().toString());
		model.addAttribute("name", "Nombre");
		model.addAttribute("password", "Apellido");
		return "user";
	}
	@GetMapping(value = "/getplayer/{id}")
	public String getPlayer(Model model, @PathVariable Long id) {
		System.out.println("Players");
		Player p = playerRepository.findOne(id);
		model.addAttribute("playerDisplay", displayPlayer(p));
		return "playerDisplay";
	}
	@GetMapping(value = "/players")
	public String getPlayers(Model model) {
		System.out.println("Players");
		model.addAttribute("players", playerRepository.findAll());
		model.addAttribute("playerTest", proccesPlayers(playerRepository.findAll()));
		return "players";
	}
	@RequestMapping("/user/new")
	public String newPlayer(Model model, User user) {
		//System.out.println(player.toString());
		//repository.save(player);
		
		System.out.println(user.toString());
		userRepository.save(user);
		return "index";
	}
	@RequestMapping("/scouting")
	public String getScouting(Model model) {
		System.out.println("Scouting");
		//getScoutingList(userRepository.findOne("alvaro"));
		model.addAttribute("playerTest", proccesPlayers(getScoutingList(userRepository.findOne("alvaro"))));
		//System.out.println(proccesPlayers(getScoutingList(userRepository.findOne("alvaro"))));
		return "scouting";
	}
	public List<Player> getScoutingList(User user){
		System.out.println("Getting " + user.getId() + " scouted players.");
		return scoutingRepository.findByUser(user).getPlayers();
	}
	/*
	public void getScoutingList(User user){
		System.out.println(scoutingRepository.findByUser(user));
		}*/
	
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
	private String proccesPlayers(List<Player> players) {
		String str = "<div class =  \"players\" >";
		for(Player p: players) {
			System.out.println(p.toString());
			str = str + " <tr class =\"playerRow\" data-href= \"/getplayer/" +p.getId() +"\" >";
			str = str + proccesPlayer(p);
			str = str + " </tr>";
		}
		str = str + "</div>";
		return str;
	}
	private String proccesPlayer(Player p) {
		String code = "";
		code = code + " <td>" + p.getId() 		+ 	"</td> ";
		code = code + " <td>" + p.getSurname() 	+	"</td> ";
		code = code + " <td>" + p.getPosition()	+	"</td> ";
		code = code + " <td>" + p.getRating()	+	"</td> ";
		code = code + " <td>" + p.getTeam() 	+ 	"</td> ";
		return code;
	}
	private String displayPlayer(Player p) {
		String code="<div class=\"playerDisplay\">";
		if(p.getImg()=="null") {
			code = code +"<img class=\"pImg\" src=\"https://www.iconfinder.com/icons/357492/account_avatar_client_man_member_person_user_profile_icon\"></img>";
		}else {
			code = code +"<img class=\"pImg\" src=\" " + p.getImg() +"></img>";
		}
		code = code + " <a class=\"pName\">" + p.getId() 		+ 	"</a> ";
		code = code + " <a class=\"pSurname\">" + p.getSurname() 	+	"</a> ";
		code = code + " <a class=\"pPosition\">" + p.getPosition()	+	"</a> ";
		code = code + " <a class=\"pRating\">" + p.getRating()	+	"</a> ";
		code = code + " <a class=\"pTeam\">" + p.getTeam() 		+ 	"</a> ";
		code = code + getScoutCheck();
		code = code + "</div>";
		return code;
	}
	private String getScoutCheck() {
		
		return null;
	}
	private boolean isScouted(User s, Player p) {
		if(scoutingRepository.findByUser(s).getPlayers().contains(p)) {
			return true;
		}
		return false;		
	}
	@PostMapping(value = "/players/search")
	public String searchPlayer(Model model, String search) {
		System.out.println("Searching for:" + search);
		model.addAttribute("playerDisplay", proccesPlayers(playerRepository.findByName(search)));
		
		return "playerDisplay";
	}
	/*
	@GetMapping(value = "/getplayer/body")
	public String getBody(Model model) {
		return "body.css";
	}
	@GetMapping(value = "/getplayer/cabecera")
	public String getHeader(Model model) {
		return "cabecera.css";
	}
	*/
}
