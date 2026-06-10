package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserService userService;

    public Tweet save(Long userId, Tweet tweet){
        User user = userService.findById(userId);
        tweet.setUser(user);
        return tweetRepository.save(tweet);
    }

    public List<Tweet> findByUserId(Long userId){
        userService.findById(userId);
        return tweetRepository.findByUserId(userId);
    }

    public Tweet findById(Long id){
        return tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı! ID: " + id));
    }

    public Tweet update(Long id, Tweet updatedTweet){
        Tweet existingTweet = findById(id);
        existingTweet.setContent(updatedTweet.getContent());
        return tweetRepository.save(existingTweet);
    }

    public Tweet delete(Long id, Long userId){
        Tweet tweet = findById(id);

        if(!tweet.getUser().getId().equals(userId)){
            throw new RuntimeException("Bu tweeti silmeye yetkiniz yok! Sadece tweet sahibi silebilir.");
        }

        tweetRepository.delete(tweet);
        return tweet;
    }
}
