package server.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import commons.Activity;
import server.database.ActivitiesRepository;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    private final ActivitiesRepository repo;

    /**
     * Constructor for the Controller with parameters
     * @param repo is the activities repository that the controller accesses
     */
    public ActivitiesController(ActivitiesRepository repo) {
        this.repo = repo;

        //Deletes the entries from the activities repository before json is imported to prevent
        //duplication of activities entries
        if(this.repo.count() != 0) {
            this.repo.deleteAll();
        }
        Activity.idgen = 1;
    }

    /**
     * The get mapping for the root of the activities repository's api (localhost:PORT/api/activities/),
     * which finds all records in the repository
     * @return the list of the activities stored in the activities repository
     */
    @GetMapping(path = { "", "/" })
    public List<Activity> getAll() {
        return repo.findAll();
    }

    /**
     * The get mapping for the path: localhost:PORT/api/activities/count,
     * which counts the number of records in the repository
     * @return the number of records in the repository
     */
    @GetMapping(path = { "/count" })
    public long getCount() {
        return repo.count();
    }

    /**
     * The get mapping for the path: localhost:PORT/api/activities/ID (where ID is the id),
     * finds the record corresponding to a desired id from the activities repository
     * @param id the number identifying the desired activity
     * @return the Activity corresponding to the id parameter
     */
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getById(id));
    }

    /**
     * The get mapping for the path: localhost:PORT/api/activities/ID (where ID is the id),
     * returns the record(s) of the activities with a certain consumption
     * @param c the consumption of the desired activities
     * @return a list of the activities corresponding to the c parameter
     */
    @GetMapping("/{consumption}")
    public ResponseEntity<List<Activity>> getByConsumption(@PathVariable("consumption") int c) {
        if (c < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Activity> ret = repo.getByConsumption_in_wh(c);
        return ResponseEntity.ok(ret);
    }

    /**
     * The get mapping for the path: localhost:PORT/api/activities/images/GROUPFOLDER/FILE
     *  where GROUPFOLDER is the group folder and FILE is the image
     * @param groupFolder the group folder where the picture is located
     * @param file the image itself
     * @return a specific image as byte array
     */
    @GetMapping("images/{groupfolder}/{file}")
    public ResponseEntity<byte[]> getImageByPath(@PathVariable("groupfolder") String groupFolder,
                                                 @PathVariable("file") String file){
        String pathPrefix = "src/main/resources/images/";
       // String encodedString = null;

        try{
            String path = pathPrefix + groupFolder + "/" + file;
            return ResponseEntity.ok(toByteArray(new File(path)));

        }
        catch(IOException e){
            System.out.println("File not found!");
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method to write the image file as byte array
     * @param file the file to be written as byte array
     * @return the byte array representation of the file
     * @throws IOException
     */
    public byte[] toByteArray(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] arr = new byte[(int) file.length()];
        fis.read(arr);
        fis.close();
        return arr;
    }

}

