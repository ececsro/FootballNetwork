package AlvaroBarroso.Football_Network;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByAuthor(String author);
}