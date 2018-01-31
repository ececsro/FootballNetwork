package Alvaro.Football_Network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SessionController {
	@Autowired
	private Usuario usuario;
	
	@GetMapping(value = "/login")
	public String getLogin(Model model) {
		System.out.println("hola");
		return "login";
	}
	@PostMapping(value = "/checkLogin")
	public String checkLogin(@RequestParam String user, String password) {
		usuario.setUser(user);
		usuario.setPassword(password);
		System.out.println("Usuario: " + usuario.getUser());
		System.out.println("Password: " + usuario.getPassword());
		return "index";
	}
}
