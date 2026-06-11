package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;

public interface CommentService {
    Comment saveComment(Long tweetId, Long userId, String content);
    Comment updateComment(Long commentId, Long userId, String newContent);
    void deleteComment(Long commentId, Long userId);
}
