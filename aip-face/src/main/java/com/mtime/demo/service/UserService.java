package com.mtime.demo.service;

import com.mtime.demo.mapper.UserMapper;
import com.mtime.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zl on 2015/8/27.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserInfo(){


        User user=userMapper.findUserInfo();
        //User user=null;
        return user;
    }
    
    public List<User> getUsers(User user){
        return userMapper.getUsers(user);
       
    }
    
    public int getUserCount(User user){
        return userMapper.getUserCount(user);
       
    }

}
