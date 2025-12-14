package com.example.board.controller;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // ⭐ [해결] 직접 생성자를 만들어서 변수 초기화 문제를 100% 해결
    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 목록 보기
    @GetMapping
    public String list(Model model,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String keyword) {

        Page<Post> postPage = postRepository.findAll(pageable);
        model.addAttribute("postPage", postPage);
        return "post-list";
    }

    // 상세 보기
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        model.addAttribute("post", post);
        return "post-detail";
    }

    // 글 작성 페이지
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post-new";
    }

    // 글 저장
    @PostMapping
    public String createPost(@ModelAttribute Post post, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없음"));

        post.setAuthor(user); // 이제 에러 안 남 (Post.java에 setter 추가했으므로)

        postRepository.save(post);
        return "redirect:/posts";
    }

    // 수정 페이지
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        model.addAttribute("post", post);
        return "post-edit";
    }

    // 수정 처리
    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable Long id, @ModelAttribute Post updatedPost) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        postRepository.save(post);
        return "redirect:/posts/" + id;
    }

    // 삭제 처리
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/posts";
    }
}