package commons;

import javax.persistence.*;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Activity {

    @Id
    public long id;

    //manually generated id to ensure that range of activities is between 1 and N
    public static long idgen = 1;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public int consumption_in_wh;

    public String source;

    public String title_id;

    public String image_path;

    /**
     * Default constructor
     */
    public Activity() {
        this.title = "";
        this.consumption_in_wh = 0;
        this.source = "";
        this.title_id = "";
        this.image_path = "";

        this.id = idgen;
        idgen++;
    }

    /**
     * Constructor with parameters
     * @param title the description of the activity
     * @param consumption_in_wh the energy the activity needs in wh
     * @param source the source of the information
     */
    public Activity(String title, int consumption_in_wh, String source) {
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
        this.title_id = "";
        this.image_path = "";

        this.id = idgen;
        idgen++;
    }


    /**
     * Constructor with parameters
     * @param title the description of the activity
     * @param consumption_in_wh the energy the activity needs in wh
     * @param source the source of the information
     * @param title_id a string value for the id
     * @param image_path the path to the corresponding image
     */
    public Activity(String title, int consumption_in_wh, String source, String title_id, String image_path) {
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
        this.title_id = title_id;
        this.image_path = image_path;

        this.id = idgen;
        idgen++;
    }

    /**
     * Get the title of the activity
     * @return the description of the activity
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the consumption of the activity
     * @return the energy the activity needs in wh
     */
    public int getConsumption_in_wh() {
        return consumption_in_wh;
    }

    /**
     * Get the id of the activity
     * @return the id of the activity which identifies it
     */
    public long getId(){
        return id;
    }


    /**
     * Get the String id of the activity
     * @return the string id of the activity
     */
    public String getTitle_id() {
        return title_id;
    }

    /**
     * Get the image path of the activity
     * @return the image path of the activity
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * Compares two activity and decides if they are equal
     * @param o the other activity to compare to
     * @return true or false based on the equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return consumption_in_wh == activity.consumption_in_wh && title.equals(activity.title);
    }

    /**
     * Generate hashcode for the activity
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Transform the activity into a String format
     * @return the String format of the activity
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}
