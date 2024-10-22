package soul.dev.projectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import soul.dev.projectservice.security.RsaKeyConfig;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfig.class)
public class ProjectServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectServiceApplication.class, args);
    }

}
