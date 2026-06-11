package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final TweetService tweetService;
    private final UserService userService;

    @Override
    public void likeTweet(Long tweetId, Long userId) {
        Optional<Like> existingLike = likeRepository.findByUserIdAndTweetId(userId, tweetId);
        if(existingLike.isPresent()){
            throw new RuntimeException("Bu tweeti zaten beğendiniz!");
        }

        Tweet tweet = tweetService.findById(tweetId);
        User user = userService.findById(userId);

        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);

        likeRepository.save(like);
    }

    @Override
    public void dislikeTweet(Long tweetId, Long userId) {
        Like like = likeRepository.findByUserIdAndTweetId(userId, tweetId)
                .orElseThrow(() -> new RuntimeException("Beğenmediğiniz bir tweetten beğeniyi geri çekemezsiniz!"));

        likeRepository.delete(like);
    }
}
