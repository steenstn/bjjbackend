package bjj.controller;

import bjj.database.UserRepository;
import bjj.domain.User;
import bjj.request.UserRegistrationRequest;
import bjj.response.UserRegistrationResponse;
import bjj.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    private UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/register")
    public UserRegistrationResponse registerUser(@RequestBody UserRegistrationRequest userRegistration) {
        User registeredUser = userService.registerUser(userRegistration.getUsername(), userRegistration.getPassword());
        return new UserRegistrationResponse(registeredUser.getUsername());
    }

    @PostMapping("user/login")
    public String login(@RequestBody UserRegistrationRequest user) {
        return userService.login(user.getUsername(), user.getPassword());
    }
}
