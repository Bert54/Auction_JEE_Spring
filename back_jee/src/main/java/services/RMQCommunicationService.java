package services;

import javax.ejb.Local;

@Local
public interface RMQCommunicationService {

    public int sendOrder(String id);

}
