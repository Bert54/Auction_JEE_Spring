package services;

import dao.UserDao;
import dto.LoginUserDto;
import dto.RegisterUserDto;
import entities.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserServiceImpl implements UserService {

    @Inject
    private HashingService hashService;

    @Inject
    private UserDao userdao;

    @Override
    public User register(RegisterUserDto newUser) {
        String hashedPassword = this.hashService.hashPassword(newUser.getPassword());
        List<User> results = this.userdao.find(newUser.getUsername());
        if (results.isEmpty()) {
            User user = new User(newUser.getUsername(), hashedPassword,newUser.getFirstName(), newUser.getLastName(), newUser.getStreet(), newUser.getCity(), newUser.getPostcode(), newUser.getHouseNumber());
            user = this.userdao.save(user);
            return user;
        }
        return null;
    }

    @Override
    public Boolean login(LoginUserDto user) {
        List<User> results = this.userdao.find(user.getUsername());
        if (!results.isEmpty()) {
            String storedPass = results.get(0).getPassword();
            return this.hashService.validatePassword(user.getPassword(), storedPass);
        }
        return null;
    }

    @Override
    public User getUser(String username) {
        List<User> results = this.userdao.find(username);
        if (!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

}
