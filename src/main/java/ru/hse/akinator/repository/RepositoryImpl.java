package ru.hse.akinator.repository;

import ru.hse.akinator.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryImpl<T extends Model> implements Repository<T> {
	private final Map<Long, T> data;

	public RepositoryImpl(Class<T> ignored) {
		data = new HashMap<>();
	}

	@Override
	public List<T> getAll() {
		return new ArrayList<>(data.values());
	}

	@Override
	public T getById(Long id) {
		if (!data.containsKey(id)) {
			return null;
		}
		return data.get(id);
	}

	@Override
	public boolean add(T value) {
		if (data.containsKey(value.getId())) {
			return false;
		}
		data.put(value.getId(), value);
		return true;
	}
}
