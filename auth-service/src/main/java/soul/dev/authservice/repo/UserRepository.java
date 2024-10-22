package soul.dev.authservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.authservice.entities.AppUser;

import java.util.List;

public interface UserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
    AppUser findByUsernameOrEmail(String username, String email);
    List<AppUser> findByUsernameContains(String keyWord);

}
