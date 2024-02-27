package projekt2.Individuals;

import projekt2.MedicalRecordHandler;
import projekt2.IndividualPermissions.Role;

public class Patient extends Individual {

    private String doctor;

    public Patient(String nurse, String division, String doctor) {
        this.name = nurse;
        this.role = Role.Patient;
        this.division = division;
        this.doctor = doctor;
    }

    public void createMedicalRecord(String doctor, String nurse, String data) {
        MedicalRecordHandler.createMedicalRecord(this.name, doctor, nurse, this.division, data);
    }

    public void writeMedicalRecord(String patient, int recordIndex, String data) {
        String fileName = this.name + "_" + recordIndex;
        MedicalRecordHandler.writeMedicalRecord(fileName, data);
    }

    public String readMedicalRecord(int recordIndex) {
        String fileName = this.name + "_" + recordIndex;
        return MedicalRecordHandler.readMedicalRecord(fileName);
    }

    public boolean deleteMedicalRecord(int recordIndex) {
        String fileName = this.name + "_" + recordIndex;
        return MedicalRecordHandler.deleteMedicalRecord(fileName);
    }

    public boolean recordExists(int recordIndex) {
        String fileName = this.name + "_" + recordIndex;
        if (!MedicalRecordHandler.exists(fileName)) {
            return false;
        }
        return true;
    }

    public boolean canRead(Individual user, int recordIndex) {
        String fileName = this.name + "_" + recordIndex;
        
        if (user.getRole().equals(Role.GovernmentAgency)) {
            return true;
        }
        String userName = user.getName();
        String division = user.getDivision();

        String doctor = MedicalRecordHandler.getDoctor(fileName);
        String nurse = MedicalRecordHandler.getNurse(fileName);
        String actualDivision = MedicalRecordHandler.getDivision(fileName);
        String patient = MedicalRecordHandler.getPatient(fileName);

        return userName.equals(doctor) || userName.equals(nurse) || userName.equals(patient)
                || division.equals(actualDivision);
    }

    public boolean canWrite(Individual user, int recordIndex) {
        String fileName = this.name + "_" + recordIndex;
        if (!MedicalRecordHandler.exists(fileName)) {
            return false;
        }
        if (user.getRole().equals(Role.GovernmentAgency)) {
            return false;
        }
        String userName = user.getName();

        String doctor = MedicalRecordHandler.getDoctor(fileName);
        String nurse = MedicalRecordHandler.getNurse(fileName);
        String patient = MedicalRecordHandler.getPatient(fileName);

        return userName.equals(doctor) || userName.equals(nurse) || userName.equals(patient);
    }

    public boolean canCreate(Individual user) {
        return user.getName().equals(this.doctor);
    }

}
