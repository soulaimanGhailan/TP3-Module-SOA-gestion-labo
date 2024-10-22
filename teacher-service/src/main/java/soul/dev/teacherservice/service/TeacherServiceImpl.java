package soul.dev.teacherservice.service;

import org.springframework.stereotype.Service;
import soul.dev.teacherservice.entities.Teacher;
import soul.dev.teacherservice.models.Researcher;
import soul.dev.teacherservice.repos.TeacherRepo;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepo teacherRepo ;

    public TeacherServiceImpl(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepo.save(teacher);
    }

    @Override
    public Teacher getTeacher(long id) {
        return teacherRepo.findById(id).orElseThrow(() -> new RuntimeException("teacher of id "+ id  + " not found"));
    }

    @Override
    public List<Teacher> getTeachers() {
        return teacherRepo.findAll();
    }


}
