package ru.hse.akinator.model;

public interface Doctor extends Model {
    static Doctor create(Long id, String name, DoctorType doctorType, double busyness) {
        return new DoctorImpl(id, name, doctorType, busyness);
    }

    String getName();

    void setName(String name);

    DoctorType getType();

    void setType(DoctorType type);

    double getBusyness();

    void setBusyness(double busyness);
}
