package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import java.util.List;

public class DrugTest {

	public static Object[][] testGetters_Source() {
		return new Object[][]{
				{1L, "", List.of()},
				{0L, "a", List.of()},
				{0L, "Podorozhnik", TestUtils.diseases(3, 3)},
				{1L, TestUtils.randomAlphabeticString(3), TestUtils.diseases(5, 5)}
		};
	}

	public static Object[][] testSetters_Source() {
		return new Object[][]{
				{1L, "", List.of(), "", List.of()},
				{0L, "A", List.of(), "B", List.of()},
				{0L, "Podorozhnik", TestUtils.diseases(3, 3), "", TestUtils.diseases(3, 3)},
				{0L, "Podorozhnik", TestUtils.diseases(3, 3), "A", List.of()},
				{0L, "Podorozhnik", TestUtils.diseases(3, 3), "A", TestUtils.diseases(3, 3)},
				{1L, TestUtils.randomAlphabeticString(10), TestUtils.diseases(3, 3), TestUtils.randomAlphabeticString(10), TestUtils.diseases(3, 3)}
		};
	}


	@ParameterizedTest
	@MethodSource("testGetters_Source")
	public void testGetters(Long id, String name, List<Disease> diseases) {
		Drug drug = TestUtils.checkNullsAndApply(Drug::create, id, name, diseases);

		Assertions.assertThat(drug.getId()).isEqualTo(id);
		Assertions.assertThat(drug.getName()).isEqualTo(name);
		Assertions.assertThat(drug.getDiseases()).isEqualTo(diseases);

	}

	@ParameterizedTest
	@MethodSource("testSetters_Source")
	public void testSetters(Long id, String name, List<Disease> diseases, String newName, List<Disease> newDiseases) {
		Drug drug = TestUtils.checkNullsAndApply(Drug::create, id, name, diseases);

		Assertions.assertThat(drug.getId()).isEqualTo(id);
		Assertions.assertThat(drug.getName()).isEqualTo(name);
		Assertions.assertThat(drug.getDiseases()).isEqualTo(diseases);

		TestUtils.checkNullsAndConsume(drug::setName, newName);
		TestUtils.checkNullsAndConsume(drug::setDiseases, newDiseases);

		Assertions.assertThat(drug.getName()).isEqualTo(newName);
		Assertions.assertThat(drug.getDiseases()).isEqualTo(newDiseases);
		Assertions.assertThat(drug.getId()).isEqualTo(id);
	}
}
