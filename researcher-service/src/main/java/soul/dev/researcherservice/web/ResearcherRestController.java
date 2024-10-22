package soul.dev.researcherservice.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import soul.dev.researcherservice.entities.Researcher;
import soul.dev.researcherservice.service.ResearcherService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/researchers")
public class ResearcherRestController {
    private ResearcherService researcherService;

    public ResearcherRestController(ResearcherService researcherService) {
        this.researcherService = researcherService;
    }

    @GetMapping
    public List<Researcher> getAllResearchers() {
        return researcherService.getAllResearchers() ;
    }

    @GetMapping("{id}")
    public Researcher getResearcherById(@PathVariable int id) {
        return researcherService.getResearcher(id) ;
    }

    @GetMapping("/supervisor/{supervisorId}")
    public List<Researcher> getAllResearchersOfSupervisor(@PathVariable long supervisorId) {
        return researcherService.getAllResearchersOfSupervisor(supervisorId) ;
    }

    @PostMapping("{supervisorId}")
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    public Researcher addResearcher(@RequestBody Researcher researcher , @PathVariable long supervisorId) {
        return this.researcherService.createResearcher(researcher , supervisorId) ;
    }

    @PostMapping("/project/{projectId}/{researcherId}")
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    public Researcher AssociateProjectWithResearcher(@PathVariable long projectId , @PathVariable long researcherId) {
        return this.researcherService.AssociateProjectWithResearcher(projectId , researcherId) ;
    }

    @DeleteMapping("{researcherId}")
    @PreAuthorize("hasAuthority('SCOPE_TEACHER')")
    public Researcher deleteResearcher(@PathVariable long researcherId) {
        return this.researcherService.deleteResearcher(researcherId) ;
    }
}
