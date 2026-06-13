package com.littlesteps.controller;

import com.littlesteps.dto.ApiResponse;
import com.littlesteps.dto.AuthRequest;
import com.littlesteps.model.User;
import com.littlesteps.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ApiResponse<Map<String, String>> register(@Valid @RequestBody AuthRequest request) {
        log.info("注册请求: username={}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("注册失败: 用户名已存在 -> {}", request.getUsername());
            return ApiResponse.error("用户名已存在");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("PARENT")
                .build();
        userRepository.save(user);
        log.info("注册成功: username={}", user.getUsername());

        return ApiResponse.ok("注册成功", Map.of("username", user.getUsername()));
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@Valid @RequestBody AuthRequest request,
                                                   HttpServletRequest httpRequest) {
        log.info("登录请求: username={}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            log.warn("登录失败: 用户不存在 -> {}", request.getUsername());
            return ApiResponse.error("用户名或密码错误");
        }

        boolean pwMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        log.info("密码验证: 用户={}, 匹配={}", user.getUsername(), pwMatches);

        if (!pwMatches) {
            log.warn("登录失败: 密码错误 -> {}", request.getUsername());
            return ApiResponse.error("用户名或密码错误");
        }

        // Create Spring Security authentication
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Persist session
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return ApiResponse.ok("登录成功", Map.of(
                "username", user.getUsername(),
                "role", user.getRole()
        ));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ApiResponse.ok("已退出登录", null);
    }

    @GetMapping("/me")
    public ApiResponse<Map<String, String>> me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ApiResponse.error("未登录");
        }
        return ApiResponse.ok(Map.of(
                "username", user.getUsername(),
                "role", user.getRole()
        ));
    }
}
