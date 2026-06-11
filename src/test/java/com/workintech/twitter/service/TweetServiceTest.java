package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.TweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetServiceImpl tweetService;

    private User tweetSahibi;
    private Tweet tweet;

    @BeforeEach
    void setUp() {
        tweetSahibi = new User();
        tweetSahibi.setId(1L);
        tweetSahibi.setUsername("beyza");

        tweet = new Tweet();
        tweet.setId(10L);
        tweet.setUser(tweetSahibi);
        tweet.setContent("JUnit testleri yazıyorum!");
    }

    // 3. TEST: Tweet sahibi kendi tweetini sorunsuzca silebiliyor mu?
    @Test
    void deleteTweet_Success_WhenUserIsTweetOwner() {
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));

        tweetService.delete(10L, 1L);

        verify(tweetRepository, times(1)).delete(tweet);
    }

    // 4. TEST: Başka biri tweeti silmeye kalkarsa sistem engel oluyor mu?
    @Test
    void deleteTweet_ThrowsException_WhenUserIsNotTweetOwner() {
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));

        TwitterException exception = assertThrows(TwitterException.class, () -> {
            tweetService.delete(10L, 5L);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        verify(tweetRepository, never()).delete(any(Tweet.class));
    }
}