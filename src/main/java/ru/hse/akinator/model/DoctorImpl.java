package ru.hse.akinator.model;

public class DoctorImpl implements Doctor {
    private final Long id;
    private String name;
    private DoctorType doctorType;
    private double busyness;

    public DoctorImpl(Long id, String name, DoctorType doctorType, double busyness) {
        if (id == null) {
            throw new IllegalArgumentException("Doctor id cannot be null");
        } else if (name == null) {
            throw new IllegalArgumentException("Doctor name cannot be null");
        } else if (doctorType == null) {
            throw new IllegalArgumentException("Doctor type cannot be null");
        } else if (busyness <= 0) {
            throw new IllegalArgumentException("Doctor busyness must be positive");
        }

        this.id = id;
        this.name = name;
        this.doctorType = doctorType;
        this.busyness = busyness;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String newName) {
        name = newName;
    }

    @Override
    public DoctorType getType() {
        return doctorType;
    }

    @Override
    public void setType(DoctorType newType) {
        doctorType = newType;
    }

    @Override
    public double getBusyness() {
        return busyness;
    }

    @Override
    public void setBusyness(double newBusyness) {
        busyness = newBusyness;
    }

    @Override
    public Long getId() {
        return id;
    }
}
