package server.database;

import commons.Multiplayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * MultiplayerRepository is an extension of JpaRepository and stores information relating
 * to the players within multiplayer games. This includes players in the waiting room,
 * as well as any active games (which are differentiated by the gameID variable).
 * This information is used to create the leaderboards in the middle and at the end of
 * each respective multiplayer game. It is also used to interact with players in the
 * waiting room.
 */
public interface MultiplayerRepository extends JpaRepository<Multiplayer, Long> {

    /**
     * getByGameID retrieves players that are within a certain game from Multiplayer Repository
     * @param id is the gameID that is being searched for in the database. The gameID
     *           differentiates which game the players are in (gameID = 0 if waiting room).
     * @return a list of players that are in a specified game
     */
    @Query(value = "SELECT * FROM MULTIPLAYER a WHERE a.gameID = ?1", nativeQuery = true)
    List<Multiplayer> getByGameID(@Param("id") long id);
//
//    @Modifying
//    @Query(value = "UPDATE MULTIPLAYER a SET a.gameID = ?1 WHERE a.gameID = 0", nativeQuery = true)
//    void setToGameID(@Param("id") long id);

}
