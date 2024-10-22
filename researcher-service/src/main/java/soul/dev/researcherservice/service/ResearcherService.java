package soul.dev.researcherservice.service;

import soul.dev.researcherservice.entities.Researcher;

import java.util.List;

public interface ResearcherService {
    Researcher createResearcher(Researcher researcher , long supervisorId);
    Researcher updateResearcher(Researcher researcher);
    Researcher AssociateProjectWithResearcher(long projectId , long researcherId);
    Researcher getResearcher(long id);
    Researcher deleteResearcher(long id);
    List<Researcher> getAllResearchers();
    List<Researcher> getAllResearchersOfSupervisor(long superVisorId);
}
