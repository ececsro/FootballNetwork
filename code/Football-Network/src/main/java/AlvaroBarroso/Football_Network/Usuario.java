package AlvaroBarroso.Football_Network;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Usuario {

		private String user;
		private String password;
		
		 String getUser() {
			return user;
		}
		void setUser(String user) {
			this.user = user;
		}
		String getPassword() {
			return password;
		}
		void setPassword(String password) {
			this.password = password;
		}

		
}