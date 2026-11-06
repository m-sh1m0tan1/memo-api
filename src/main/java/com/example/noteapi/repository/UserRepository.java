package com.example.noteapi.repository;

import com.example.noteapi.dto.User;
import com.example.noteapi.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final UserMapper userMapper;

    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }
}
