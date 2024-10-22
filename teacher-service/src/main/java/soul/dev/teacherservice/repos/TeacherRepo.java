package soul.dev.teacherservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.teacherservice.entities.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
}
