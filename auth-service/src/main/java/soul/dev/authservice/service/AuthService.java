package soul.dev.authservice.service;

import soul.dev.authservice.dtos.RegistrationRequestDTO;
import soul.dev.authservice.entities.AppRole;
import soul.dev.authservice.entities.AppUser;

import java.util.List;
import java.util.Map;

public interface AuthService {

    AppRole addRole(String roleName);
    AppRole addRoleToUser(String username, String roleName);
    AppUser findUserByUserId(String userId);
    Map<String,String> generateToken(String username, boolean generateRefreshToken);
    AppUser register(RegistrationRequestDTO requestDTO);
    AppUser findUserByUsernameOrEmail(String usernameOrEmail);
    List<AppRole> getAllRoles();
    List<AppUser> getAllUsers();
}
