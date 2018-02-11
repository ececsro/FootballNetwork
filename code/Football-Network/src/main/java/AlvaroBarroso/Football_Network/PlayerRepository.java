package AlvaroBarroso.Football_Network;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	List<Player> findByName(String name);
	List<Player> findBySurname(String surname);
}