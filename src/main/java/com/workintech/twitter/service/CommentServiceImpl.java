package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final TweetService tweetService;
    private final UserService userService;

    @Override
    public Comment saveComment(Long tweetId, Long userId, String content) {
        Tweet tweet = tweetService.findById(tweetId);
        User user = userService.findById(userId);

        Comment comment = new Comment();
        comment.setTweet(tweet);
        comment.setUser(user);
        comment.setContent(content);

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, Long userId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new TwitterException("Yorum bulunamadı! ID: " + commentId, HttpStatus.NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)){
            throw new TwitterException("Bu yorumu güncellemeye yetkiniz yok! Yalnızca yorum sahibi güncelleyebilir.", HttpStatus.BAD_REQUEST);
        }

        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new TwitterException("Yorum bulunamadı! ID: " + commentId, HttpStatus.NOT_FOUND));

        Long yorumSahibiId = comment.getUser().getId();
        Long tweetSahibiId = comment.getTweet().getUser().getId();

        if(userId.equals(yorumSahibiId) || userId.equals(tweetSahibiId)){
            commentRepository.delete(comment);
        } else {
            throw new TwitterException("Bu yorumu silmeye yetkiniz yok! (Yalnızca tweet sahibi veya yorum sahibi silebilir.)", HttpStatus.BAD_REQUEST);
        }
    }
}
