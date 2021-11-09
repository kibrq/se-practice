package ru.hse.akinator.model;

import java.util.List;

public interface Drug extends Model {
	static Drug create(Long id, String name, List<Disease> diseases) {
		return new DrugImpl(id, name, diseases);
	}

	String getName();

	void setName(String name);

	List<Disease> getDiseases();

	void setDiseases(List<Disease> symptoms);
}
