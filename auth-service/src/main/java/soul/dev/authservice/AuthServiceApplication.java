package soul.dev.authservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import soul.dev.authservice.config.JwtTokenParams;
import soul.dev.authservice.config.RsaKeyConfig;
import soul.dev.authservice.dtos.RegistrationRequestDTO;
import soul.dev.authservice.service.AuthService;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyConfig.class, JwtTokenParams.class})
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(AuthService authService){
        return args -> {
            authService.addRole("USER");
            authService.addRole("TEACHER");
            authService.addRole("RESEARCHER");
            authService.addRole("ADMIN");
            authService.register(new RegistrationRequestDTO("admin","mohammed","mmmmm","med@mmm.net","1234","1234"));
            authService.register(new RegistrationRequestDTO("prof1","yassine","mmmm","yassine@mmmm.net","1234","1234"));
            authService.register(new RegistrationRequestDTO("prof2","DAAIF","Aziz","daaif@gmail.com","1234","1234"));
            authService.register(new RegistrationRequestDTO("student1","IBRAHIMI","Imane","ibrahimi@gmail.com","1234","1234"));
            authService.register(new RegistrationRequestDTO("student2","CHIHAB","ILYAS","chihab@gmail.com","1234","1234"));
            authService.register(new RegistrationRequestDTO("student3","NADIR","INES","nadir@gmail.com","1234","1234"));
            authService.addRoleToUser("admin","ADMIN");
        };
    }
}
