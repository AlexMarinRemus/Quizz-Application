package server.api;

import commons.Multiplayer;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.MultiplayerRepository;

import java.util.List;

@RestController
@RequestMapping("/api/multiplayers")
public class MultiplayerController {

    private final MultiplayerRepository repo;
    private static int gameIDGen = 0;

    /**
     * The constructor for the Controller with parameters
     * @param repo the Multiplayer Repository that the controller accesses
     */
    public MultiplayerController(MultiplayerRepository repo) {
        this.repo = repo;
        repo.deleteAll();
    }

    /**
     * The get mapping for the root of the multiplayer repository's api (localhost:PORT/api/multiplayers/),
     * which finds all records in the repository
     * @return the list of the multiplayers stored in the multiplayer repository
     */
    @GetMapping(path = { "", "/" })
    public List<Multiplayer> getAll() {
        return repo.findAll();
    }

    /**
     * The get mapping for the path: localhost:PORT/api/multiplayers/ID (where ID is the id),
     * finds the record corresponding to a desired id from the multiplayer repository
     * @param id the number identifying the desired multiplayer
     * @return the Multiplayer corresponding to the id parameter
     */
    @GetMapping("/{id}")
    public ResponseEntity<Multiplayer> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getById(id));
    }

    /**
     * The get mapping for the path: localhost:PORT/api/multiplayers/GAMEID (where GAMEID is the gameid),
     * finds all of the players that are in a certain game
     * @param gameid the id of the game that the players are in
     * @return the list of multiplayers in the game
     */
    @GetMapping("/game/{gameid}")
    public ResponseEntity<List<Multiplayer>> getMultiplayersByGameId(@PathVariable("gameid") long gameid) {
        if (gameid < 0 || gameid > gameIDGen) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<List<Multiplayer>> r = ResponseEntity.ok(repo.getByGameID(gameid));
        return r;
    }

    /**
     * The post mapping for the path: localhost:PORT/api/multiplayers/update,
     * which updates the repository by changing the entry of an existing Multiplayer record
     * @param multiplayer the record to be updated
     * @return the Multiplayer record that has been updated to the database - note: this return
     * is important because its id has been updated to become unique, and must be used in the future
     * to access the added record
     */
    @PostMapping(path = { "/update" })
    public ResponseEntity<Multiplayer> update(@RequestBody Multiplayer multiplayer) {

        if (multiplayer.name == null || multiplayer.name.equals("")) {
            return ResponseEntity.badRequest().build();
        }
        Multiplayer saved = repo.save(multiplayer);
        return ResponseEntity.ok(saved);
    }

    /**
     * The get mapping for the path: localhost:PORT/api/multiplayers/game/start,
     * which takes all of the players in the waiting room (gameID = 0), and changes
     * their gameID to a new value, effectively starting a new game room for them.
     * @return the ID of the new game
     */
    @GetMapping("/game/start")
    public ResponseEntity<Integer> setMultiplayersToGameId() {
        gameIDGen++;
        List<Multiplayer> list = repo.getByGameID(0);
        for(Multiplayer m : list){
            m.setGameID(gameIDGen);
            repo.save(m);
        }
        return ResponseEntity.ok(gameIDGen);
    }

    /**
     * The post mapping for the path: localhost:PORT/api/multiplayers/,
     * which updates the repository by adding the entry of a new Multiplayer record
     * @param multiplayer the record to be added
     * @return the Multiplayer record that has been added to the database - note: this return
     * is important because its id has been updated to become unique, and must be used in the future
     * to access the added record
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Multiplayer> add(@RequestBody Multiplayer multiplayer) {

        if (multiplayer.name == null || multiplayer.name.equals("")) {
            return ResponseEntity.badRequest().build();
        }
        Multiplayer saved = repo.save(multiplayer);
        return ResponseEntity.ok(saved);
    }

    /**
     * The delete mapping for the path: localhost:PORT/api/multiplayers/deletall,
     * which deletes all of the multiplayers in the database
     */
    @DeleteMapping(path = {"/deleteall"})
    public void deleteAllMultiplayers()
    {
        repo.deleteAll();
    }

    /**
     * The delete mapping for the path: localhost:PORT/api/multiplayers/ID (where ID is the id),
     * which deletes the record corresponding to a certain id
     * @param id the id of the player to be deleted
     * @return a boolean specifying whether the operation was successful
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public boolean deleteMultiplayer(@PathVariable("id") long id){
        if(repo.existsById(id)){
            Multiplayer p = repo.getById(id);
            repo.delete(p);
            return true;
        }
        return false;
    }

    /**
     * Takes messages from the localhost:/app/multiplayers path, adds them to the repository,
     * and sends the resulting Multiplayer object back through the /topic/multiplayers path
     * @param m the Multiplayer object to be added
     * @return the resulting Multiplayer object that is returned by the add() method, seen above
     */
    @MessageMapping("/multiplayers") //app/multiplayers
    @SendTo("/topic/multiplayers")
    public Multiplayer addPlayer(Multiplayer m){
        return add(m).getBody();
    }

    /**
     * Takes messages from the localhost:/app/emojis path, adds them to the repository,
     * and sends the resulting Multiplayer object back through the /topic/emojis path
     * @param m the Multiplayer object which receives the new emotion
     * @return the resulting Multiplayer object that is returned by the send() method, seen above
     */
    @MessageMapping("/emojis")
    @SendTo("/topic/emojis")
    public Multiplayer send(Multiplayer m){
        return m;
    }

}
