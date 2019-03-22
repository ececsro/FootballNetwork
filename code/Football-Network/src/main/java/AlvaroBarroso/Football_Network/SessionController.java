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
	
	//Remote
	//private String path = "https://192.168.56.1:8060/";
	//Local

//	private String path = "./";
//	private String path = "https://localhost:8443/";
//	private String path = "https://192.168.56.1:8060/";
//	private String path = "https://football-network-service:8443/";
//	private String path = "https://192.168.99.100:31708/";
//	private String path = "https://football-network.minikube.io:8443/";
    private String path = "http://csro-football-network:8443/";

	
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
		
		
		//alvaro = userRepository.save(alvaro);
		//playerRepository.getOne((long) 1).addComment(new Comment("alvaro",(int) 120, "El mejor jugador de la historia"));
		//playerRepository.getOne((long) 1).addComment(new Comment("paco",(int) 120, "El mejor"));
		*/
	}
	@RequestMapping(value = "/home")
	public String getHome(Model model) {
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			//System.out.println("user   "+SecurityContextHolder.getContext().getAuthentication().getName());
		}
		System.out.println("Home");
		return "redirect:" + path;
	}
	
	//User
	
	@RequestMapping(value = "/user")
	public String getUser(Model model) {
		System.out.println("User");
		
		//System.out.println(playerRepository.findAll().toString());
		
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

    @GetMapping("/login")
    public String login() {
    	return "login";
    }
    @GetMapping("/signup")
    public String signup() {
    	return "signup";
    }
    @PostMapping("/signup")
    public String signupresult(Model model, @RequestParam  String email, String username, String password) {
    	List<String> singleUser = new LinkedList<String>();
    	singleUser.add("ROLE_USER");
    	User user = new User(email, username, password, singleUser);
    	//System.out.println(user.toString());
    	userRepository.save(user);
    	return "redirect:" + path;
    }
    @GetMapping("/loginerror")
    public String loginerror() {
    	return "loginerror";
    }
	
}
