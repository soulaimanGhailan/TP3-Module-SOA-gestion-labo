package soul.dev.authservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.authservice.entities.AppRole;

public interface RoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String roleName);
}
