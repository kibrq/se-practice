package ru.hse.akinator.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import ru.hse.akinator.model.Symptom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class RepositoryTest {

	public static Object[][] test_Source() {
		return new Object[][]{
				{TestUtils.symptomsFromRandomNames(10)}
		};
	}


	@ParameterizedTest
	@MethodSource("test_Source")
	public void testGetAll(List<Symptom> symptoms) {
		Repository<Symptom> repository = Repository.create(Symptom.class);
		Assertions.assertThat(repository).isNotNull();
		Assertions.assertThat(repository.getAll()).isEqualTo(List.of());
		for (Symptom symptom : symptoms) {
			repository.add(symptom);
		}
		Assertions.assertThat(new HashSet<>(repository.getAll())).isEqualTo(new HashSet<>(symptoms));
	}

	@ParameterizedTest
	@MethodSource("test_Source")
	public void testGetById(List<Symptom> symptoms) {
		Repository<Symptom> repository = Repository.create(Symptom.class);
		Assertions.assertThat(repository).isNotNull();
		for (Symptom symptom : symptoms) {
			repository.add(symptom);
		}
		for (int i = 0; i < symptoms.size(); i++) {
			Assertions.assertThat(repository.getById((long) i)).isEqualTo(symptoms.get(i));
		}
	}
}
