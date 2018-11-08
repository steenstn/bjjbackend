package bjj.database;

import bjj.domain.User;
import bjj.request.UserRegistrationRequest;

public interface UserRepository {
    User registerUser(UserRegistrationRequest user);
    String login(UserRegistrationRequest user);
    boolean userExists(String username);
    User getUser(String username);
}
