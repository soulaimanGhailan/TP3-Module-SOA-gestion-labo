package soul.dev.researcherservice.service;

import org.springframework.stereotype.Service;
import soul.dev.researcherservice.entities.Researcher;
import soul.dev.researcherservice.models.Project;
import soul.dev.researcherservice.models.Teacher;
import soul.dev.researcherservice.repo.ResearcherRepo;
import soul.dev.researcherservice.restClients.ProjectRestClient;
import soul.dev.researcherservice.restClients.TeacherRestClient;
import soul.dev.researcherservice.security.tokenHolderConfig.TokenContextHolder;

import javax.transaction.Transactional;
import java.util.List;

@Service @Transactional
public class ResearcherServiceImpl implements ResearcherService {

    private ResearcherRepo researcherRepo ;
    private ProjectRestClient projectRestClient ;
    private TeacherRestClient teacherRestClient ;

    public ResearcherServiceImpl(ResearcherRepo researcherRepo, ProjectRestClient projectRestClient, TeacherRestClient teacherRestClient) {
        this.researcherRepo = researcherRepo;
        this.projectRestClient = projectRestClient;
        this.teacherRestClient = teacherRestClient;
    }

    @Override
    public Researcher createResearcher(Researcher researcher , long supervisorId) {
        Teacher teacher = this.teacherRestClient.getTeacher(getToken(), supervisorId);
        if(teacher == null) {throw new RuntimeException("Teacher not found");} ;
        researcher.setSupervisorId(supervisorId);
        return researcherRepo.save(researcher);
    }

    @Override
    public Researcher updateResearcher(Researcher researcher) {
        return researcherRepo.save(researcher);
    }

    @Override
    public Researcher AssociateProjectWithResearcher(long projectId, long researcherId) {
        Researcher researcher = researcherRepo.findById(researcherId).orElseThrow(() -> new RuntimeException("researcher not found"));
        researcher.setProjectId(projectId);
        return researcherRepo.save(researcher);
    }


    @Override
    public Researcher getResearcher(long id) {
        Researcher researcher = researcherRepo.findById(id).orElseThrow(() -> new RuntimeException("researcher not found"));
        Project project = projectRestClient.getProject(getToken(), researcher.getProjectId());
        researcher.setProject(project);
        return researcher;
    }

    @Override
    public Researcher deleteResearcher(long id) {
        Researcher researcher = researcherRepo.findById(id).orElseThrow(() -> new RuntimeException("researcher not found"));
        // we need to delete the project associated with this student fist
        this.projectRestClient.deleteProjectOfStudent(getToken() , id) ;
        researcherRepo.delete(researcher);
        return researcher;
    }


    @Override
    public List<Researcher> getAllResearchers() {
        return researcherRepo.findAll();
    }

    @Override
    public List<Researcher> getAllResearchersOfSupervisor(long superVisorId) {
        return researcherRepo.findResearchersBySupervisorId(superVisorId);
    }
    private String getToken() {
        return "Bearer " +  TokenContextHolder.getToken() ;
    }

}
