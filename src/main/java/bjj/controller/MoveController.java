package bjj.controller;

import bjj.database.MoveRepository;
import bjj.database.UserRepository;
import bjj.domain.Move;
import bjj.domain.User;
import bjj.request.MoveRequest;
import bjj.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/moves")
    public List<Move> getMoves(@RequestHeader("Authorization") String auth) {
        User user = userRepository.getUser(jwtTokenProvider.getUsernameFromAuthHeader(auth));
        return moveRepository.getMovesForUser(user);
    }
}
