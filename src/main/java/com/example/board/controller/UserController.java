package com.example.board.controller;

import com.example.board.domain.User;
import com.example.board.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ⭐ [이 부분이 없어서 에러가 난 것입니다]
    // 생성자를 통해 변수들을 강제로 채워주는 코드입니다.
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 페이지 보여주기
    @GetMapping("/signup")
    public String signupForm() {
        return "signup"; // signup.html 파일을 찾아갑니다
    }

    // 회원가입 기능 처리
    @PostMapping("/signup")
    public String signup(String username, String password) {
        // 이미 있는 아이디인지 확인
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/signup?error"; // 중복이면 에러 표시
        }

        User user = new User();
        user.setUsername(username);
        // 비밀번호를 암호화해서 넣기 (필수)
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user); // DB에 저장

        return "redirect:/login"; // 성공하면 로그인 페이지로
    }
}