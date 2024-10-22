package soul.dev.researcherservice.restClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import soul.dev.researcherservice.entities.Researcher;
import soul.dev.researcherservice.models.Project;

@FeignClient(name = "project-service" , url = "http://localhost:8081")
public interface ProjectRestClient {
    @DeleteMapping("/api/v1/projects/ofResearcher/{researcherId}")
    Researcher deleteProjectOfStudent(@RequestHeader(value = "Authorization", required = true) String authorizationHeader , @PathVariable long researcherId);
    @GetMapping("/api/v1/projects/{projectId}")
    Project getProject(@RequestHeader(value = "Authorization", required = true) String authorizationHeader , @PathVariable long projectId);
}
