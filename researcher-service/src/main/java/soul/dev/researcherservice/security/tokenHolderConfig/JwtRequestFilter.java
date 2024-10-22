package soul.dev.researcherservice.security.tokenHolderConfig;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Extract the JWT token from the Authorization header
        String authHeader = httpRequest.getHeader("Authorization");
        String jwtToken = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); // Extract the token
        }

        TokenContextHolder.setToken(jwtToken);

        try {
            chain.doFilter(request, response);
        } finally {
            TokenContextHolder.clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
