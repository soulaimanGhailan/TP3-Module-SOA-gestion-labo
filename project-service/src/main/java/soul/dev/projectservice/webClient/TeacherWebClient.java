package soul.dev.projectservice.webClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import soul.dev.projectservice.models.Teacher;

@FeignClient(name = "teacher-service" , url = "http://localhost:8083")
public interface TeacherWebClient {
    @GetMapping("/api/v1/encadrants/{teacherId}")
    Teacher getTeacher(@RequestHeader(value = "Authorization", required = true) String authorizationHeader , @PathVariable long teacherId);
}
