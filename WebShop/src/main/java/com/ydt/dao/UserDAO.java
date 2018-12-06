package com.ydt.dao;

import com.ydt.entity.Users;

import java.util.List;

public interface UserDAO {
    public List<Users> getAllUsers() ;
    public Users getUserById(int id);
}
