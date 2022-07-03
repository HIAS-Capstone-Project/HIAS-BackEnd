package com.hias.security;

import com.hias.constant.SecurityConstant;
import com.hias.model.response.TokenResponseDTO;
import com.hias.security.dto.UserDetail;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${hias.app.jwtSecret}")
    private String jwtSecret;

    @Value("${hias.app.accessTokenExpiration}")
    private int accessTokenExpiration;

    private String generateAccessToken(UserDetail userDetail) {

        return Jwts.builder()
                .setSubject((userDetail.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration))
                .claim(SecurityConstant.ROLE, userDetail.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public TokenResponseDTO getTokenResponseDTO(UserDetail userDetail) {
        String accessToken = generateAccessToken(userDetail);
        return TokenResponseDTO.builder()
                .accessToken(accessToken)
                .tokenType(StringUtils.trimAllWhitespace(SecurityConstant.BEARER_TOKEN))
                .build();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(SecurityConstant.AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(SecurityConstant.BEARER_TOKEN)) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    @PostConstruct
    private void k() {
        log.info(jwtSecret + " " + accessTokenExpiration);
    }
}
