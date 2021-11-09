package ru.hse.akinator.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.DoctorType;
import ru.hse.akinator.model.Drug;
import ru.hse.akinator.repository.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DrugServiceTest {

	public static Object[][] testGetAllDrugsForDisease_Source() {
		return new Object[][]{
				{List.of(), 1, List.of()},
				{List.of(List.of(0L)), 1, List.of()},
				{List.of(List.of(0L, 1L)), 1, List.of(0)},
				{List.of(List.of(0L), List.of(0L), List.of(0L)), 0, List.of(0, 1, 2)},
				{List.of(List.of(0L), List.of(0L), List.of(0L)), 2, List.of()}
		};
	}

	@ParameterizedTest
	@MethodSource("testGetAllDrugsForDisease_Source")
	public void testGetAllRelevantTypes(List<List<Long>> drugDiseaseDependencies, Integer diseaseNumber, List<Integer> relevantDrugIds) {
		List<Disease> allDiseases = TestUtils.diseases(10, 10);
		List<Drug> allDrugs = TestUtils.drugs(allDiseases, drugDiseaseDependencies);

		List<Drug> actualDrugs = DrugService.getAllDrugsForDisease(allDrugs, allDiseases.get(diseaseNumber));

		Set<Drug> expected = relevantDrugIds.stream()
				.map(allDrugs::get)
				.collect(Collectors.toSet());
		Set<Drug> actual = new HashSet<>(actualDrugs);
		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@ParameterizedTest
	@MethodSource("testGetAllDrugsForDisease_Source")
	public void testGetAllRelevantTypesFromRepo(List<List<Long>> drugDiseaseDependencies, Integer diseaseNumber, List<Integer> relevantDrugIds) {
		List<Disease> allDiseases = TestUtils.diseases(10, 10);
		List<Drug> allDrugs = TestUtils.drugs(allDiseases, drugDiseaseDependencies);
		Repository<Drug> drugRepo = Repository.create(Drug.class);
		for (Drug drug : allDrugs) {
			drugRepo.add(drug);
		}

		List<Drug> actualDrugs = DrugService.getAllDrugsForDiseaseFromRepo(drugRepo, allDiseases.get(diseaseNumber));

		Set<Drug> expected = relevantDrugIds.stream()
				.map(allDrugs::get)
				.collect(Collectors.toSet());
		Set<Drug> actual = new HashSet<>(actualDrugs);
		Assertions.assertThat(actual).isEqualTo(expected);
	}
}
