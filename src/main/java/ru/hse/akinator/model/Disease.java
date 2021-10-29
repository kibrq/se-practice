package ru.hse.akinator.model;

import java.util.List;

public interface Disease extends Model {
    static Disease create(Long id, String name, List<Symptom> symptoms) {
        throw new UnsupportedOperationException();
    }

    String getName();

    void setName(String name);

    List<Symptom> getSymptoms();

    void setSymptoms(List<Symptom> symptoms);
}
