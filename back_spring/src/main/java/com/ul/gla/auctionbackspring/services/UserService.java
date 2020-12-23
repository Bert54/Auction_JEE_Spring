package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dto.LoginUserDto;
import com.ul.gla.auctionbackspring.dto.RegisterUserDto;
import com.ul.gla.auctionbackspring.entities.User;

public interface UserService {

    public User signup(RegisterUserDto newUser);

    public Boolean login(LoginUserDto user);

    public User getUser(String username);

}
