package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/retweet")
@RequiredArgsConstructor
public class RetweetController {
    private final TweetService tweetService;

    @PostMapping("/")
    public ResponseEntity<Tweet> retweet(@RequestBody Map<String, Long> body){
        Long tweetId = body.get("tweetId");
        Long userId = body.get("userId");

        Tweet savedRetweet = tweetService.retweet(tweetId, userId);
        return ResponseEntity.ok(savedRetweet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRetweet(@PathVariable Long id, @RequestParam Long userId){
        tweetService.delete(id, userId);
        return ResponseEntity.ok("Retweet başarıyla silindi.");
    }
}
