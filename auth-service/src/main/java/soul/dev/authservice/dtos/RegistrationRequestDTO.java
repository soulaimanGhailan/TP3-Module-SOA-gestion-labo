package soul.dev.authservice.dtos;


public record RegistrationRequestDTO(String username, String firstName, String lastName, String email, String password, String confirmPassword) {
}
