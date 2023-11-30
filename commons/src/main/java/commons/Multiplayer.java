package commons;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Multiplayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(nullable = false)
    public String name;

    public int score;

    public String emotion;

    public int gameID;

    /**
     * Default constructor
     */
    public Multiplayer() {
        this.name = "";
        this.score = 0;
        emotion = "";
        gameID = 0;
    }

    /**
     * Constructor with one parameter
     * @param name the name of the player
     */
    public Multiplayer(String name) {
        this.name = name;
        this.score = 0;
        emotion = "";
        gameID = 0;
    }

    /**
     * Constructor with two parameters
     * @param name the name of the player
     * @param score the player's score
     */
    public Multiplayer(String name, int score) {
        this.name = name;
        this.score = score;
        emotion = "";
        gameID = 0;
    }

    //once this method is changed CHANGE THE TEST!!
    /**
     * Increase the score for the player
     */
    public void increment() {
        score++;
    }

    /**
     * Get the id of the player
     * @return the id of the player which identifies it
     */
    public long getId(){
        return id;
    }

    /**
     * Get the name of the player
     * @return the name of the player
     */
    public String getName(){
        return name;
    }

    /**
     * Get the score of the player
     * @return The player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the emotion the player reacted with
     * @return the name of the emotion
     */
    public String getEmotion() {
        return emotion;
    }

    /**
     * Get the id of the game the player participates in
     * @return the id of the game
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Set the id of the game the player participates in
     * @param gameID the new id for the game
     */
    public void setGameID(int gameID) { this.gameID = gameID; }

//    public void setEmotion(String emotion) {
//        this.emotion = emotion;
//    }

    /**
     * Compares two player and decides if they are equal
     * @param obj the other player to compare to
     * @return true or false based on the equality
     */
    @Override
    public boolean equals(Object obj) {
        Multiplayer mp = (Multiplayer) obj;
        return this.name.equals(mp.getName()) && this.id == mp.getId() && this.gameID == mp.getGameID();
    }

    /**
     * Generate hashcode for the player
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Transform the player into a String format
     * @return the String format of the player
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
