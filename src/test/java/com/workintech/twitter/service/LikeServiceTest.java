package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.LikeRepository;
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
public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private TweetService tweetService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LikeServiceImpl likeService;

    // 5. TEST: Kullanıcı bir tweeti zaten beğenmişse, tekrar beğenmeye çalıştığında TwitterException fırlatıyor mu?
    @Test
    void likeTweet_ThrowsException_WhenAlreadyLiked() {
        Long userId = 1L;
        Long tweetId = 10L;
        when(likeRepository.findByUserIdAndTweetId(userId, tweetId)).thenReturn(Optional.of(new Like()));

        TwitterException exception = assertThrows(TwitterException.class, () -> {
            likeService.likeTweet(tweetId, userId);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Bu tweeti zaten beğendiniz!", exception.getMessage());

        verify(likeRepository, never()).save(any(Like.class));
    }

    // 6. TEST: Kullanıcı beğenmediği bir tweeti dislike etmeye çalışırsa hata fırlatıyor mu?
    @Test
    void dislikeTweet_ThrowsException_WhenNotLikedBefore() {
        Long userId = 1L;
        Long tweetId = 10L;
        when(likeRepository.findByUserIdAndTweetId(userId, tweetId)).thenReturn(Optional.empty());

        TwitterException exception = assertThrows(TwitterException.class, () -> {
            likeService.dislikeTweet(tweetId, userId);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Beğenmediğiniz bir tweetten beğeniyi geri çekemezsiniz!", exception.getMessage());

        verify(likeRepository, never()).delete(any(Like.class));
    }
}