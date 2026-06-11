package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı! ID: " + commentId));

        if(!comment.getUser().getId().equals(userId)){
            throw new RuntimeException("Bu yorumu güncellemeye yetkiniz yok! Yalnızca yorum sahibi güncelleyebilir.");
        }

        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı! ID: " + commentId));

        Long yorumSahibiId = comment.getUser().getId();
        Long tweetSahibiId = comment.getTweet().getUser().getId();

        if(userId.equals(yorumSahibiId) || userId.equals(tweetSahibiId)){
            commentRepository.delete(comment);
        } else {
            throw new RuntimeException("Bu yorumu silmeye yetkiniz yok! (Yalnızca tweet sahibi veya yorum sahibi silebilir.)");
        }
    }
}
