package projekt2;

import projekt2.Individuals.Individual;
import projekt2.Individuals.Nurse;
import projekt2.Individuals.Patient;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import projekt2.Individuals.Doctor;

public class IndividualPermissions {
    List<Individual> individuals;



    public String answer(Individual individual, String request) { // som nurse kan man skriva: write
                                                                  // Kalle record1 Mår bra

        String[] words = request.split(" ");
        String action = words[0];
        String patientName = words[1];


        switch (individual.getRole()) {
            // -------------------------------------------------------------------------------------------
            case "DOCTOR":

                for (Individual other : individuals) {
                    if (other instanceof Patient) {
                        Patient patient = (Patient) other;
                        if (patient.getName().equals(patientName)) {

                            switch (action) {
                                case "create":// create patient1 nurse1 data

                                    patient.createMedicalRecord(individual.getName(), words[2],
                                            request.substring(3));
                                    return "Medical record created";
                                case "write":// write Kalle 3 han mår bra

                                    if (!patient.associatedWithRecord(individual.getName(),
                                            individual.getDivision(),
                                            Integer.valueOf(words[2]))) {
                                        return "Not allowed to access patients record";
                                    }
                                    patient.writeMedicalRecord(individual.getName(),
                                            Integer.valueOf(words[2]),
                                            request.substring(3));
                                    return "Medical record written";
                                case "read":// read Kalle 3

                                    if (!patient.associatedWithRecord(individual.getName(),
                                            individual.getDivision(),
                                            Integer.valueOf(words[2]))) {
                                        return "Not allowed to access patients record";
                                    }
                                    return patient.readMedicalRecord(Integer.valueOf(words[2]));
                                default:
                                    return "No such action";
                            }
                        }
                    }
                }
                return "No patient match";

            // -------------------------------------------------------------------------------------------
            case "NURSE":

                for (Individual other : individuals) {
                    if (other instanceof Patient) {
                        Patient patient = (Patient) other;
                        if (patient.getName().equals(patientName)) {

                            switch (action) {
                                case "create":// create patient1 nurse1 data
                                    return "Illegal action";
                                case "write":// write Kalle 3 han mår bra

                                    if (!patient.associatedWithRecord(individual.getName(),
                                            individual.getDivision(),
                                            Integer.valueOf(words[2]))) {
                                        return "Not allowed to access patients record";
                                    }
                                    patient.writeMedicalRecord(individual.getName(),
                                            Integer.valueOf(words[2]),
                                            request.substring(3));
                                    return "Medical record written";
                                case "read":// read Kalle 3

                                    if (!patient.associatedWithRecord(individual.getName(),
                                            individual.getDivision(),
                                            Integer.valueOf(words[2]))) {
                                        return "Not allowed to access patients record";
                                    }
                                    return patient.readMedicalRecord(Integer.valueOf(words[2]));
                                default:
                                    return "No such action";
                            }
                        }
                    }
                }
                return "Not allowed to access patients records";
            // -------------------------------------------------------------------------------------------
            case "GOVERNMENT":

                for (Individual other : individuals) {
                    if (other instanceof Patient) {
                        Patient patient = (Patient) other;
                        if (patient.getName().equals(patientName)) {

                            switch (action) {
                                case "create":// create patient1 nurse1 data
                                    return "Illegal action";
                                case "write":// write Kalle 3 han mår bra
                                    return "Illegal action";
                                case "read":// read Kalle 3
                                    return patient.readMedicalRecord(Integer.valueOf(words[2]));
                                case "delete": //delete Kalle 3
                                    patient.deleteMedicalRecord(Integer.valueOf(words[2]));
                                    break;
                                default:
                                    return "No such action";
                            }
                        }
                    }
                }

                break;
            // -------------------------------------------------------------------------------------------
            case "PATIENT":
                switch (action) {
                    case "read":// read 2
                        for (Individual other : individuals) {
                            if (other instanceof Patient) {
                                Patient patient = (Patient) other;

                                if (patient.getName().equals(individual.getName())) {
                                    return patient.readMedicalRecord(Integer.valueOf(words[1]));
                                }
                            }

                        }
                        break;

                    default:
                        System.out.println("NOT ALLOWED");
                        break;
                }

            default:
                break;
        }

        return "";
    }


    /* private boolean doctorPermissionCheck(Doctor dr, String request) {
        return false;
    } */

    /* private boolean nursePermissionCheck(Nurse n, String request, String fileName) {
        boolean ret = false;
        readName(fileName).forEach(str -> {
            if (n.getName().equals(str)) {
                ret = true;
            }
        });

        return false;
    } */



    public void loadIndividuals(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            if (line == "") {
                continue;
            }
            String[] words = line.split(" ");
            if (words.length < 3) {
                System.out.println("Wrong format");
                return;
            }
            System.out.println(line);


            String name = words[0];
            String role = words[1];
            String division = words[2];

            List<String> extra = new ArrayList<>();
            for (int i = 3; i < words.length; i++) {
                extra.add(words[i]);
            }

            switch (role) {
                case "Patient":
                    String doctor = extra.get(0);
                    individuals.add(new Patient(name, division, doctor));
                    break;
                case "Nurse":
                    individuals.add(new Nurse(name, division, extra));

                    break;
                case "Doctor":
                    individuals.add(new Doctor(name, division, extra));
                    break;

                default:
                    System.out.println("Patient/Nurse/Doctor");
                    break;
            }



        }
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    /* private String readName(String fileName) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));


        try {
            return br.readLine();
        } catch (Exception e) {
            return null;
        }

    } */


}
