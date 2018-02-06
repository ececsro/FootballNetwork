package AlvaroBarroso.Football_Network;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class SessionController {
	private String path = "http://127.0.0.1:8080/";
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
		return "redirect:" + path;
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
	@RequestMapping("/user/new")
	public String newPlayer(Model model, User user) {
		//System.out.println(player.toString());
		//repository.save(player);
		
		System.out.println(user.toString());
		userRepository.save(user);
		return "redirect:"+ path + "/user";
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
	@PostMapping(value = "/players/search")
	public String searchPlayer(Model model, String search) {
		System.out.println("Searching for:" + search);
		model.addAttribute("playerDisplay", proccesPlayers(playerRepository.findByName(search)));
		
		return "playerDisplay";
	}

	@RequestMapping("/scouting")
	public String getScouting(Model model) {
		System.out.println("Scouting");
		//getScoutingList(userRepository.findOne("alvaro"));
		model.addAttribute("playerTest", proccesPlayers(getScoutingList(userRepository.findOne("alvaro"))));
		//System.out.println(proccesPlayers(getScoutingList(userRepository.findOne("alvaro"))));
		return "players";
	}
	
	public List<Player> getScoutingList(User user){
		System.out.println("Getting " + user.getId() + " scouted players.");
		return scoutingRepository.findByUser(user).getPlayers();
	}
	/*
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
			//str = str + getScoutCheck(userRepository.findOne("alvaro"), p);
		}
		str = str + "</div>";
		return str;
	}
	private String proccesPlayer(Player p) {
		String code = "";
		code = code + " <td>" + p.getName()		+ 	"</td> ";
		code = code + " <td>" + p.getSurname() 	+	"</td> ";
		code = code + " <td>" + p.getPosition()	+	"</td> ";
		code = code + " <td>" + p.getRating()	+	"</td> ";
		code = code + " <td>" + p.getTeam() 	+ 	"</td> ";
		code = code + getScoutCheck(userRepository.findOne("alvaro"), p);
		return code;
	}

	private String getScoutCheck(User s, Player p) {
		String code = "";
		code = code + " <td class=\"pScouting\" data-href= \"/addScouting/\" "+p.getId();
		code = code + ">";
		if(isScouted(s, p)) {
			code = code + "<img class=\"imgScout\" src= \"approved.png\"></img>";
		}else {                 
			code = code + "<img class=\"imgScout\" src= \"false.png\"></img>";
		}
		code = code + "</td>";
		return code;
	}
	private boolean isScouted(User s, Player p) {
		if(scoutingRepository.findByUser(s).getPlayers().contains(p)) {
			return true;
		}
		return false;		
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
		code = code + "</div>";
		return code;
	}
	@GetMapping("/approved.png")
	public @ResponseBody byte[] getApprove() throws IOException {
		File fi = new File("src/main/resources/assets/approved.png");
		byte[] fileContent = Files.readAllBytes(fi.toPath());
	    return fileContent;
	}
	@GetMapping("/false.png")
	public @ResponseBody byte[] getFalse() throws IOException {
		File fi = new File("src/main/resources/assets/false.png");
		byte[] fileContent = Files.readAllBytes(fi.toPath());
	    return fileContent;
	}
}
