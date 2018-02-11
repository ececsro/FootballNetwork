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
import org.springframework.ui.ModelMap;
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
	@Autowired
	private CommentRepository 	commentRepository;
	@Autowired
	private ContractRepository 	contractRepository;
	User alvaro = new User("alvaro", "12345");
	
	@PostConstruct
	public void init() {
		Player cr7 = new Player("Cristiano", "Ronaldo", "LW", 95, "Real");
		Player m10 = new Player("Lionel", "Messi", "RW", 93, "Barca");
		Contract con = new Contract(5,1900000);
		cr7.setContract(con);
		cr7 = playerRepository.save(cr7);
		m10 = playerRepository.save(m10);
		playerRepository.save(new Player("Gareth", "Bale", "RW", 89, "Real"));
		playerRepository.save(new Player("https://realsport101.com/wp-content/uploads/2017/10/Isco-NIF.png","Francisco", "Alarcón", "CAM", 86, "Real"));
		
		//cr7.addComment(new Comment(alvaro.getId(),(int) 120, "El mejor jugador de la historia"));
		
		
		List<Player> lista = new LinkedList<Player>();
		lista.add(cr7);
		lista.add(m10);
		scoutingRepository.save(new Scouting(alvaro, lista ));
		alvaro = userRepository.save(alvaro);
		//playerRepository.getOne((long) 1).addComment(new Comment("alvaro",(int) 120, "El mejor jugador de la historia"));
		//playerRepository.getOne((long) 1).addComment(new Comment("paco",(int) 120, "El mejor"));
		
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
		System.out.println("Players" + id);
		Player p = playerRepository.findOne(id);
		System.out.println(p.getName());
		model.addAttribute("playerDisplay", displayPlayer(p));
		System.out.println(proccessComments(p));
		model.addAttribute("commentsTable", proccessComments(p));
		model.addAttribute("Contract", proccessContract(p));
		model.addAttribute("id", id);
		model.addAttribute("scoutingDisplay", getScoutingDisplay(p, alvaro));
		return "playerDisplay";
	}
	@GetMapping(value = "/scouting/add/{id}")
	public String setScouting(Model model, @PathVariable Long id) {
		
		Player p = playerRepository.findOne(id);
		System.out.println("Change state " + p.getName());
		if(!isScouted(alvaro, p)) {
			addToScouting(p, alvaro);
		}else {
			removeToScouting(p, alvaro);
		}
		return getPlayer(model,id);
	}
	private String getScoutingDisplay(Player p, User u) {
		String code = "<img class=\"scoutIcon\" src=\"";
		if(isScouted(u, p)) {
			code = code + "/approved.png";
		}else {
			code = code + "/false.png";
		}
		code = code + "\" data-href=\"/scouting/add/" + p.getId() + "\" style=\"width: 50px;height: auto\"> </img>";
		return code;
	}
	private void addToScouting(Player p, User u) {
		scoutingRepository.findByUser(u).addPlayer(p);
		userRepository.save(u);
		//playerRepository.save(arg0)
	}
	private void removeToScouting(Player p, User u) {
		scoutingRepository.findByUser(u).getPlayers().remove(p);
		userRepository.save(u);
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
		model.addAttribute("playerTest", proccesPlayers(playerRepository.findByName(search)));
		return "players";
	}
	@PostMapping(value = "/newcomment/{id}")
	public String addComment(Model model,  @PathVariable Long id, String comment) {
		System.out.println("New comment for:" + id + " -> " + comment);
		Comment com = new Comment(alvaro.getId(),(int) 0, comment);
		playerRepository.getOne(id).addComment(com);
		commentRepository.save(com);
		return getPlayer(model,id);
		//return "/getplayer/"+id;
	}
	@PostMapping(value = "/players/new")
	public String newPlayer(Model model,@RequestParam  String name, String position, String surname, String team, int rating, int money, int years) {
		Player newPlayer = new Player(name, surname, position, rating, team);
		Contract con = new Contract(years, money);
		newPlayer.setContract(con);
		newPlayer = playerRepository.save(newPlayer);
		System.out.println("new Player: " + newPlayer.toString());
		
		return getPlayers(model);
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
	private String proccessContract(Player p) {
		String code = "";
		if(p.getContract()!=null) {
		for(int i=1; i<=p.getContract().getYears(); i++) {
			code = code + "<tr>";
			code = code + "<td>" + i		+ 	"</td> ";
			code = code + "<td>" + p.getContract().getMoney()	+ 	" € </td> ";
			code = code + "</tr>";
		}
		}
		return code;
	}

	private String getScoutCheck(User s, Player p) {
		String code = "";
		code = code + " <td class=\"pScouting\" data-href= \"/addScouting/\" "+p.getId();
		code = code + ">";
		if(isScouted(s, p)) {
			code = code + "<img class=\"imgScout\" src= \"/approved.png\"></img>";
		}else {                 
			code = code + "<img class=\"imgScout\" src= \"/false.png\"></img>";
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
	private String proccessComments(Player p){
		String code = "";
		for(Comment comment : p.getComments())
		{
			code = code + "<tr class=\"comment\" >";
			code = code + "<td>" + "<button class=\"upKarma\"   style=\"background-color:green; height: 40px; width: 40px\"   data-href=\"/player/upKarma/"  + comment.getId() + "/" + p.getId() +"\">"+ "</button>" + "</td>";
			code = code + "<td>" + "<a class=\"karma\">" + comment.getKarma() + "</a>" + "</td>" ;
			code = code + "<td>" + "<button class=\"downKarma\" style=\"background-color:red; height: 40px; width: 40px\" data-href=\"/player/downKarma/"+ comment.getId() + "/" + p.getId() +"\">" + "</button>" + "</td>";
			code = code + "<td>" +  "<a class=\"author\">" + comment.getAuthor() + "</a>" +"</td>";
			code = code + "<td>" + "<a class=\"text\">" + comment.getText() + "</a>"+"</td>";
			code = code + "</tr>";
		}
		return code;
	}
	private String displayPlayer(Player p) {
		String code="<div class=\"playerDisplay\">";
		if(p.getImg().length()<5) {
			code = code +"<img class=\"pImg\" src=\"https://cdn3.iconfinder.com/data/icons/gray-toolbar-3/512/user-128.png\"></img>";
		}else {
			code = code +"<img class=\"pImg\" src=\"" + p.getImg() +"\"></img>";
		}
		code = code + " <a class=\"pRating\">" + p.getRating()	+	"</a> ";
		
		code = code + " <a class=\"pName\">" + p.getName() 		+ 	"</a> ";
		code = code + " <a class=\"pSurname\">" + p.getSurname() 	+	"</a> <br>";
		code = code + " <a class=\"pPosition\">" + p.getPosition()	+	"</a>  ";		
		code = code + " <a class=\"pTeam\">" + p.getTeam() 		+ 	"</a> ";
		code = code + "</div>";
		return code;
	}
	
	@GetMapping("/player/upKarma/{id}/{pid}")
	private String upKarma(Model model, @PathVariable Long id, @PathVariable Long pid ) {
		Comment comment = commentRepository.getOne(id);
		comment.setKarma(comment.getKarma()+1);
		commentRepository.save(comment);
		return getPlayer(model,pid);
	}
	@GetMapping("/player/downKarma/{id}/{pid}")
	private String downKarma(Model model, @PathVariable Long id, @PathVariable Long pid) {
		Comment comment = commentRepository.getOne(id);
		comment.setKarma(comment.getKarma()-1);
		commentRepository.save(comment);
		return getPlayer(model,pid);
	}
	
	//Mapeo de imagenes
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
