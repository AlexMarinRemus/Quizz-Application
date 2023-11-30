/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Player;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.PlayerRepository;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerRepository repo;

    private Map<Object, Consumer<Player>> listenersPlayers = new HashMap<>();

    /**
     * Constructor for the Controller with parameters
     * @param repo the Player Repository that the controller accesses - exclusively used for single player entries
     */
    public PlayerController(PlayerRepository repo) {
        this.repo = repo;
    }

    /**
     * The get mapping for the root of the player repository's api (localhost:PORT/api/players/),
     * which finds all records in the repository
     * @return the list of the (single) players stored in the player repository
     */
    @GetMapping(path = { "", "/" })
    public List<Player> getAll() {
        return repo.findAll();
    }

    /**
     * The get mapping for the path: localhost:PORT/api/players/ID (where ID is the id),
     * finds the record corresponding to a desired id from the players repository
     * @param id the number identifying the desired player
     * @return the Player corresponding to the id parameter
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getById(id));
    }

    /**
     * The post mapping for the path: localhost:PORT/api/players/,
     * which updates the repository by adding the entry of a new Player record
     * @param player the record to be added
     * @return the Player record that has been added to the database - note: this return
     * is important because its id has been updated to become unique, and must be used in the future
     * to access the added record
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Player> add(@RequestBody Player player) {

        if (player.name == null || player.name.equals("")) {
            return ResponseEntity.badRequest().build();
        }
        listenersPlayers.forEach((k, l) -> l.accept(player));
        Player saved = repo.save(player);
        return ResponseEntity.ok(saved);
    }

    /**
     * The get mapping for the path: localhost:PORT/api/multiplayers/updates/players,
     * which returns the Player object that was previously added to the repo
     * @return the Player record was added before as a deffered result (asynchronous)
     */
    @GetMapping(path = {"/updates/players"})
    public DeferredResult<ResponseEntity<Player>> getUpdatesPlayer() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Player>>(5000L, noContent);
        var key = new Object();
        listenersPlayers.put(key, p -> {
            res.setResult(ResponseEntity.ok(p));
        });
        res.onCompletion(() -> {
            listenersPlayers.remove(key);
        });
        return res;
    }

    /**
     * The delete mapping for the path: localhost:PORT/api/players/ID (where ID is the id),
     * which deletes the record corresponding to a certain id
     * @param id the id of the player to be deleted
     * @return a boolean specifying whether the operation was successful
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public boolean deletePlayer(@PathVariable("id") long id)
    {
        if(repo.existsById(id)){
            Player p = repo.getById(id);
            repo.delete(p);
            return true;
        }
        return false;
    }

}
