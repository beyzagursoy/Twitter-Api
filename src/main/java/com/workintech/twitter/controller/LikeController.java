package com.workintech.twitter.controller;

import com.workintech.twitter.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like/")
    public ResponseEntity<String> likeTweet(@RequestBody Map<String, Long> body){
        likeService.likeTweet(body.get("tweetId"), body.get("userId"));
        return ResponseEntity.ok("Tweet beğenildi.");
    }

    @PostMapping("/dislike/")
    public ResponseEntity<String> dislikeTweet(@RequestBody Map<String, Long> body) {
        likeService.dislikeTweet(body.get("tweetId"), body.get("userId"));
        return ResponseEntity.ok("Beğeni geri çekildi.");
    }
}
