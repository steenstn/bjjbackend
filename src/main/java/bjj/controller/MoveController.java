package bjj.controller;

import bjj.database.MoveRepository;
import bjj.database.UserRepository;
import bjj.domain.Move;
import bjj.domain.User;
import bjj.request.MoveRequest;
import bjj.security.JwtTokenProvider;
import bjj.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoveController {

    private UserService userService;
    private MoveRepository moveRepository;


    public MoveController(UserService userService, MoveRepository moveRepository) {
        this.userService = userService;
        this.moveRepository = moveRepository;
    }

    @PostMapping("/moves/new")
    public Move postNewMove(@RequestBody MoveRequest move, @RequestHeader("Authorization") String auth) {
        User user = userService.getAuthenticatedUser(auth);
        return moveRepository.insertMove(move, user);
    }

    @GetMapping("/moves")
    public List<Move> getMoves(@RequestHeader("Authorization") String auth) {
        User user = userService.getAuthenticatedUser(auth);
        return moveRepository.getMovesForUser(user);
    }
}
