package services;

import javax.ejb.Local;

@Local
public interface HashingService {

    public String hashPassword(final String pass);

    public boolean validatePassword(final String userPass, final String storedPass);

}
