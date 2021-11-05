package ru.hse.akinator.model;

import java.util.List;

public class DiseaseImpl implements Disease {
    private Long id;
    private String name;
    private List<Symptom> symptoms;

    public DiseaseImpl(Long id, String name, List<Symptom> symptoms) {
        if (id == null || name == null || symptoms == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public List<Symptom> getSymptoms() {
        return null;
    }

    @Override
    public void setSymptoms(List<Symptom> symptoms) {

    }

    @Override
    public Long getId() {
        return null;
    }
}
