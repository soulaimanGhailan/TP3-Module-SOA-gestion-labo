package soul.dev.researcherservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.researcherservice.entities.Researcher;

import java.util.List;

public interface ResearcherRepo extends JpaRepository<Researcher, Long> {
    List<Researcher> findResearchersBySupervisorId(Long supervisorId);
}
