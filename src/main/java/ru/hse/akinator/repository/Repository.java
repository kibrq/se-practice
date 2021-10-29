package ru.hse.akinator.repository;

import ru.hse.akinator.model.Model;

import java.util.List;

public interface Repository<T> {
    static <T extends Model> Repository<T> create(Class<T> c) {
        throw new UnsupportedOperationException();
    }

    List<T> getAll();

    T getById(Long id);

    boolean add(T value);
}
