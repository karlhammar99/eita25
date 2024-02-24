package projekt2;

import java.util.Properties;
import projekt2.Individuals.Patient;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class MedicalRecord {

    private String recordName;
    private Properties record;

    public MedicalRecord(Patient patient, String doctor, String nurse, String division, String data) {

        record = new Properties();
        record.setProperty("patient", patient.getName());
        record.setProperty("doctor", doctor);
        record.setProperty("nurse", nurse);
        record.setProperty("division", division);
        
        record.setProperty("data", data);

        this.recordName =
                patient.getName() + "_" + patient.getNumberOfMedicalRecords() + ".medicalRecord";

        saveToFile();
    }

    public void write(String data) {
        record.setProperty("data", data);
        saveToFile();
    }

    private void saveToFile() {
        try {
            record.store(new FileOutputStream(recordName + ".medicalRecord"), null);

        } catch (IOException e) {
            System.out.println("Error creating medical record file");
            e.printStackTrace();
        }
    }

    public String read() {
        return record.getProperty("data", "No data in medical record.");
    }

    public boolean delete() {
        File file = new File(recordName + ".medicalRecord");
        if (file.exists()) {
            if (file.delete()) {
                this.record = null;
                this.recordName = null;
                return true;
            }
            return false;
        }
        return false;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MedicalRecord) {
            MedicalRecord other = (MedicalRecord) obj;
            if (other.recordName.equals(this.recordName)) {
                return true;
            }
        }
        return false;
    }


    public String getDoctor() {
        return record.getProperty("doctor");
    }

    public String getNurse() {
        return record.getProperty("nurse");
    }

    public String getPatient() {
        return record.getProperty("patient");
    }
    public String getDivision() {
        return record.getProperty("division");
    }

}
