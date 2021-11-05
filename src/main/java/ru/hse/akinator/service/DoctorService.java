package ru.hse.akinator.service;

import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Doctor;
import ru.hse.akinator.model.DoctorType;
import ru.hse.akinator.repository.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {
    public static List<DoctorType> getAllRelevantTypes(List<DoctorType> allTypes, Disease disease) {
        return allTypes.stream()
                .filter(doctorType -> doctorType.getDiseases().contains(disease))
                .collect(Collectors.toList());
    }

    public static List<Doctor> getAllRelevantDoctors(List<Doctor> allDoctors, List<DoctorType> types) {
        return allDoctors.stream().filter(doctor -> types.contains(doctor.getType())).collect(Collectors.toList());
    }

    public static Doctor getLessBusynessDoctor(Disease disease, Repository<Doctor> doctorRepository) {
        return doctorRepository.getAll().stream()
                .filter(doctor -> doctor.getType().getDiseases().contains(disease))
                .reduce((d1, d2) -> d1.getBusyness() < d2.getBusyness() ? d1 : d2)
                .orElse(null);
    }
}
