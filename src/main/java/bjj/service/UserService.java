package bjj.service;

import bjj.domain.User;

public interface UserService {
    User registerUser(String username, String password);
    String login(String username, String password);
    User getAuthenticatedUser(String authHeader);
}
