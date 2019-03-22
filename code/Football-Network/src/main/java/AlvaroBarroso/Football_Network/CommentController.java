package AlvaroBarroso.Football_Network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
		//Remote
		//private String path = "https://192.168.56.1:8060/";
		//Local
//		private String path = "./";
//		private String path = "https://football-network-service:8443/";
		private String path = "https://football-network.minikube.io:8443/";
//		private String path = "https://192.168.56.1:8060/";

	@Autowired
	private PlayerRepository 	playerRepository;
	@Autowired
	private UserRepository 		userRepository;
	@Autowired
	private CommentRepository 	commentRepository;
	
	@PostMapping(value = "/comment/{id}")
	public String addComment(Model model,  @PathVariable Long id, String comment) {
		User us = new User();
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			us = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		System.out.println("New comment for:" + id + " -> " + comment);
		Comment com = new Comment(us.getName(),(int) 0, comment);
		playerRepository.getOne(id).addComment(com);
		commentRepository.save(com);

//		return "redirect:" + "https://192.168.10.20/" + "player/" + id;
		return "redirect:" + path + "player/" + id;
		//return getPlayer(model,id);
		//return "/getplayer/"+id;
	}

	//karma
	@GetMapping("/player/upKarma/{id}/{pid}")
	private String upKarma(Model model, @PathVariable Long id, @PathVariable Long pid ) {
		Comment comment = commentRepository.getOne(id);
		comment.setKarma(comment.getKarma()+1);
		commentRepository.save(comment);

//		return "redirect:" + "https://192.168.10.20/" + "player/" + pid;
		return "redirect:" + path + "player/" + pid;
		//return getPlayer(model,pid);
	}
	@GetMapping("/player/downKarma/{id}/{pid}")
	private String downKarma(Model model, @PathVariable Long id, @PathVariable Long pid) {
		Comment comment = commentRepository.getOne(id);
		comment.setKarma(comment.getKarma()-1);
		commentRepository.save(comment);
//		return "redirect:" + "https://192.168.10.20/" + "player/" + pid;
		return "redirect:" + path + "player/" + pid;
		//return getPlayer(model,pid);
	}

	

}
