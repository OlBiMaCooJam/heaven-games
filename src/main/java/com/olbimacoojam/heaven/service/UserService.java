package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
    }
}