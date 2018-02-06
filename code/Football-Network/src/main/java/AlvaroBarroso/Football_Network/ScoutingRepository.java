package AlvaroBarroso.Football_Network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoutingRepository extends JpaRepository<Scouting, Long>{
	Scouting findByUser(User user);

}
