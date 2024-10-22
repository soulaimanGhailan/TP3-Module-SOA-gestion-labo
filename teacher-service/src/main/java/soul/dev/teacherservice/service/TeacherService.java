package soul.dev.teacherservice.service;

import soul.dev.teacherservice.entities.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher addTeacher(Teacher teacher);
    Teacher getTeacher(long id);
    List<Teacher> getTeachers();

}
