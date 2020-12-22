package services;

import dto.LoginUserDto;
import dto.RegisterUserDto;
import entities.User;

import javax.ejb.Local;

@Local
public interface UserService {

    public User register(RegisterUserDto newUser);

    public Boolean login(LoginUserDto user);

}
