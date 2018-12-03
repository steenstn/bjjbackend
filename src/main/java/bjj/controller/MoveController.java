package bjj.controller;

import bjj.database.MoveRepository;
import bjj.database.UserRepository;
import bjj.domain.Move;
import bjj.domain.User;
import bjj.request.MoveRequest;
import bjj.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveController {

    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private MoveRepository moveRepository;


    public MoveController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, MoveRepository moveRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.moveRepository = moveRepository;
    }

    @PostMapping("/moves/new")
    public Move postNewMove(@RequestBody MoveRequest move, @RequestHeader("Authorization") String auth) {
        User user = userRepository.getUser(jwtTokenProvider.getUsernameFromAuthHeader(auth));
        return moveRepository.insertMove(move, user);
    }
}
