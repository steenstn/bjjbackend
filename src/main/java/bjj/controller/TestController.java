package bjj.controller;

import bjj.database.DatabaseConnection;
import bjj.domain.TrainingSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

}