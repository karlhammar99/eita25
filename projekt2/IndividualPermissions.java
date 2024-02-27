package projekt2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import projekt2.Individuals.Doctor;
import projekt2.Individuals.GovernmentAgency;
import projekt2.Individuals.Individual;
import projekt2.Individuals.Nurse;
import projekt2.Individuals.Patient;

public class IndividualPermissions {

    public enum Role {
        Patient, Nurse, Doctor, GovernmentAgency
    }

    private static final Set<String> VALID_ACTIONS = Set.of("read", "write", "create", "delete");

    List<Individual> individuals = new ArrayList<>();

    private Optional<Patient> findPatient(String patientName) {
        return individuals.stream()
                .filter(i -> i instanceof Patient)
                .map(i -> (Patient) i)
                .filter(p -> p.getName().equals(patientName))
                .findFirst();
    }

    public String answer(Individual user, String request) {
        String[] words = request.split(" ");
        String action = words[0];

        if (!isValidAction(action)) {
            return "Must be read/write/create/delete";
        }

        if (!hasValidArguments(user, words.length)) {
            return "More args needed";
        }
        String patientName = words[1];


        switch (user.getRole()) {
            case Doctor:
                return handleDoctorAction2(user, action, patientName, words, request);
            case Nurse:
                return handleNurseAction2(user, action, patientName, words, request);
            case GovernmentAgency:
                return handleGovernmentAgencyAction2(user, action, patientName, words, request);
            case Patient:
                return handlePatientAction2(user, action, words, request);
            default:
                return "Wrong user type";
        }
    }

    private boolean isValidAction(String action) {
        return VALID_ACTIONS.contains(action);
    }

    private boolean hasValidArguments(Individual user, int argCount) {
        return !(user instanceof Patient ? argCount < 2 : argCount < 3);
    }

    private String handleDoctorAction2(Individual user, String action, String patientName,
            String[] words, String request) {
        if (!action.equals("read") && !action.equals("write") && !action.equals("create")) {
            return "Illegal action";
        }

        Optional<Patient> patientOpt = findPatient(patientName);
        if (!patientOpt.isPresent()) {
            return "No patient match";
        }
        return performAction(user, patientOpt.get(), action, words, request);
    }

    private String handleNurseAction2(Individual user, String action, String patientName,
            String[] words, String request) {
        if (!action.equals("read") && !action.equals("write")) {
            return "Illegal action";
        }

        Optional<Patient> patientOpt = findPatient(patientName);
        if (!patientOpt.isPresent()) {
            return "No patient match";
        }
        return performAction(user, patientOpt.get(), action, words, request);
    }

    private String handleGovernmentAgencyAction2(Individual user, String action, String patientName,
            String[] words, String request) {
        if (!action.equals("read") && !action.equals("delete")) {
            return "Illegal action";
        }

        Optional<Patient> patientOpt = findPatient(patientName);
        if (!patientOpt.isPresent()) {
            return "No patient match";
        }
        return performAction(user, patientOpt.get(), action, words, request);
    }

    private String performAction(Individual user, Patient patient, String action, String[] words,
            String request) {
        switch (action) {
            case "read":
                try {
                    Integer.valueOf(words[2]);
                } catch (Exception e) {
                    return "Record index must be integer.";
                }
                if (!patient.recordExists(Integer.valueOf(words[2]))) {
                    Logger.log(user.getName() + " tried to read " + patient.getName() + "_"
                            + words[2] + ".medicalRecord but the medical record did not exist.");
                    return "Medical record does not exist.";
                }
                if (!patient.canRead(user,
                        Integer.valueOf(words[2]))) {
                            Logger.log(user.getName() + " tried to read " + patient.getName() + "_"
                            + words[2] + ".medicalRecord but was unathorized.");
                    return "Not allowed to access record.";
                }
                Logger.log(user.getName() + " read " + patient.getName() + "_" + words[2]
                        + ".medicalRecord");
                return patient.readMedicalRecord(Integer.valueOf(words[2]));

            case "write":
                try {
                    Integer.valueOf(words[2]);
                } catch (Exception e) {
                    return "Record index must be integer.";
                }
                String data = request.split(" ", 4)[3];

                if (!patient.recordExists(Integer.valueOf(words[2]))) {
                    Logger.log(user.getName() + " tried to write " + data + " into "
                            + patient.getName() + "_" + words[2]
                            + ".medicalRecord but the medical record did not exist.");
                    return "Record does not exist.";
                }
                if (!patient.canWrite(user,
                        Integer.valueOf(words[2]))) {
                            Logger.log(user.getName() + " tried to write \"" + data + "\" into "
                            + patient.getName() + "_" + words[2]
                            + ".medicalRecord but was unathorized.");

                    return "Not allowed to access record.";
                }

                patient.writeMedicalRecord(user.getName(),
                        Integer.valueOf(words[2]),
                        data);

                Logger.log(user.getName() + " wrote: \"" + data + "\" in "
                        + patient.getName() + "_" + words[2] + ".medicalRecord");
                return "Medical record written";
            case "create":
                if (!patient.canCreate(user)) {
                    Logger.log(user.getName() + "tried to create medical record for patient "
                            + patient.getName() + "but was unauthorized.");
                    return "This is not your patient.";
                }
                try {
                    patient.createMedicalRecord(user.getName(), words[2],
                            request.split(" ", 4)[3]);
                    Logger.log(user.getName() + "created medical record for patient "
                            + patient.getName() + ".");
                } catch (Exception e) {
                    return "Wrong number of args";
                }

                return "Medical record created";
            case "delete":
                try {
                    Integer.valueOf(words[2]);
                } catch (Exception e) {
                    return "Record index must be integer.";
                }
                if (patient.deleteMedicalRecord(Integer.valueOf(words[2]))) {
                    Logger.log(user.getName() + " deleted " + patient.getName() + "_" + words[2]
                            + ".medicalRecord.");
                    return "Medical record deleted.";
                }
                Logger.log(user.getName() + " tried to delete " + patient.getName() + "_" + words[2]
                            + ".medicalRecord, but it did not exist.");
                return "Medical record not found";


            default:
                return "invalid action";
        }
    }

    private String handlePatientAction2(Individual user, String action, String[] words,
            String request) {
        if (!action.equals("read")) {
            return "Illegal action";
        }

        Optional<Patient> patientOpt = findPatient(user.getName());
        if (!patientOpt.isPresent()) {
            return "No patient match";
        }
        String[] words_fixed = new String[3];
        words_fixed[2] = words[1];

        return performAction(user, patientOpt.get(), action, words_fixed, request);
    }


    public void loadIndividuals(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals("") || line.startsWith("#")) {
                continue;
            }

            String[] words = line.split(" ");
            String name = words[0];
            Role role = Role.valueOf(words[1]);
            String division = "";

            if (role != Role.GovernmentAgency) {
                division = words[2];
            }

            switch (role) {
                case Patient:
                    String doctor = words[3];
                    individuals.add(new Patient(name, division, doctor));
                    break;
                case Nurse:
                    individuals.add(new Nurse(name, division));

                    break;
                case Doctor:
                    individuals.add(new Doctor(name, division));
                    break;

                case GovernmentAgency:
                    individuals.add(new GovernmentAgency(name));
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

}
