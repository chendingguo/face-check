package com.mtime.demo.mapper;

import java.util.List;

import com.mtime.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    public User findUserInfo();
    
    public List<User> getUsers(User user);
    
    public int getUserCount(User user);
    	
    
}
