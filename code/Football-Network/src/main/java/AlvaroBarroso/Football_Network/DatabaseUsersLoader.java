package AlvaroBarroso.Football_Network;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DatabaseUsersLoader {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initDatabase() {
    	List<String> singleUser = new LinkedList<String>();
    	singleUser.add("ROLE_USER");
    	List<String> multiUser = new LinkedList<String>();
    	multiUser.add("ROLE_USER");
    	multiUser.add("ROLE_ADMIN");

    	userRepository.save(new User("alvarobm61@hotmail.es","user", "pass", singleUser));
		userRepository.save(new User("alvarobm61@gmail.com", "alvaro", "brujula61", multiUser));
    }

}
