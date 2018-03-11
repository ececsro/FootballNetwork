package AlvaroBarroso.ContactService;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class Controller {
		@Autowired
		private SmtpMailSender smtpMailSender;
		String body;
		
	    public Controller() {
	    	smtpMailSender = new SmtpMailSender();
			//this.body = "Someone is trying to contact you";
		}
	    
		@PostMapping("/send")
	    public void reciever(Model model, @RequestBody Email mail) throws MessagingException {
	    		System.out.println("Trying to contact : " + mail.getTo());
	    		sendEmail(mail);
	    }
	    /*@RequestMapping("/hola")
	    public void test() {
	    		System.out.println("hola");
	    		Gson gson = new Gson();
	    		String jsonInString = gson.toJson(new Email("Manolo", "Paco", "hola que tal todo"));
	    		System.out.println(jsonInString);
	    }*/
	    private void sendEmail(Email mail) throws MessagingException {
	    	smtpMailSender.send(mail.getTo(), mail.getFrom() + " is trying to contact you", mail.getMsg());
    		
		}
}
