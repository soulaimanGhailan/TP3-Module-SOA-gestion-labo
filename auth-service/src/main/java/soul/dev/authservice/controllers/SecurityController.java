package soul.dev.authservice.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import soul.dev.authservice.dtos.AddRoleToUserDTO;
import soul.dev.authservice.dtos.AuthRequestDTO;
import soul.dev.authservice.dtos.RegistrationRequestDTO;
import soul.dev.authservice.entities.AppRole;
import soul.dev.authservice.entities.AppUser;
import soul.dev.authservice.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Slf4j
public class SecurityController {
    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtDecoder jwtDecoder;


    public SecurityController(AuthService authService, AuthenticationManager authenticationManager, JwtDecoder jwtDecoder) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtDecoder = jwtDecoder;
    }
    @PostMapping(value = "/public/auth")
    public ResponseEntity<Map<String,String>> authentication(@RequestBody AuthRequestDTO authRequestDTO, HttpServletRequest request){
            String subject=authRequestDTO.username();
            String grantType = authRequestDTO.grantType();
            if (grantType == null)
                return new ResponseEntity<>(Map.of("errorMessage", "grantType is required"), HttpStatus.UNAUTHORIZED);
            if (grantType.equals("password")) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(subject, authRequestDTO.password())
                );
                System.out.println("ok");
                subject=authentication.getName();
            } else if (grantType.equals("refreshToken")) {
                Jwt decodedRefreshToken = null;
                decodedRefreshToken = jwtDecoder.decode(authRequestDTO.refreshToken());
                subject = decodedRefreshToken.getClaim("username");

            } else {
                return new ResponseEntity<>(Map.of("errorMessage", String.format("GrantType %s not supported", grantType)), HttpStatus.UNAUTHORIZED);
            }
        System.out.println("ok");
            Map<String, String> idToken = authService.generateToken(subject, authRequestDTO.withRefreshToken());
        System.out.println(idToken);
            return ResponseEntity.ok(idToken);
    }


    @PostMapping(path = "/public/register")
    public AppUser register(@RequestBody RegistrationRequestDTO requestDTO){
        return  this.authService.register(requestDTO);
    }


    @GetMapping(path = "/admin/roles")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<AppRole> rolesList(){
        return authService.getAllRoles();
    }
    @PostMapping(path = "/admin/roles")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public AppRole addNewRole(@RequestBody AppRole appRole){
        return authService.addRole(appRole.getRoleName());
    }
    @GetMapping(path = "/admin/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<AppUser> usersList(){
        return authService.getAllUsers();
    }
    @PostMapping(path = "/admin/addRoleToUser")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public AppRole addRoleToUser(@RequestBody AddRoleToUserDTO request){
         return this.authService.addRoleToUser(request.username(),request.roleName());
    }
}
