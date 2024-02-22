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



    public String answer(Individual individual, String request) {

        String[] words = request.split(" ");
        String action = words[0];
        String patientName = words[1];

        if (words.length > 2) { // Om doctor och man vill göra något med medical records
            for (int i = 2; i < words.length; i++) {

            }
        }


        switch (individual.getRole()) {
            // -------------------------------------------------------------------------------------------
            case "DOCTOR":
                switch (action) {
                    case "create":
                        // doktorn skriver:
                        // create patient1 data
                        // createRecord()
                        for (Individual other : individuals) {
                            if (other instanceof Patient) {
                                Patient patient = (Patient) other;
                                if (patient.getName().equals(patientName)
                                        && patient.getDoctor().equals(individual.getName())) {
                                    // Får createa
                                    // createRecord();
                                    return "Record created";
                                }
                            }
                        }
                        break;
                    case "write":

                        for (Individual i : individuals) {
                            if (individual.equals(i)
                                    || individual.getDivision().equals(i.getDivision())) {
                                // BORDE funka??????????
                                // write()
                                return "Record written";
                            }
                        }

                        break;
                    case "read":

                        for (Individual i : individuals) {
                            if (individual.getDivision().equals(i.getDivision())) {
                                // BORDE funka??????????
                                // return read();
                            }
                        }

                        break;
                    default:
                        System.out.println("NOT ALLOWED");
                        break;
                }



                break;
            // -------------------------------------------------------------------------------------------
            case "NURSE":

                switch (action) {
                    case "create":
                        System.out.println("NOT ALLOWED");
                        break;
                    case "write":

                        for (Individual i : individuals) {
                            if (individual.equals(i)) {
                                // BORDE funka??????????
                                // writeToRecord()
                            }
                        }

                        break;
                    case "read":

                        for (Individual i : individuals) {
                            if (individual.equals(i)
                                    || individual.getDivision().equals(i.getDivision())) {
                                // BORDE funka??????????
                                // return read();
                            }
                        }


                        break;
                    case "delete":
                        System.out.println("NOT ALLOWED");
                        break;

                    default:
                        System.out.println("NOT ALLOWED");
                        break;
                }
                // -------------------------------------------------------------------------------------------
            case "GOVERNMENT":

                switch (action) {
                    case "read":
                        System.out.println("ALLOW");
                        // return read();
                        break;
                    case "delete":
                        // delete()
                        System.out.println("ALLOW");
                        break;

                    default:
                        System.out.println("NOT ALLOWED");
                        break;
                }

                break;
            // -------------------------------------------------------------------------------------------
            case "PATIENT":
                switch (action) {
                    case "read":
                        if (patientName.equals(individual.getName())) {
                            // får läsa
                            // readRecord()
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


    private boolean doctorPermissionCheck(Doctor dr, String request) {
        return false;
    }

    private boolean nursePermissionCheck(Nurse n, String request, String fileName) {
        boolean ret = false;
        readName(fileName).forEach(str -> {
            if (n.getName().equals(str)) {
                ret = true;
            }
        });

        return false;
    }


    private boolean patientPermissionCheck(Patient p, String request, String fileName) {
        try {

            if (p.getName().equals(readName(fileName))) {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }



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

    private String readName(String fileName) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));


        try {
            return br.readLine();
        } catch (Exception e) {
            return null;
        }

    }


}
