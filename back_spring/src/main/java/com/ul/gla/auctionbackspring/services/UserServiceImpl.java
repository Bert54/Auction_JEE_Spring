package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dao.UserRepository;
import com.ul.gla.auctionbackspring.dto.LoginUserDto;
import com.ul.gla.auctionbackspring.dto.RegisterUserDto;
import com.ul.gla.auctionbackspring.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private HashingService hashService;

    @Autowired
    private UserRepository userDao;

    @Override
    public User signup(RegisterUserDto newUser) {
        String hashedPassword = this.hashService.hashPassword(newUser.getPassword());
        if (this.userDao.findByUsername(newUser.getUsername()) == null) {
            User user = new User(newUser.getUsername(), hashedPassword,newUser.getFirstName(), newUser.getLastName(), newUser.getStreet(), newUser.getCity(), newUser.getPostcode(), newUser.getHouseNumber());
            return this.userDao.save(user);
        }
        return null;
    }

    @Override
    public Boolean login(LoginUserDto user) {
        User userBD = this.userDao.findByUsername(user.getUsername());
        if (userBD != null) {
            return this.hashService.validatePassword(user.getPassword(), userBD.getPassword());
        }
        return null;
    }

    @Override
    public User getUser(String username) {
        return this.userDao.findByUsername(username);
    }

}
