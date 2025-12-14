package com.example.board.repository;

import com.example.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 1. 제목 또는 내용으로 검색 (유지)
    Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);

    // 2. 제목으로만 검색 (유지)
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    // 3. 내용으로만 검색 (유지)
    Page<Post> findByContentContaining(String keyword, Pageable pageable);

    // 📢 4. 작성자(User)의 username으로 검색 (수정)
    // Post의 author 필드(User 엔티티)의 username 필드를 검색합니다.
    Page<Post> findByAuthorUsernameContaining(String keyword, Pageable pageable); // 👈 필드명 수정 (author.username)

    // 참고: findByWriterContaining 메서드는 Post 엔티티에 writer 필드가 없으므로 삭제하거나 위와 같이 수정해야 합니다.
}