package bjj.database;

import bjj.domain.Move;
import bjj.domain.User;
import bjj.request.MoveRequest;

public interface MoveRepository {
    Move insertMove(MoveRequest move, User user);
}
