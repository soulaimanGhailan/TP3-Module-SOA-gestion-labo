package soul.dev.projectservice.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import soul.dev.projectservice.entities.Project;
import soul.dev.projectservice.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectRestController {

    private ProjectService projectService;

    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("{projectId}")
    public Project getProject(@PathVariable long projectId) {
        return projectService.getProject(projectId) ;
    }
    @GetMapping("/ofResearcher/{researcherId}")
    public Project getProjectsOfResearcher(@PathVariable long researcherId) {
        return projectService.getProjectOfResearcher(researcherId) ;
    }

    @GetMapping("/ofTeacher/{teacherId}")
    public List<Project> getProjectsOfTeacher(@PathVariable long teacherId) {
        return projectService.getProjectsSupervisedByTeacher(teacherId) ;
    }

    @PostMapping("{supervisorId}")    // can be fetched from jwt token
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    public Project addProject(@RequestBody Project project , @PathVariable long supervisorId) {
        return projectService.addProject(project, supervisorId) ;
    }



    @PutMapping
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    public Project deleteProject(@RequestBody Project project) {
        return projectService.updateProject(project) ;
    }
    @DeleteMapping("{projectId}")
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    public Project deleteProject(@PathVariable long projectId) {
        return projectService.deleteProject(projectId) ;
    }
    @DeleteMapping("/ofResearcher/{researcherId}")
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    public Project deleteProjectOfStudent(@PathVariable long researcherId) {
        return projectService.deleteProject(researcherId) ;
    }

}
