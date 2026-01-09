package org.example.testsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.LoginRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request, HttpServletRequest httpRequest) {

        // 1. ID/PW로 "인증 토큰" 생성 (아직 검증 안 된 상태)
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        // 2. 매니저에게 "검증해줘!" 라고 시킴 (여기서 UserDetailsService 호출됨)
        //    실패하면 예외(AuthenticationException) 발생, 성공하면 인증된 객체 리턴
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 3. ★ 검증 통과했으니, 시큐리티 컨텍스트(저장소)에 인증 정보 저장
        //    이 줄이 실행되면 세션(JSESSIONID)이 생성되고 로그인 된 것으로 처리됨
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = httpRequest.getSession(true);


        // 3. ★ 검증 통과했으니, 시큐리티 컨텍스트(저장소)에 인증 정보 저장
        //    이 줄이 실행되면 세션(JSESSIONID)이 생성되고 로그인 된 것으로 처리됨
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return ResponseEntity.ok("로그인 성공! (세션 방식)");
    }
}
