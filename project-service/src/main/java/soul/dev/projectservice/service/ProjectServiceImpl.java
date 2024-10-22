package soul.dev.projectservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.dev.projectservice.entities.Project;
import soul.dev.projectservice.enums.ProjectStatus;
import soul.dev.projectservice.models.Researcher;
import soul.dev.projectservice.models.Teacher;
import soul.dev.projectservice.repos.ProjectRepo;
import soul.dev.projectservice.security.tokenHolderConfig.TokenContextHolder;
import soul.dev.projectservice.webClient.ResearcherWebClient;
import soul.dev.projectservice.webClient.TeacherWebClient;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepo projectRepo ;
    private ResearcherWebClient researcherWebClient ;
    private TeacherWebClient teacherWebClient ;

    public ProjectServiceImpl(ProjectRepo projectRepo, ResearcherWebClient researcherWebClient, TeacherWebClient teacherWebClient) {
        this.projectRepo = projectRepo;
        this.researcherWebClient = researcherWebClient;
        this.teacherWebClient = teacherWebClient;
    }

    @Override
    public Project getProject(long id) {
        return projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public Project addProject(Project project, long supervisor) {
        // check the existance of teacher and researcher
        Teacher teacher = teacherWebClient.getTeacher(getToken(), supervisor);
        if(teacher == null) {throw new RuntimeException("Teacher not found");}
        project.setSupervisorId(supervisor);
        project.setStatus(ProjectStatus.CREATED);
        return projectRepo.save(project);
    }

    @Override
    public Project associateProjectWithResearcher(Project project, long researcherId) {
        Researcher researcher = this.researcherWebClient.associateProjectWithStudnet(getToken(), researcherId);
        if(researcher == null) throw new RuntimeException("Researcher not found");
        project.setResearcherId(researcherId);
        project.setStatus(ProjectStatus.AFFECTED);
        return projectRepo.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public Project deleteProject(long id) {
        Project project = getProject(id);
        projectRepo.deleteById(id);
        return project;
    }

    @Override
    public Project deleteProjectOfStudent(long id) {
        Project project = getProject(id);
        projectRepo.deleteProjectByResearcherId(id);
        return project;
    }

    @Override
    public Project getProjectOfResearcher(long researcherId) {
        return projectRepo.findById(researcherId).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    @Override
    public List<Project> getProjectsSupervisedByTeacher(long teacherId) {
        return projectRepo.findProjectsBySupervisorId(teacherId);
    }

    private static String getToken(){
        return "Bearer " +  TokenContextHolder.getToken()  ;
    }

}
