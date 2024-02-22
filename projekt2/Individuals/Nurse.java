package projekt2.Individuals;

import java.util.List;

public class Nurse extends Individual{
    
    private List<String> patients;

    public Nurse(String name, String division, List<String> patients){
        this.name=name;
        this.division=division;
        this.role="NURSE";
        this.patients = patients;
    }

    

}
