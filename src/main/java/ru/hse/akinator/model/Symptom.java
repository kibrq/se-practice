package ru.hse.akinator.model;

public interface Symptom extends Model {
    static Symptom create(Long id, String name) {
        return new SymptomImpl(id, name);
    }

    String getName();

    void setName(String name);
}
