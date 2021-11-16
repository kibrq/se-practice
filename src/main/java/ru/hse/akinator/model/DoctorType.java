package ru.hse.akinator.model;

import java.util.Set;

public interface DoctorType extends Model {
    static DoctorType create(Long id, String name, Set<Disease> diseases) {
        return new DoctorTypeImpl(id, name, diseases);
    }

    String getName();

    void setName(String name);

    Set<Disease> getDiseases();

    void setDiseases(Set<Disease> diseases);
}
