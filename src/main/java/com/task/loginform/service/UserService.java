package com.task.loginform.service;

import com.task.loginform.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    
}