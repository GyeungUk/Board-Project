package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// import lombok.RequiredArgsConstructor; // 이걸 지우거나 주석 처리하세요.

@Service
// @RequiredArgsConstructor // 이것도 지우거나 주석 처리하세요.
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    // ⭐ [추가] 직접 생성자를 만들어서 주입받습니다 (Lombok 대신)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}