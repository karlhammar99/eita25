package projekt2.Individuals;

import java.util.ArrayList;
import projekt2.MedicalRecord;

public class Patient extends Individual {

    private String doctor;
    private ArrayList<MedicalRecord> records;

    public Patient(String nurse, String division, String doctor) {
        this.name = nurse;
        this.role = "PATIENT";
        this.division = division;
        this.doctor = doctor;
        this.records = new ArrayList<>();
    }

    public String getDoctor() {
        return doctor;
    }

    public boolean createMedicalRecord(String doctor, String nurse, String data){
        MedicalRecord record = new MedicalRecord(this, doctor, nurse, division, data);
        return records.add(record);
    }

    public void writeMedicalRecord(String patient, int recordIndex, String data) {
        MedicalRecord record = records.get(recordIndex);
        record.write(data);
    }

    public String readMedicalRecord(int recordIndex) {
        return records.get(recordIndex).read();
    }

    public boolean deleteMedicalRecord(int recordIndex) {
        MedicalRecord record = records.remove(recordIndex);
        return record.delete();
    }

    public int getNumberOfMedicalRecords(){
        return records.size();
    }

    public boolean readWriteLegal(Individual individual) {
        if(individual instanceof Patient){
            if(this == individual){
                return true;
            }
        }
        return false;
    }

    public boolean associatedWithRecord(String individualName, String division, int recordIndex) {
        MedicalRecord record = records.get(recordIndex);
        String doctor = record.getDoctor();
        String nurse = record.getNurse();
        String patient = record.getPatient();

        return individualName.equals(doctor) || individualName.equals(nurse) || individualName.equals(patient) || division.equals(record.getDivision());
    }

}
