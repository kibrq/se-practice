package ru.hse.akinator.model;

public interface Symptom extends Model {
    static Symptom create(Long id, String name) {
        throw new UnsupportedOperationException();
    }

    String getName();

    void setName(String name);
}
