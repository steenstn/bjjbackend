package bjj.service;

import bjj.database.UserRepository;
import bjj.domain.Role;
import bjj.domain.User;
import bjj.security.EncryptionWrapper;
import bjj.security.JwtTokenProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private EncryptionWrapper encryptionWrapper;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           EncryptionWrapper encryptionWrapper) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encryptionWrapper = encryptionWrapper;
    }

    @Override
    public User registerUser(String username, String password) {
        if(userRepository.userExists(username)) {
            throw new RuntimeException("User with username '" + username + "' already exists");
        }
        return userRepository.registerUser(username, password);
    }

    @Override
    public String login(String username, String password) {
        User dbUser = userRepository.getUser(username);
        if(encryptionWrapper.checkPassword(password, dbUser.getPassword())) {
            return jwtTokenProvider.createToken(dbUser.getUsername(), new ArrayList<>(Collections.singleton(Role.ROLE_USER)));
        } else {
            throw new RuntimeException("Unsuccessful login");
        }
    }
}
