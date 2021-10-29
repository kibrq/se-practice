package ru.hse.akinator.service;

import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Doctor;
import ru.hse.akinator.model.DoctorType;

import java.util.List;

public class DoctorService {
    public static List<DoctorType> getAllRelevantTypes(Disease disease) {
        throw new UnsupportedOperationException();
    }
    
    public static List<Doctor> getAllRelevantDoctors(DoctorType type) {
        throw new UnsupportedOperationException();
    }

    public static Doctor getLessBusynessDoctor(Disease disease) {
        throw new UnsupportedOperationException();
    }
}
