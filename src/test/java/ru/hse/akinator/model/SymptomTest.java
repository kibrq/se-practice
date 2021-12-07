package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

public class SymptomTest {

    public static Object[][] testGetters_Source() {
        return new Object[][]{
                {0L, ""},
                {0L, "Smert"},
                {1L, TestUtils.randomAlphabeticString(5)}
        };
    }

    public static Object[][] testSetters_Source() {
        return new Object[][]{
                {0L, "", ""},
                {0L, "Che-to ne ochen'", "Voobshe ploho"},
				{0L, "", TestUtils.randomAlphabeticString(10)},
				{0L, TestUtils.randomAlphabeticString(10), ""},
                {0L, TestUtils.randomAlphabeticString(10), TestUtils.randomAlphabeticString(10)}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name) {
		Symptom symptom = TestUtils.checkNullsAndApply(Symptom::create, id, name);

		Assertions.assertThat(symptom.getId()).isEqualTo(id);
		Assertions.assertThat(symptom.getName()).isEqualTo(name);
    }


    @ParameterizedTest
    @MethodSource("testSetters_Source")
    public void testSetters(Long id, String name, String newName) {
		Symptom symptom = TestUtils.checkNullsAndApply(Symptom::create, id, name);

		Assertions.assertThat(symptom.getId()).isEqualTo(id);
		Assertions.assertThat(symptom.getName()).isEqualTo(name);

		TestUtils.checkNullsAndConsume(symptom::setName, newName);

		Assertions.assertThat(symptom.getName()).isEqualTo(newName);
		Assertions.assertThat(symptom.getId()).isEqualTo(id);
    }
}
