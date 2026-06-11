package com.workintech.twitter.service;

import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new TwitterException("Kullanıcı bulunamadı! ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
}
