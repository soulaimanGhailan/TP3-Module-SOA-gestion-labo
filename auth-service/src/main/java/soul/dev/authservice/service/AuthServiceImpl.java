package soul.dev.authservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.dev.authservice.config.JwtTokenParams;
import soul.dev.authservice.dtos.RegistrationRequestDTO;
import soul.dev.authservice.entities.AppRole;
import soul.dev.authservice.entities.AppUser;
import soul.dev.authservice.repo.RoleRepository;
import soul.dev.authservice.repo.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;

    private JwtTokenParams jwtTokenParams;
    @Value("${jwt.issuer}")
    private String issuer;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, JwtTokenParams jwtTokenParams) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.jwtTokenParams = jwtTokenParams;
    }




    @Override
    public AppRole addRole(String roleName) {
        AppRole appRole=roleRepository.findByRoleName(roleName);
        if(appRole!=null) throw new RuntimeException(String.format("Role %s already exit",roleName));
        appRole=AppRole.builder().roleName(roleName).build();
        return roleRepository.save(appRole);
    }

    @Override
    public AppRole addRoleToUser(String username, String roleName) {
        AppUser appUser=userRepository.findByUsername(username);
        if(appUser==null) throw new RuntimeException(String.format("This username %s do not exist",username));
        AppRole appRole=roleRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException(String.format("This Role %s do not exist",roleName));
        if(appUser.getAppRoles()==null) appUser.setAppRoles(new ArrayList<>());
        appUser.getAppRoles().add(appRole);
        return appRole;
    }


    @Override
    public Map<String,String> generateToken(String username, boolean generateRefreshToken){
        System.out.println("ok");
        AppUser appUser=findUserByUsernameOrEmail(username);
        String scope=appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.joining(" "));
        Map<String,String> idToken=new HashMap<>();
        Instant instant=Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(appUser.getId())
                .issuedAt(instant)
                .expiresAt(instant.plus(generateRefreshToken?jwtTokenParams.shirtAccessTokenTimeout():jwtTokenParams.longAccessTokenTimeout(), ChronoUnit.MINUTES))
                .issuer(issuer)
                .claim("scope",scope)
                .claim("email",appUser.getEmail())
                .claim("firstName",appUser.getFirstName())
                .claim("lastName",appUser.getLastName())
                .claim("username",appUser.getUsername())
                .build();
        String jwtAccessToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        idToken.put("access-token",jwtAccessToken);
        if(generateRefreshToken){
            JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                    .subject(appUser.getId())
                    .issuedAt(instant)
                    .expiresAt(instant.plus(jwtTokenParams.refreshTokenTimeout(), ChronoUnit.MINUTES))
                    .issuer(issuer)
                    .claim("username",appUser.getUsername())
                    .claim("email",appUser.getEmail())
                    .build();
            String jwtRefreshTokenToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtRefreshClaimsSet)).getTokenValue();
            idToken.put("refresh-token",jwtRefreshTokenToken);
        }
        return idToken;
    }

    @Override
    public AppUser register(RegistrationRequestDTO requestDTO) {
        AppUser appUser=userRepository.findByUsername(requestDTO.username());
        if(appUser!=null) throw new RuntimeException("This username is not available");
        if(!requestDTO.password().equals(requestDTO.confirmPassword()))
            throw new RuntimeException("Passwords not match");
        appUser=AppUser.builder()
                .username(requestDTO.username())
                .password(passwordEncoder.encode(requestDTO.password()))
                .firstName(requestDTO.firstName())
                .lastName(requestDTO.lastName())
                .id(UUID.randomUUID().toString())
                .email(requestDTO.email())
                .build();
        AppUser savedAppUser = userRepository.save(appUser);
        addRoleToUser(requestDTO.username(),"USER");
        return appUser;
    }

    @Override
    public AppUser findUserByUsernameOrEmail(String usernameOrEmail) {
        System.out.println(usernameOrEmail);
        AppUser appUser=userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);
        if(appUser==null) throw new RuntimeException("Bad Credentials");
        return appUser;
    }

    @Override
    public AppUser findUserByUserId(String userId) {
        AppUser appUser=userRepository.findById(userId).orElse(null);
        if(appUser==null) throw new RuntimeException(String.format("This username %s do not exist",userId));
        return appUser;
    }
    @Override
    public List<AppRole> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

}
