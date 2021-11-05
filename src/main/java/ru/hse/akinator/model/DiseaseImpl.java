package ru.hse.akinator.model;

import java.util.List;

public class DiseaseImpl implements Disease {
    private final Long id;
    private String name;
    private List<Symptom> symptoms;

    public DiseaseImpl(Long id, String name, List<Symptom> symptoms) {
        if (id == null || name == null || symptoms == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = name;
        this.symptoms = symptoms;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    @Override
    public void setSymptoms(List<Symptom> symptoms) {
        if (symptoms == null) {
            throw new IllegalArgumentException();
        }
        this.symptoms = symptoms;
    }

    @Override
    public Long getId() {
        return id;
    }
}
