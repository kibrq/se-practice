package ru.hse.akinator.model;

public class SymptomImpl implements Symptom {
    private final Long id;
    private String name;

    public SymptomImpl(Long id, String name) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        if (name == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }
}
