package soul.dev.researcherservice.restClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import soul.dev.researcherservice.models.Project;
import soul.dev.researcherservice.models.Teacher;

@FeignClient(name = "teacher-service" , url = "http://localhost:8083")
public interface TeacherRestClient {
    @GetMapping("/api/v1/encadrants/{teacherId}")
    Teacher getTeacher(@RequestHeader(value = "Authorization", required = true) String authorizationHeader , @PathVariable long teacherId);

}
