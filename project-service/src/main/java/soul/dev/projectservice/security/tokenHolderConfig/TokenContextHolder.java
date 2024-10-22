package soul.dev.projectservice.security.tokenHolderConfig;

public class TokenContextHolder {
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    // Method to set the token
    public static void setToken(String token) {
        tokenHolder.set(token);
    }

    // Method to get the token
    public static String getToken() {
        return tokenHolder.get();
    }

    // Method to clear the token (important for memory management)
    public static void clear() {
        tokenHolder.remove();
    }
}
