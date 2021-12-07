package ru.hse.akinator.model;

import java.util.List;

public class DrugImpl implements Drug {

    private final Long id;
    private String name;
    private List<Disease> diseases;

    public DrugImpl(Long id, String name, List<Disease> diseases) {
        if (id == null || name == null || diseases == null) {
            throw new IllegalArgumentException();
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
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        if (diseases == null) {
            throw new IllegalArgumentException();
        }
        this.diseases = diseases;
    }

    @Override
    public Long getId() {
        return id;
    }

}
