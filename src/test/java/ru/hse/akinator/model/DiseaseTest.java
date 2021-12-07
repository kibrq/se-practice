package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import java.util.List;

public class DiseaseTest {

    public static Object[][] testCreate_Source() {
        return new Object[][]{
            {0L, "You are Maxim", TestUtils.symptoms(List.of("0iq", "Tupovat"))},
            {1L, "", TestUtils.symptoms(5)}
        };
    }

    public static Object[][] testGetters_Source() {
        return new Object[][]{
            {0L, "You are Maxim", TestUtils.symptoms(List.of("0iq", "Tupovat"))},
            {1L, "", TestUtils.symptoms(5)}
        };
    }

    public static Object[][] testSetters_Source() {
        return new Object[][]{
            {0L, "You are Maxim", TestUtils.symptoms(List.of("0iq", "Tupovat")), "You are Maxim Suhodolski", TestUtils.symptoms(List.of("-3iq", "Sovsem ploh"))},
            {1L, TestUtils.randomAlphabeticString(10), TestUtils.symptoms(5), TestUtils.randomAlphabeticString(10), TestUtils.symptoms(5)}
        };
    }

    @ParameterizedTest
    @MethodSource("testCreate_Source")
    public void testCreate(Long id, String name, List<Symptom> symptoms) {
        Assertions.assertThatThrownBy(() -> Disease.create(null, null, null)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> Disease.create(null, name, symptoms)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> Disease.create(id, null, symptoms)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> Disease.create(id, name, null)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> Disease.create(null, null, symptoms)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> Disease.create(id, null, null)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> Disease.create(null, name, null)).isInstanceOf(IllegalArgumentException.class);
        Disease disease = Disease.create(id, name, symptoms);
        Assertions.assertThat(disease).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name, List<Symptom> symptoms) {
        Disease disease = Disease.create(id, name, symptoms);
        Assertions.assertThat(disease.getId()).isEqualTo(id);
        Assertions.assertThat(disease.getName()).isEqualTo(name);
        Assertions.assertThat(disease.getSymptoms()).isEqualTo(symptoms);
    }

    @ParameterizedTest
    @MethodSource("testSetters_Source")
    public void testSettersNulls(Long id, String name, List<Symptom> symptoms, String newName, List<Symptom> newSymptoms) {
        Disease disease = Disease.create(id, name, symptoms);
        Assertions.assertThatThrownBy(() -> disease.setName(null)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> disease.setSymptoms(null)).isInstanceOf(IllegalArgumentException.class);
        disease.setName(newName);
        Assertions.assertThat(disease.getName()).isEqualTo(newName);
        disease.setSymptoms(newSymptoms);
        Assertions.assertThat(disease.getSymptoms()).isEqualTo(newSymptoms);
        Assertions.assertThat(disease.getId()).isEqualTo(id);
    }
}
