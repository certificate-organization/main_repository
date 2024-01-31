package com.start.st.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PasswordValidationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();
        if (authentication == null || !authentication.isAuthenticated() || !isPasswordVerified(authentication, session)) {
            // 비밀번호 검증 상태가 아니라면 POST 요청으로 받은 비밀번호를 검증합니다.
            if (isValidPassword(password)) {
                // 비밀번호가 올바르다면 세션에 인증된 사용자로 표시합니다.
                session.setAttribute("authenticated", true);
            } else {
                // 비밀번호가 올바르지 않다면 비밀번호 확인 페이지로 리디렉션합니다.
                response.sendRedirect(request.getContextPath() + "/member/passwordConfirm");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
    private boolean isPasswordVerified(Authentication authentication, HttpSession session) {
        // 사용자의 인증 정보에서 사용자명(또는 ID)를 가져옵니다.
        String username = authentication.getName();
        // 세션에서 사용자의 비밀번호 검증 상태를 확인합니다.
        Boolean passwordVerified = (Boolean) session.getAttribute(username + "_password_verified");
        // 세션에서 비밀번호 검증 상태를 가져와서 반환합니다.
        return passwordVerified != null && passwordVerified;
    }
}
