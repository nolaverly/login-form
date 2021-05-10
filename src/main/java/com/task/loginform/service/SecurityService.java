package com.task.loginform.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
