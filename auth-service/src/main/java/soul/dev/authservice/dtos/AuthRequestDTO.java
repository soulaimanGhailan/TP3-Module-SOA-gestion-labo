package soul.dev.authservice.dtos;

public record AuthRequestDTO (
   String grantType, String username, String password, boolean withRefreshToken, String refreshToken
){ }
