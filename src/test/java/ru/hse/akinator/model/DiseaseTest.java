package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import java.util.List;

public class DiseaseTest {

    public static Object[][] testGetters_Source() {
        return new Object[][]{
            {0L, "", List.of()},
            {-1L, " ", List.of()},
            {0L, "Debil", List.of()},
            {0L, "You are Maxim", TestUtils.symptoms(List.of("0iq", "Tupovat"))},
            {1L, "", TestUtils.symptoms(5)}
        };
    }

    public static Object[][] testSetters_Source() {
        return new Object[][]{
            {0L, "", List.of(), "", List.of()},
            {0L, "You are Maxim", TestUtils.symptoms(List.of("0iq", "Tupovat")), "", TestUtils.symptoms(List.of("-3iq", "Sovsem ploh"))},
            {0L, "You are Maxim", TestUtils.symptoms(List.of("0iq", "Tupovat")), "You are Maxim Suhodolski", List.of()},
            {0L, "You are Maxim", TestUtils.symptoms(List.of("0iq", "Tupovat")), "You are Maxim Suhodolski", TestUtils.symptoms(List.of("-3iq", "Sovsem ploh"))},
            {1L, TestUtils.randomAlphabeticString(10), TestUtils.symptoms(5), TestUtils.randomAlphabeticString(10), TestUtils.symptoms(5)}
        };
    }


    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name, List<Symptom> symptoms) {
        Disease disease = TestUtils.checkNullsAndApply(Disease::create, id, name, symptoms);

        Assertions.assertThat(disease.getId()).isEqualTo(id);
        Assertions.assertThat(disease.getName()).isEqualTo(name);
        Assertions.assertThat(disease.getSymptoms()).isEqualTo(symptoms);
    }

    @ParameterizedTest
    @MethodSource("testSetters_Source")
    public void testSetters(Long id, String name, List<Symptom> symptoms, String newName, List<Symptom> newSymptoms) {
        Disease disease = TestUtils.checkNullsAndApply(Disease::create, id, name, symptoms);

        Assertions.assertThat(disease.getId()).isEqualTo(id);
        Assertions.assertThat(disease.getName()).isEqualTo(name);
        Assertions.assertThat(disease.getSymptoms()).isEqualTo(symptoms);

        TestUtils.checkNullsAndConsume(disease::setName, newName);
        TestUtils.checkNullsAndConsume(disease::setSymptoms, newSymptoms);

        Assertions.assertThat(disease.getName()).isEqualTo(newName);
        Assertions.assertThat(disease.getSymptoms()).isEqualTo(newSymptoms);
        Assertions.assertThat(disease.getId()).isEqualTo(id);
    }
}
