package soul.dev.teacherservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import soul.dev.teacherservice.entities.Teacher;
import soul.dev.teacherservice.security.RsaKeyConfig;
import soul.dev.teacherservice.service.TeacherService;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfig.class)
public class TeacherServiceApplication {

    @Autowired
    private TeacherService teacherService;

    public static void main(String[] args) {
        SpringApplication.run(TeacherServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            teacherService.addTeacher(Teacher.builder().cne("p1111111111").firstname("mohamed").lastname("ggggg").email("med@gmail.com").researchField("IT").build()) ;
            teacherService.addTeacher(Teacher.builder().cne("p1111111111").firstname("mohamed").lastname("ggggg").email("med@gmail.com").researchField("IT").build()) ;
        };
    }

}
