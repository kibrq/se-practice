package ru.hse.akinator.model;

import javax.print.Doc;

public interface Doctor extends Model {
    static Doctor create(Long id, String name, DoctorType doctorType, double busyness) {
        throw new UnsupportedOperationException();
    }

    String getName();
    void setName(String name);

    DoctorType getType();
    void setType(DoctorType type);

    double getBusyness();
    void setBusyness(double busyness);

}
