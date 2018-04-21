package AlvaroBarroso.Football_Network;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class PlayerController {
		//Remote
		//private String path = "https://192.168.56.1:8060/";
		//Local
		private String path = "./";
		
	private String servicePath = "http://192.168.10.22:8060/";
	
	@Autowired
		private PlayerRepository 	playerRepository;
		@Autowired
		private UserRepository 		userRepository;
		
		@Autowired
		private CommentRepository 	commentRepository;
		@Autowired
		private ContractRepository 	contractRepository;
    @GetMapping("/player/{id}/contact")
	  	public String contact(Model model, @PathVariable Long id) {
	    	
	    	if(playerRepository.getOne(id).getUser()!=null) {
	    	System.out.println("Contactando con :" + id);
	    	
	    	 RestTemplate rt = new RestTemplate();
	    	 String url=servicePath+"send";

	     	String to = playerRepository.getOne(id).getUser().getEmail();
	     	String playerTo = playerRepository.getOne(id).getName();
	     	String from = SecurityContextHolder.getContext().getAuthentication().getName();
	     	String data = "Hola me gustaria contactar con " + playerTo;
	     	
	     	//System.out.println("Contactando con :" + to);
	     	
	    	 Email email = new Email(from, to, data);
	    	 rt.postForLocation(url, email);
	    	}else {
	    		System.out.println("Player without user linked");
	    	}
	    	
	    	/*String body = "Hola";
	    	String to = playerRepository.getOne(id).getUser().getEmail();
	    	smtpMailSender.send(to, "This is a test email", body);*/
			return "redirect:" + "https://192.168.10.20/" + "player/" + id;
	    }
		
    @GetMapping(value = "/player/{id}")
		public String getPlayer(Model model, @PathVariable Long id) {
			System.out.println("Players" + id);
			Player p = playerRepository.findOne(id);
			//System.out.println(p.getName());
			model.addAttribute("playerDisplay", displayPlayer(p));
			//System.out.println(proccessComments(p));
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
		@GetMapping(value = "/player")
	    @Cacheable(cacheNames="player")
		public String getPlayers(Model model) {
			System.out.println("Players");
			model.addAttribute("players", playerRepository.findAll());
			//System.out.println("hallo "+playerRepository.findAll().toString());
			model.addAttribute("playerTest", proccesPlayers(playerRepository.findAll()));
			return "players";
		}
		@PostMapping(value = "/player/search")
		public String searchPlayer(Model model, String search) {
			//System.out.println("Searching for:" + search);
			model.addAttribute("playerTest", proccesPlayers(playerRepository.findByName(search)));
			return "players";
		}
		@PostMapping(value = "/player")
		@CacheEvict(cacheNames="player")
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

			return "redirect:" + path + "player/" + newPlayer.getId();
			//return getPlayers(model);
		}
		String proccesPlayers(List<Player> players) {
			String str = "<div class =  \"players\" >";
			for(Player p: players) {
				//System.out.println(p.toString());
				str = str + " <tr class =\"playerRow\" data-href= \"/player/" +p.getId() +"\" >";
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
		private String getScoutingDisplay(Player p, User u) {
			String code = "<img class=\"scoutIcon\" src=\"";
			if(isScouted(u, p)) {
				code = code + "/approved.png";
			}else {
				code = code + "/false.png";
			}
			code = code + "\" data-href=\"/scouting/" + p.getId() + "\" style=\"width: 50px;height: auto\"> </img>";
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
			if(s == null) {
				return false;
			}else if(s.getPlayers()!=null) {
				if(s.getPlayers().contains(p)) {
					return true;
				}
			}
			
			return false;		
		}

}
