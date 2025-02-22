package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Text;
import java.time.LocalDateTime;

public interface TextRepository extends JpaRepository<Text,Long>
{
	void deleteByDateLessThan(LocalDateTime date);
}