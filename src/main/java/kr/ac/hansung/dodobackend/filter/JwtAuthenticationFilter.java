package kr.ac.hansung.dodobackend.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import kr.ac.hansung.dodobackend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
//jwt 필터 현재 디버깅을 위해 비활성화
@RequiredArgsConstructor
@WebFilter(
        urlPatterns = {
                //"/api/user/*",
//                "/api/image/*",
//                "/api/do/*",
                //"/api/chat/*"
        }
)
////@Component
@Order(1)
public class JwtAuthenticationFilter implements Filter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);

        // 1. token이 존재하는지 확인
        if (token == null) {
            System.out.println("Token not exist.");
            return;
        }
        // 2. token이 유효한지 확인 아닐경우 throw
        if (!jwtTokenProvider.validateToken(token)){
            System.out.println("Not a valid token : " + token);
            return;
        }
        //System.out.println("Valid Token inputted : " + token);
        chain.doFilter(request, response);
        //System.out.println("필터 호출 후");
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        //System.out.println("token : " +bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            try {
                return bearerToken.substring(7);
            } catch (StringIndexOutOfBoundsException e){
                return null;
            }

        }
        return null;
    }
}
