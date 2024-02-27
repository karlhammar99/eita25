package projekt2.Individuals;

import projekt2.IndividualPermissions.Role;

public class GovernmentAgency extends Individual {

    public GovernmentAgency(String name) {
        this.name = name;
        this.role = Role.GovernmentAgency;
    }

}
