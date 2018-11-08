package bjj.controller;

import bjj.database.UserRepository;
import bjj.domain.User;
import bjj.request.UserRegistrationRequest;
import bjj.response.UserRegistrationResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("user/register")
    public UserRegistrationResponse registerUser(@RequestBody UserRegistrationRequest userRegistration) {
        if(userRepository.userExists(userRegistration.getUsername())) {
            throw new RuntimeException("User with username '" + userRegistration.getUsername() + "' already exists");
        }
        User user = userRepository.registerUser(userRegistration);
        return new UserRegistrationResponse(user.getUsername());
    }

    @GetMapping("user/get")
    public String getUser() {
        return "woop";
    }

    @PostMapping("user/login")
    public String login(@RequestBody UserRegistrationRequest user) {
        return userRepository.login(user);
    }
}
