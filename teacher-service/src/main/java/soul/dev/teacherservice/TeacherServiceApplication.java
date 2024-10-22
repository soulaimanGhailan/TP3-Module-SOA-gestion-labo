package soul.dev.teacherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import soul.dev.authservice.config.RsaKeyConfig;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfig.class)
public class TeacherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherServiceApplication.class, args);
    }

}
