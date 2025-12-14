package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 📢 1. 작성자 설정 로직 추가
    public Post save(Post post, User author) {
        post.setAuthor(author); // 👈 Post 엔티티에 User 객체를 작성자로 설정
        return postRepository.save(post);
    }

    // 2. 검색 기능을 포함한 목록 조회 (Controller에서 호출되는 주 메서드)
    public Page<Post> findAllBySearch(String searchType, String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty() || searchType == null || searchType.trim().isEmpty()) {
            return postRepository.findAll(pageable);
        }

        switch (searchType) {
            case "title":
                return postRepository.findByTitleContaining(keyword, pageable);
            case "content":
                return postRepository.findByContentContaining(keyword, pageable);
            case "writer":
                // 📢 2. PostRepository의 수정된 메서드 이름에 맞춥니다.
                // Post 엔티티의 author 필드(User 객체)의 username으로 검색합니다.
                return postRepository.findByAuthorUsernameContaining(keyword, pageable);
            case "titleOrContent":
                return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
            default:
                return postRepository.findAll(pageable);
        }
    }


    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElse(null);
    }

    public void update(Long id, Post postData) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글이 없습니다. id=" + id));
        post.setTitle(postData.getTitle());
        post.setContent(postData.getContent());
        // 📢 작성자 정보는 업데이트 시 변경하지 않으므로 author 필드에 대한 추가 작업은 생략합니다.
        postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}