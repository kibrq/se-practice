package ru.hse.akinator.model;

import java.util.Set;

public class DoctorTypeImpl implements DoctorType {
    private final Long id;
    private String name;
    private Set<Disease> diseases;

    public DoctorTypeImpl(Long id, String name, Set<Disease> diseases) {
        if (id == null) {
            throw new IllegalArgumentException("Doctor id cannot be null");
        } else if (name == null) {
            throw new IllegalArgumentException("Doctor name cannot be null");
        } else if (diseases == null) {
            throw new IllegalArgumentException("Doctor diseases cannot be null");
        }

        this.id = id;
        this.name = name;
        this.diseases = diseases;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String newName) {
        if (newName == null) {
            throw new IllegalArgumentException("Doctor name cannot be null");
        }
        name = newName;
    }

    @Override
    public Set<Disease> getDiseases() {
        return diseases;
    }

    @Override
    public void setDiseases(Set<Disease> newDiseases) {
        if (newDiseases == null) {
            throw new IllegalArgumentException("Doctor diseases cannot be null");
        }
        diseases = newDiseases;
    }

    @Override
    public Long getId() {
        return id;
    }
}
