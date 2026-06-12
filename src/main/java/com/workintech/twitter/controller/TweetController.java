package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.service.TweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3200")
@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @PostMapping()
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet, @RequestParam Long userId) {
        if (tweet.getContent() == null || tweet.getContent().trim().isEmpty()) {
            throw new TwitterException("Tweet içeriği boş olamaz!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(tweetService.save(userId, tweet));
    }

    @GetMapping("/findByUserId")
    public List<Tweet> getTweetsByUserId(@RequestParam Long userId){
        return tweetService.findByUserId(userId);
    }

    @GetMapping("/findById")
    public Tweet getTweetById(@RequestParam Long id){
        return tweetService.findById(id);
    }

    @PutMapping("/{id}")
    public Tweet updateTweet(@PathVariable Long id, @Valid @RequestBody Tweet tweet){
        return tweetService.update(id, tweet);
    }

    @DeleteMapping("/{id}")
    public Tweet deleteTweet(@PathVariable Long id, @RequestParam Long userId){
        return tweetService.delete(id, userId);
    }
}
