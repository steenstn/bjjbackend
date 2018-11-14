package bjj.controller;

import bjj.database.TrainingSessionRepository;
import bjj.database.UserRepository;
import bjj.domain.TrainingSession;
import bjj.domain.User;
import bjj.request.TrainingSessionRequest;
import bjj.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TrainingSessionController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private UserRepository userRepository;

    public TrainingSessionController(JwtTokenProvider jwtTokenProvider,
                                     TrainingSessionRepository trainingSessionRepository,
                                     UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.trainingSessionRepository = trainingSessionRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/test")
    public String index() {
        return "App is running!";
    }

    @GetMapping("/trainingsessions")
    public List<TrainingSession> getTrainingSessions(@RequestHeader("Authorization") String auth) {
        User user = userRepository.getUser(jwtTokenProvider.getUsernameFromAuthHeader(auth));
        return trainingSessionRepository.getTrainingSessionsForUser(user);

    }

    @PostMapping("trainingsessions/new")
    public boolean postNewTrainingSession(@RequestBody TrainingSessionRequest trainingSession, @RequestHeader("Authorization") String auth) {
        User user = userRepository.getUser(jwtTokenProvider.getUsernameFromAuthHeader(auth));
        return trainingSessionRepository.insertTrainingSession(trainingSession, user);
    }

}