package bjj.controller;

import bjj.database.TrainingSessionRepository;
import bjj.database.UserRepository;
import bjj.domain.TrainingSession;
import bjj.domain.User;
import bjj.request.TrainingSessionRequest;
import bjj.security.JwtTokenProvider;
import bjj.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TrainingSessionController {

    private TrainingSessionRepository trainingSessionRepository;
    private UserService userService;

    public TrainingSessionController(TrainingSessionRepository trainingSessionRepository,
                                     UserService userService) {
        this.userService = userService;
        this.trainingSessionRepository = trainingSessionRepository;
    }

    @RequestMapping("/test")
    public String index() {
        return "App is running!";
    }

    @GetMapping("/trainingsessions")
    public List<TrainingSession> getTrainingSessions(@RequestHeader("Authorization") String auth) {
        User user = userService.getAuthenticatedUser(auth);
        return trainingSessionRepository.getTrainingSessionsForUser(user);

    }

    @PostMapping("trainingsessions/new")
    public TrainingSession postNewTrainingSession(@RequestBody TrainingSessionRequest trainingSession, @RequestHeader("Authorization") String auth) {
        User user = userService.getAuthenticatedUser(auth);
        return trainingSessionRepository.insertTrainingSession(trainingSession, user);
    }

    @PutMapping("trainingsessions/{id}")
    public boolean editTrainingSession (@RequestBody TrainingSessionRequest trainingSession, @PathVariable String id) {
        return trainingSessionRepository.editTrainingSession(trainingSession, UUID.fromString(id));
    }

    @PostMapping("trainingsessions/{id}/delete")
    public boolean deleteTrainingSession(@PathVariable String id, @RequestHeader("Authorization") String auth) {
        User user = userService.getAuthenticatedUser(auth);
        return trainingSessionRepository.deleteTrainingSession(UUID.fromString(id), user);
    }
}