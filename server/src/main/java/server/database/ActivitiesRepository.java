package server.database;

import commons.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * ActivitiesRepository is an extension of JpaRepository and stores all information
 * relating to energy-consumption activities which is used to generate questions and
 * answers in the game. The data in this repository is imported from the
 * activities.json file.
 */
public interface ActivitiesRepository extends JpaRepository<Activity, Long> {

    /**
     * getByConsumption_in_wh returns a list of activities that have a certain consumption
     * @param c is the consumption quantity that is being searched for
     * @return a list of activities that have a specified consumption
     */
    @Query(value = "SELECT * FROM ACTIVITY a WHERE a.consumption_in_wh = ?1", nativeQuery = true)
    List<Activity> getByConsumption_in_wh(@Param("c") int c);

//    List<Activity> findActivitiesByConsumption_in_wh(int consumption_in_wh);

}

