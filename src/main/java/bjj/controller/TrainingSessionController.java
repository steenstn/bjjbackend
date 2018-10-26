package bjj.controller;

import bjj.database.TrainingSessionRepository;
import bjj.domain.TrainingSession;
import bjj.input.TrainingSessionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TrainingSessionController {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    public TrainingSessionController(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    @RequestMapping("/")
    public String index() {
        return "App is running!";
    }

    @GetMapping("/trainingsessions")
    public List<TrainingSession> getTrainingSessions() {
        return trainingSessionRepository.getAllTrainingSessions();
    }

    @PostMapping("trainingsessions/new")
    public boolean postNewTrainingSession(@RequestBody TrainingSessionInput trainingSession) {
        return trainingSessionRepository.insertTrainingSession(trainingSession);
    }

}