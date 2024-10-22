package soul.dev.projectservice.service;

import soul.dev.projectservice.entities.Project;

import java.util.List;

public interface ProjectService {
    Project getProject(long id);
    Project addProject(Project project , long supervisor);
    Project associateProjectWithResearcher(Project project , long researcherId);
    Project updateProject(Project project);
    Project deleteProject(long id);
    Project deleteProjectOfStudent(long id);
    Project getProjectOfResearcher(long researcherId);
    List<Project> getAllProjects();
    List<Project> getProjectsSupervisedByTeacher(long teacherId);

}
