package projekt2.Individuals;

import java.util.HashSet;

public class Doctor extends Individual{

   private HashSet<Patient> patients;
   

   public Doctor(String n, String d, HashSet set){
        this.name=n;
        this.role="DOCTOR";
        this.division=d;
        this.patients=set;
   }

    
    public  void addPatient(Patient p){
        patients.add(p);
    }

    public void removePatient(Patient p){
        patients.remove(p);
    }

    public void setDivision(String d){
        this.division=d;
    }


}
