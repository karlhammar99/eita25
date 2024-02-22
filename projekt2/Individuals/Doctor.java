package projekt2.Individuals;

import java.util.HashSet;
import java.util.List;

public class Doctor extends Individual{

   private List<String> patients;
   

   public Doctor(String n, String d, List<String> patients){
        this.name=n;
        this.role="DOCTOR";
        this.division=d;
        this.patients=patients;
   }

    
    /* public  void addPatient(Patient p){
        patients.add(p);
    }

    public void removePatient(Patient p){
        patients.remove(p);
    } */

    public void setDivision(String d){
        this.division=d;
    }


}
