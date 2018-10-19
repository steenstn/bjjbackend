package bjj.controller;

import bjj.database.DatabaseConnection;
import bjj.domain.TrainingSession;
import bjj.input.TrainingSessionInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TestController {

    @RequestMapping("/")
    public String index() {
        return "App is running!";
    }

    @GetMapping("/trainingsessions")
    public List<TrainingSession> getTrainingSessions() {
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getAllTrainingSessions();
    }

    @PostMapping("trainingsessions/new")
    public boolean postNewTrainingSession(@RequestBody TrainingSessionInput trainingSession) {
        DatabaseConnection connection = new DatabaseConnection();
        return connection.insertTrainingSession(trainingSession);
    }

}