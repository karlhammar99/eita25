package projekt2.Individuals;

public class Patient extends Individual{

        private String dr;
    
    public Patient(String n, String division, String dr){
        this.name=n;
        this.role="PATIENT";
        this.division=division;
        this.dr=dr;
    }
    public String getDoctor(){
        return dr;
    }

}
