package bjj.database;

import bjj.domain.Move;
import bjj.domain.User;
import bjj.request.MoveRequest;

import java.util.List;

public interface MoveRepository {
    Move insertMove(MoveRequest move, User user);
    List<Move> getMovesForUser(User user);
}
