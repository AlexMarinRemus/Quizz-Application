package commons;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(nullable = false)
    public String name;

    public int score;

    /**
     * Default constructor
     */
    public Player() {
        this.name = "";
        this.score = 0;
    }

    /**
     * Constructor with one parameter
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * Constructor with two parameters
     * @param name the name of the player
     * @param score the player's score
     */
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    //once this method is changed CHANGE THE TEST!!
    /**
     * Increase the score for the player by one
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
     * Compares two player and decides if they are equal
     * @param obj the other player to compare to
     * @return true or false based on the equality
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
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
