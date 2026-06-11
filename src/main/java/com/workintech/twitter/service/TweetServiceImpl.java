package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{
    private final TweetRepository tweetRepository;
    private final UserService userService;

    @Override
    public Tweet save(Long userId, Tweet tweet){
        User user = userService.findById(userId);
        tweet.setUser(user);
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findByUserId(Long userId){
        userService.findById(userId);
        return tweetRepository.findByUserId(userId);
    }

    @Override
    public Tweet findById(Long id){
        return tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı! ID: " + id));
    }

    @Override
    public Tweet update(Long id, Tweet updatedTweet){
        Tweet existingTweet = findById(id);
        existingTweet.setContent(updatedTweet.getContent());
        return tweetRepository.save(existingTweet);
    }

    @Override
    public Tweet delete(Long id, Long userId){
        Tweet tweet = findById(id);

        if(!tweet.getUser().getId().equals(userId)){
            throw new RuntimeException("Bu tweeti silmeye yetkiniz yok! Sadece tweet sahibi silebilir.");
        }

        tweetRepository.delete(tweet);
        return tweet;
    }

    //retweet
    @Override
    public Tweet retweet(Long tweetId, Long userId) {
        Tweet originalTweet = findById(tweetId);
        User user = userService.findById(userId);

        Tweet retweet = new Tweet();
        retweet.setUser(user);
        retweet.setOriginalTweet(originalTweet);
        retweet.setContent(null);

        return tweetRepository.save(retweet);
    }

    @Override
    public void deleteRetweet(Long retweetId, Long userId) {
        Tweet retweet = findById(retweetId);

        if(retweet.getOriginalTweet() == null){
            throw new RuntimeException("Bu normal bir tweet, retweet silme endpoint'inden silinemez!");
        }

        if(!retweet.getUser().getId().equals(userId)){
            throw new RuntimeException("Bu retweet'i silmeye yetkiniz yok! Sadece retweet sahibi silebilir.");
        }

        tweetRepository.delete(retweet);
    }
}
