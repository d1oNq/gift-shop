package com.giftshop.dao;

import com.giftshop.entity.User;

public interface UserDAO {
    boolean userRegister(User user);

    User userLogin(String email, String password);

    boolean updateProfile(User user);

    boolean updateAddress(User user);

    boolean checkUser(String email) ;
}
