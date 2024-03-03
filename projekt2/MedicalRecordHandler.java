package projekt2;

import java.util.Properties;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

public class MedicalRecordHandler {
    private static  String folderPath = "MedicalRecords/";

    public static void writeMedicalRecord(String fileName, String data) {
        Properties record = loadFromFile(fileName + ".medicalRecord");
        record.setProperty("data", data);
        saveToFile(fileName, record);
    }

    public static void createMedicalRecord(String patient, String doctor, String nurse,
            String division,
            String data) {
        Properties record = new Properties();
        record.setProperty("patient", patient);
        record.setProperty("doctor", doctor);
        record.setProperty("nurse", nurse);
        record.setProperty("division", division);
        record.setProperty("data", data);
        int index = countMedicalRecords(patient, folderPath);
        String fileName = patient + "_" + index;

        saveToFile(fileName, record);
    }

    public static String readMedicalRecord(String fileName) {

        Properties record = loadFromFile(fileName + ".medicalRecord");
        return record.getProperty("data", "Medical record does not exist.");
    }

    public static boolean deleteMedicalRecord(String fileName) {
        System.out.println("MedicalRecords/" + fileName + ".medicalRecord");
        File file = new File("MedicalRecords/" + fileName + ".medicalRecord");

        if (file.exists()) {
            if (file.delete()) {
                return true;
            }
            return false;
        }
        return false;
    }


    public static String getDoctor(String fileName) {
        Properties record = loadFromFile(fileName + ".medicalRecord");
        return record.getProperty("doctor");
    }

    public static String getNurse(String fileName) {
        Properties record = loadFromFile(fileName + ".medicalRecord");
        return record.getProperty("nurse");
    }

    public static String getPatient(String fileName) {
        Properties record = loadFromFile(fileName + ".medicalRecord");
        return record.getProperty("patient");
    }

    public static String getDivision(String fileName) {
        Properties record = loadFromFile(fileName + ".medicalRecord");
        return record.getProperty("division");
    }



    private static int countMedicalRecords(String patient, String folderPath) {
        File folder = new File(folderPath);

        int counter = 0;
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            for (File file : files) {
                if (!file.getName().endsWith(".medicalRecord")) {
                    continue;
                }
                if (file.getName().split("_")[0].equals(patient)) {
                    counter++;
                }
            }
        }
        return counter;
    }


    private static Properties loadFromFile(String fileName) {
        Properties prop = new Properties();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(folderPath + fileName);
            prop.load(fileInputStream);
            return prop;
        } catch (IOException e) {
            return prop;
        }
    }

    private static void saveToFile(String fileName, Properties record) {
        try {
            createFolderIfNotExists();
            record.store(new FileOutputStream(folderPath + fileName + ".medicalRecord"), null);

        } catch (IOException e) {
            System.out.println("Error creating medical record file");
            e.printStackTrace();
        }
    }

    public static boolean exists(String fileName) {
        File file = new File(folderPath + fileName + ".medicalRecord");
        if (!file.exists()) {
            return false;
        }
        System.out.println(file.getName());
        if (!file.getName().endsWith(".medicalRecord")) {
            return false;
        }
        return true;
    }

    public static void createFolderIfNotExists() {
        File folder = new File(folderPath.replace("/", ""));
        if (folder.exists() && folder.isDirectory()) {
            return;
        } 
        folder.mkdirs();
    }

}
