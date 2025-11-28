package MaterialMart.Config;

import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("🟦 JwtFilter: Incoming request → " + request.getRequestURI());

        String header = request.getHeader("Authorization");
        System.out.println("🟦 JwtFilter: Authorization header = " + header);

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {

            String token = header.substring(7);
            System.out.println("🟦 JwtFilter: Extracted Token = " + token);

            if (jwtUtil.validateToken(token)) {

                String username = jwtUtil.getUsername(token);
                System.out.println("🟩 JwtFilter: Token VALID. Username = " + username);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of()   // No roles needed
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } else {
                System.out.println("🟥 JwtFilter: Token INVALID ❌");
            }
        } else {
            System.out.println("🟥 JwtFilter: No token provided");
        }

        filterChain.doFilter(request, response);
    }
}
