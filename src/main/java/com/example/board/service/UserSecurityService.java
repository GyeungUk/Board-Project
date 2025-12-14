package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;


    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // loadUserByUsername 메서드는 Spring Security가 로그인 요청을 처리할 때 호출됩니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. UserRepository를 사용하여 DB에서 사용자 이름으로 User 객체를 조회합니다.
        Optional<User> _user = this.userRepository.findByUsername(username);

        if (_user.isEmpty()) {
            // 사용자가 없으면 예외를 발생시켜 Spring Security에게 로그인 실패를 알립니다.
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        User user = _user.get();

        // 2. User 객체에서 역할(Role) 정보를 가져와 권한(Authorities) 리스트를 생성합니다.
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자의 role 필드를 기반으로 권한을 설정합니다. (예: "ROLE_USER")
        // role 이름은 반드시 "ROLE_" 접두사로 시작해야 합니다.
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        // 3. Spring Security의 UserDetails 객체를 생성하여 반환합니다.
        // 이 객체에는 사용자 이름, 비밀번호, 권한 리스트가 담겨 있습니다.
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
