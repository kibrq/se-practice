package ru.hse.akinator.service;

import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Doctor;
import ru.hse.akinator.model.DoctorType;
import ru.hse.akinator.repository.Repository;

import java.util.List;

public class DoctorService {
    public static List<DoctorType> getAllRelevantTypes(List<DoctorType> allTypes, Disease disease) {
        throw new UnsupportedOperationException();
    }

    public static List<Doctor> getAllRelevantDoctors(List<Doctor> allDoctors, List<DoctorType> types) {
        throw new UnsupportedOperationException();
    }

    public static Doctor getLessBusynessDoctor(Disease disease, Repository<Doctor> doctorRepository) {
        throw new UnsupportedOperationException();
    }
}
