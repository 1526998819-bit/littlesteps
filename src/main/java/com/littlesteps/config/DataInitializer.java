package com.littlesteps.config;

import com.littlesteps.model.Child;
import com.littlesteps.model.User;
import com.littlesteps.repository.ChildRepository;
import com.littlesteps.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 默认管理员账号（仅首次启动时创建）
        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123456"))
                    .role("PARENT")
                    .build());
        }

        // 默认孩子（仅首次启动时创建）
        if (childRepository.count() == 0) {
            childRepository.save(Child.builder()
                    .name("小宜蓁")
                    .gender("女宝")
                    .birthDate(LocalDate.of(2026, 1, 22))
                    .build());
        }
    }
}
