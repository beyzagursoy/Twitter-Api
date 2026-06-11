package com.workintech.twitter.service;

import com.workintech.twitter.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User findById(Long id);
    List<User> findAll();
}
