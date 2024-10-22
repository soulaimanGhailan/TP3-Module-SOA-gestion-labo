package soul.dev.projectservice.webClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import soul.dev.projectservice.models.Researcher;
import soul.dev.projectservice.models.Teacher;

@FeignClient(name = "researcher-service" , url = "http://localhost:8082")
public interface ResearcherWebClient {
    @PostMapping("/api/v1/researchers/project/{projectId}/{researcherId}")
    Researcher associateProjectWithStudnet(@RequestHeader(value = "Authorization", required = true) String authorizationHeader , @PathVariable long researcherId);
}
