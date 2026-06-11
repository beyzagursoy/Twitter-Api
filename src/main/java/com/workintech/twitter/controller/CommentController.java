package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<Comment> createComment(@RequestBody Map<String, Object> body){
        Long tweetId = ((Number) body.get("tweetId")).longValue();
        Long userId = ((Number) body.get("userId")).longValue();
        String content = (String) body.get("content");

        Comment savedComment = commentService.saveComment(tweetId, userId, content);
        return ResponseEntity.ok(savedComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Map<String, Object> body){
        Long userId = ((Number) body.get("userId")).longValue();
        String content = (String) body.get("content");

        Comment updateComment = commentService.updateComment(id, userId, content);
        return ResponseEntity.ok(updateComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, @RequestParam Long userId){
        commentService.deleteComment(id, userId);
        return ResponseEntity.ok("Yorum başarıyla silindi.");
    }
}
