package monash.kuyumcians.unear.Models;

import java.util.ArrayList;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class University {

    private String universityName;
    private ArrayList<Campus> campuses;

    //Constructor
    public University(String universityName) {
        campuses = new ArrayList<Campus>();
        this.universityName = universityName;
    }

    public ArrayList<Campus> getBuildings() {
        return campuses;
    }

    public void addCampus(Campus campus) {
        campuses.add(campus);
    }

    public String getUniversityName() {
        return universityName;
    }
}
