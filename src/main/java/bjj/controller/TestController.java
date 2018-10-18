package bjj.controller;

import bjj.database.DatabaseConnection;
import bjj.domain.TrainingSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @RequestMapping("/")
    public List<TrainingSession> index() {
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getAllTrainingSessions();
    }

}