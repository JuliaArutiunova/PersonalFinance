package by.it_academy.jd2.controller.filter;


import by.it_academy.jd2.controller.utils.JwtTokenHandler;
import by.it_academy.lib.dto.TokenInfoDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isEmpty;


@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JwtTokenHandler jwtTokenHandler;

    public JWTFilter(JwtTokenHandler jwtTokenHandler) {
        this.jwtTokenHandler = jwtTokenHandler;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.split(" ")[1].trim();

        if (!jwtTokenHandler.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenInfoDto tokenInfo = jwtTokenHandler.getTokenInfo(token);

        UUID userId = UUID.fromString(tokenInfo.getId());

        UsernamePasswordAuthenticationToken
                authenticationToken = new UsernamePasswordAuthenticationToken(userId,
                null,
                AuthorityUtils.createAuthorityList("ROLE_" + tokenInfo.getRole()));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
