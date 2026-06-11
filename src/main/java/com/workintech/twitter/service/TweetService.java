package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;

import java.util.List;

public interface TweetService {
    Tweet save(Long userId, Tweet tweet);
    List<Tweet> findByUserId(Long userId);
    Tweet findById(Long id);
    Tweet update(Long id, Tweet updatedTweet);
    Tweet delete(Long id, Long userId);
}
