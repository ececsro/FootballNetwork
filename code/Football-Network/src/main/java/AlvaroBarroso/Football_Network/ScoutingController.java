package AlvaroBarroso.Football_Network;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScoutingController {
		//Remote
		//private String pathClear = "https://192.168.10.20:8060/";
		//Local
		private String path = "./";

	@Autowired
	private PlayerRepository 	playerRepository;
	@Autowired
	private UserRepository 		userRepository;
	
	

	@GetMapping(value = "/scouting/{id}")
	@CacheEvict(cacheNames="player")
	public String setScouting(Model model, @PathVariable Long id) {
		
		Player p = playerRepository.findOne(id);
		//System.out.println("Change state " + p.getName());
		User us = new User();
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			us = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		if(!isScouted(us, p)) {
			addToScouting(p, us);
		}else {
			removeToScouting(p, us);
		}
		return "redirect:" + "https://192.168.10.20/" + "player/" + id;
		//return getPlayer(model,id);
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
	private String getScoutCheck(User s, Player p) {
		String code = "";
		code = code + " <td class=\"pScouting\"  "+p.getId();
		code = code + ">";
		if(isScouted(s, p)) {
			code = code + "<img class=\"imgScout\" src= \"/approved.png\"></img>";
		}else {                 
			code = code + "<img class=\"imgScout\" src= \"/false.png\"></img>";
		}
		code = code + "</td>";
		return code;
	}

}
