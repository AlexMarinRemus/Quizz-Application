package server.database;

import commons.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PlayerRepository is an extension of JpaRepository and stores information relating
 * to each single player that has finished a game using the Player object.
 * This information is used to create the leaderboards on the splash screen and at
 * the end of a single player game.
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
