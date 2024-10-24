package soul.dev.researcherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import soul.dev.researcherservice.security.RsaKeyConfig;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfig.class)
@EnableFeignClients
public class ResearcherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearcherServiceApplication.class, args);
    }

}
