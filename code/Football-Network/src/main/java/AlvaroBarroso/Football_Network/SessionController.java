package AlvaroBarroso.Football_Network;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@Controller
public class SessionController {
	private String path = "https://localhost:8443/";
	private String servicePath = "http://127.0.0.1:5/";
	boolean logged = false;

	@Autowired
	private PlayerRepository 	playerRepository;
	@Autowired
	private UserRepository 		userRepository;
	
	@Autowired
	private CommentRepository 	commentRepository;
	@Autowired
	private ContractRepository 	contractRepository;
	User alvaro;
	User user;
	@PostConstruct
	public void init() {
		
		
		
		
		//alvaro = userRepository.findByName("alvaro");
		//user = userRepository.findByName("user");
		/*
		Player cr7 = new Player("Cristiano", "Ronaldo", "LW", 95, "Real", alvaro);
		Player m10 = new Player("Lionel", "Messi", "RW", 93, "Barca", user);
		Contract con = new Contract(5,1900000);
		cr7.setContract(con);
		cr7 = playerRepository.save(cr7);
		m10 = playerRepository.save(m10);
		playerRepository.save(new Player("Gareth", "Bale", "RW", 89, "Real"));
		playerRepository.save(new Player("https://realsport101.com/wp-content/uploads/2017/10/Isco-NIF.png","Francisco", "Alarcón", "CAM", 86, "Real"));
		
		//cr7.addComment(new Comment(alvaro.getId(),(int) 120, "El mejor jugador de la historia"));
		
		System.out.println(userRepository.findAll().toString());
		*/
		
		//alvaro = userRepository.save(alvaro);
		//playerRepository.getOne((long) 1).addComment(new Comment("alvaro",(int) 120, "El mejor jugador de la historia"));
		//playerRepository.getOne((long) 1).addComment(new Comment("paco",(int) 120, "El mejor"));
		
	}
	@RequestMapping(value = "/home")
	public String getHome(Model model) {
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			System.out.println("user   "+SecurityContextHolder.getContext().getAuthentication().getName());
		}
		System.out.println("Home");
		return "redirect:" + path;
	}
	
	//User
	
	@RequestMapping(value = "/user")
	public String getUser(Model model) {
		System.out.println("User");
		
		System.out.println(playerRepository.findAll().toString());
		
		return "user";
	}
	/*@RequestMapping("/user/new")
	public String newPlayer(Model model, User user) {
		//System.out.println(player.toString());
		//repository.save(player);
		
		System.out.println(user.toString());
		userRepository.save(user);
		return "redirect:"+ path + "/user";
	}*/
	
	//Login
	
	/*
    @PostMapping(value = "/login")
    public String login(Model model, @RequestParam String username) {
    	System.out.println(" / " + username);
    	//System.out.println(ses.getId());
    	return "redirect: /home";
    }*/
	/*
	@PostMapping(value = "/login")
    public String login(Model model, @RequestParam String username) {
    	System.out.println(" / " + username);
    	//System.out.println(ses.getId());
    	return "redirect: /home";
    }
	
	
	@PostMapping(value = "/login")
	public String login(Model model) {
		System.out.println(" /  asdxczxc" );
		return "redirect:" + "/home";
		//return getPlayers(model);
	}*/
    @GetMapping("/login")
    public String login() {
    	return "login";
    }
    @GetMapping("/register")
    public String signup() {
    	return "signup";
    }
    @PostMapping("/signup")
    public String signupresult(Model model, @RequestParam  String email, String username, String password) {
    	List<String> singleUser = new LinkedList<String>();
    	singleUser.add("ROLE_USER");
    	User user = new User(email, username, password, singleUser);
    	System.out.println(user.toString());
    	userRepository.save(user);
    	return "redirect:" + path + "home";
    }
    
    @GetMapping("/loginerror")
    public String loginerror() {
    	return "loginerror";
    }
	
	//Players
    @RequestMapping("/getplayer/{id}/contact")
    public String contact(Model model, @PathVariable Long id) {
    	if(playerRepository.getOne(id).getUser().getEmail()!=null) {
    	System.out.println("Contactando con :" + id);
    	
    	 RestTemplate rt = new RestTemplate();
    	 String url="http://127.0.0.1:5/send";

     	String to = playerRepository.getOne(id).getUser().getEmail();
     	String playerTo = playerRepository.getOne(id).getName();
     	String from = SecurityContextHolder.getContext().getAuthentication().getName();
     	String data = "Hola me gustaria contactar con " + playerTo;
     	
     	System.out.println("Contactando con :" + to);
     	
    	 Email email = new Email(from, to, data);
    	 rt.postForLocation(url, email);
    	}else {
    		System.out.println("Player without email adress");
    	}
    	
    	/*String body = "Hola";
    	String to = playerRepository.getOne(id).getUser().getEmail();
    	smtpMailSender.send(to, "This is a test email", body);*/
		return "redirect:" + path + "getplayer/" + id;
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
		User us = new User();
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			us = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		model.addAttribute("scoutingDisplay", getScoutingDisplay(p,us));
		return "playerDisplay";
	}
	@GetMapping(value = "/players")
	public String getPlayers(Model model) {
		System.out.println("Players");
		model.addAttribute("players", playerRepository.findAll());
		System.out.println("hallo "+playerRepository.findAll().toString());
		model.addAttribute("playerTest", proccesPlayers(playerRepository.findAll()));
		return "players";
	}
	@PostMapping(value = "/players/search")
	public String searchPlayer(Model model, String search) {
		System.out.println("Searching for:" + search);
		model.addAttribute("playerTest", proccesPlayers(playerRepository.findByName(search)));
		return "players";
	}
	@PostMapping(value = "/players/new")
	public String newPlayer(Model model,@RequestParam  String name, String position, String surname, String team, int rating, int money, int years) {
		Player newPlayer = new Player(name, surname, position, rating, team);
		Contract con = new Contract(years, money);
		newPlayer.setContract(contractRepository.save(con));
		User us = new User();
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			us = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		newPlayer.setUser(us);
		newPlayer = playerRepository.save(newPlayer);
		
		System.out.println("new Player: " + newPlayer.toString());

		return "redirect:" + path + "getplayer/" + newPlayer.getId();
		//return getPlayers(model);
	}
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
		User us = new User();
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			us = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		code = code + getScoutCheck(us, p);
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
	//Scouting
	
	@GetMapping(value = "/scouting/add/{id}")
	public String setScouting(Model model, @PathVariable Long id) {
		
		Player p = playerRepository.findOne(id);
		System.out.println("Change state " + p.getName());
		User us = new User();
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			us = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		if(!isScouted(us, p)) {
			addToScouting(p, us);
		}else {
			removeToScouting(p, us);
		}
		return "redirect:" + path + "getplayer/" + id;
		//return getPlayer(model,id);
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
		/*
		if(.findByUser(u)!=null) {
			scoutingRepository.findByUser(u).addPlayer(p);
			userRepository.save(u);
		}else {
			//scoutingRepository.findByUser(u).addPlayer(p);
			//scoutingRepository.findByUser(u).setPlayers(new LinkedList<Player>());
			List<Player> lista = new LinkedList<Player>();
			lista.add(p);
			Scouting sc = scoutingRepository.save(new Scouting(u, lista ));
			
			System.out.println("debug");
			System.out.println(u.toString());
			List<Player> lista = new LinkedList<Player>();
			lista.add(p);
			Scouting sc = new Scouting(u, lista );
			//sc.setId(2);
			System.out.println(sc.toString());
			scoutingRepository.save(sc);
			//userRepository.save(u);
		}*/
		u.addPlayer(p);
		userRepository.save(u);
		//playerRepository.save(arg0)
	}
	private void removeToScouting(Player p, User u) {
		/*
		if(scoutingRepository.findByUser(u)!=null) {
		}else {
			Scouting scou = new Scouting();
			scoutingRepository.save(scou);
			List<Player> lista = new LinkedList<Player>();
			lista.add(p);
			scoutingRepository.save(new Scouting(u, lista ));
		}
		scoutingRepository.findByUser(u).getPlayers().remove(p);
		userRepository.save(u);
		*/
		u.removePlayer(p);
		userRepository.save(u);
	}
	@RequestMapping("/scouting")
	public String getScouting(Model model) {
		System.out.println("Scouting");
		//getScoutingList(userRepository.findOne("alvaro"));
		User us = new User();
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			us = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		model.addAttribute("playerTest", proccesPlayers(getScoutingList(us)));
		//System.out.println(proccesPlayers(getScoutingList(userRepository.findOne("alvaro"))));
		return "players";
	}
	public List<Player> getScoutingList(User user){
		System.out.println("Getting " + user.getId() + " scouted players.");
		if(user.getPlayers()!=null) {
			return user.getPlayers();
		}else {
			return new LinkedList<Player>();
		}
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
		if(s == null) {
			return false;
		}else if(s.getPlayers()!=null) {
			if(s.getPlayers().contains(p)) {
				return true;
			}
		}
		
		return false;		
	}

	
	//Comments
	
	@PostMapping(value = "/newcomment/{id}")
	public String addComment(Model model,  @PathVariable Long id, String comment) {
		User us = new User();
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			us = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		System.out.println("New comment for:" + id + " -> " + comment);
		Comment com = new Comment(us.getName(),(int) 0, comment);
		playerRepository.getOne(id).addComment(com);
		commentRepository.save(com);

		return "redirect:" + path + "/getplayer/" + id;
		//return getPlayer(model,id);
		//return "/getplayer/"+id;
	}
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
	
	
	//Karma
	
	@GetMapping("/player/upKarma/{id}/{pid}")
	private String upKarma(Model model, @PathVariable Long id, @PathVariable Long pid ) {
		Comment comment = commentRepository.getOne(id);
		comment.setKarma(comment.getKarma()+1);
		commentRepository.save(comment);

		return "redirect:" + path + "getplayer/" + pid;
		//return getPlayer(model,pid);
	}
	@GetMapping("/player/downKarma/{id}/{pid}")
	private String downKarma(Model model, @PathVariable Long id, @PathVariable Long pid) {
		Comment comment = commentRepository.getOne(id);
		comment.setKarma(comment.getKarma()-1);
		commentRepository.save(comment);
		return "redirect:" + path + "getplayer/" + pid;
		//return getPlayer(model,pid);
	}

	

	

	
	//Mapeo de imagenes
	
	/*
	@GetMapping("/approved.png")
	public @ResponseBody byte[] getApprove() throws IOException {
		File fi = new File("src/main/resources/static/assets/approved.png");
		byte[] fileContent = Files.readAllBytes(fi.toPath());
	    return fileContent;
	}
	@GetMapping("/false.png")
	public @ResponseBody byte[] getFalse() throws IOException {
		File fi = new File("src/main/resources/static/assets/false.png");
		byte[] fileContent = Files.readAllBytes(fi.toPath());
	    return fileContent;
	}
	@GetMapping("/email.png")
	public @ResponseBody byte[] getMail() throws IOException {
		File fi = new File("src/main/resources/static/assets/email.png");
		byte[] fileContent = Files.readAllBytes(fi.toPath());
	    return fileContent;
	}
	*/
}
