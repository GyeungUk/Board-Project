package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    // ⭐ [수정됨] 변수명을 user -> author로 변경
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // DB에는 user_id로 저장됨
    private User author;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // =============================================
    // ⭐ [해결] setAuthor 메소드를 직접 만들어서 오류 해결
    // =============================================

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    // 나머지 수동 Setter (혹시 몰라 유지)
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }
    public Long getId() { return id; }
}