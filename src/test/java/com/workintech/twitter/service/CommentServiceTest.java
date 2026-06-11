package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.CommentRepository;
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
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TweetService tweetService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentServiceImpl commentService;

    private User tweetSahibi;
    private User yorumSahibi;
    private Tweet tweet;
    private Comment comment;

    @BeforeEach
    void setUp() {

        tweetSahibi = new User();
        tweetSahibi.setId(1L);
        tweetSahibi.setUsername("beyza");

        yorumSahibi = new User();
        yorumSahibi.setId(2L);
        yorumSahibi.setUsername("ahmet");

        tweet = new Tweet();
        tweet.setId(10L);
        tweet.setUser(tweetSahibi);
        tweet.setContent("Bu bir test tweetidir.");

        comment = new Comment();
        comment.setId(100L);
        comment.setTweet(tweet);
        comment.setUser(yorumSahibi);
        comment.setContent("Harika tweet!");
    }

    // 1. TEST: Yorumu silmeye yetkisi olan kişinin (Yorum Sahibi) silme senaryosu
    @Test
    void deleteComment_Success_WhenUserIsCommentOwner() {

        when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));

        commentService.deleteComment(100L, 2L); // 2L -> yorumSahibi'nin ID'si

        verify(commentRepository, times(1)).delete(comment);
    }

    // 2. TEST: Yetkisiz bir kişi yorumu silmeye çalıştığında TwitterException fırlatıyor mu?
    @Test
    void deleteComment_ThrowsException_WhenUserIsNotAuthorized() {

        when(commentRepository.findById(100L)).thenReturn(Optional.of(comment));

        TwitterException exception = assertThrows(TwitterException.class, () -> {
            commentService.deleteComment(100L, 3L);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().contains("Bu yorumu silmeye yetkiniz yok"));

        verify(commentRepository, never()).delete(any(Comment.class));
    }
}