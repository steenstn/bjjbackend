package bjj.database;

import bjj.domain.User;
import bjj.request.UserRegistrationRequest;

public interface UserRepository {
    User registerUser(String username, String password);
    boolean userExists(String username);
    User getUser(String username);
}
