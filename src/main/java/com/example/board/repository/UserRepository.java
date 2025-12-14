package com.example.board.repository;

import com.example.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Security에서 사용자의 ID(username)로 정보를 찾기 위해 필요합니다.
    Optional<User> findByUsername(String username);
}
