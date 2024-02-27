package projekt2.Individuals;

import java.util.HashSet;
import java.util.List;
import projekt2.IndividualPermissions.Role;

public class Doctor extends Individual {

    private List<String> patients;


    public Doctor(String name, String division) {
        this.name = name;
        this.role = Role.Doctor;
        this.division = division;
    }


}
