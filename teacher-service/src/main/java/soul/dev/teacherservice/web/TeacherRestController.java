package soul.dev.teacherservice.web;

import org.springframework.web.bind.annotation.*;
import soul.dev.teacherservice.entities.Teacher;
import soul.dev.teacherservice.models.Researcher;
import soul.dev.teacherservice.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/encadrants")
public class TeacherRestController {

    private TeacherService teacherService;

    public TeacherRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getAll() {
       return teacherService.getTeachers()  ;
    }

    @GetMapping("{teacherId}")
    public Teacher getById(@PathVariable long teacherId) {
        return teacherService.getTeacher(teacherId) ;
    }



    @PostMapping
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }



}
