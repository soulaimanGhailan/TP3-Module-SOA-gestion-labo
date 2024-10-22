package soul.dev.projectservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.projectservice.entities.Project;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    Project findProjectByResearcherId(long researcherId);
    List<Project> findProjectsBySupervisorId(long supervisorId);
    void deleteProjectByResearcherId(long researcherId);
}
