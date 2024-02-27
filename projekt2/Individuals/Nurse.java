package projekt2.Individuals;

import java.util.List;
import projekt2.IndividualPermissions.Role;

public class Nurse extends Individual {

    public Nurse(String name, String division) {
        this.name = name;
        this.division = division;
        this.role = Role.Nurse;
    }



}
