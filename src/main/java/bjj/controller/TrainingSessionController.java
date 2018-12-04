package bjj.controller;

import bjj.database.TrainingSessionRepository;
import bjj.database.UserRepository;
import bjj.domain.TrainingSession;
import bjj.domain.User;
import bjj.request.TrainingSessionRequest;
import bjj.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.UUID;

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
    public TrainingSession postNewTrainingSession(@RequestBody TrainingSessionRequest trainingSession, @RequestHeader("Authorization") String auth) {
        User user = userRepository.getUser(jwtTokenProvider.getUsernameFromAuthHeader(auth));
        return trainingSessionRepository.insertTrainingSession(trainingSession, user);
    }

    @PutMapping("trainingsessions/{id}")
    public boolean editTrainingSession (@RequestBody TrainingSessionRequest trainingSession, @PathVariable String id) {
        return trainingSessionRepository.editTrainingSession(trainingSession, UUID.fromString(id));
    }

    @DeleteMapping("trainingsessions/{id}")
    public boolean deleteTrainingSession(@PathVariable String id, @RequestHeader("Authorization") String auth) {
        User user = userRepository.getUser(jwtTokenProvider.getUsernameFromAuthHeader(auth));
        return trainingSessionRepository.deleteTrainingSession(UUID.fromString(id), user);
    }
}