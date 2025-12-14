package com.example.board.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity

@Table(name = "site_user")
public class User {

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String role;

    // 📢 생성자 (생략하지 않고 있다면 유지)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ===================================
    // 📢 GETTER (이전 코드를 기반으로 유지)
    // ===================================
    public String getRole() {
        return role;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    // ===================================
    // 📢 SETTER 추가 (필수!)
    // ===================================
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
}