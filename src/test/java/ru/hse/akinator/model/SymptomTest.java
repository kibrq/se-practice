package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

public class SymptomTest {

    public static Object[][] testGetters_Source() {
        return new Object[][]{
                {null, null},
                {null, ""},
                {0L, null},
                {0L, "Smert"},
                {1L, TestUtils.randomAlphabeticString(5)}
        };
    }

    public static Object[][] testSetters_Source() {
        return new Object[][]{
                {null, null, null},
                {0L, "Che-to ne ochen'", null},
                {0L, "Che-to ne ochen'", "Voobshe ploho"},
                {0L, TestUtils.randomAlphabeticString(10), TestUtils.randomAlphabeticString(10)}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name) {
        if (id == null || name == null) {
            Assertions.assertThatThrownBy(() -> Symptom.create(id, name)).isInstanceOf(IllegalArgumentException.class);
        } else {
            Symptom symptom = Symptom.create(id, name);
            Assertions.assertThat(symptom.getId()).isEqualTo(id);
            Assertions.assertThat(symptom.getName()).isEqualTo(name);
        }
    }


    @ParameterizedTest
    @MethodSource("testSetters_Source")
    public void testSetters(Long id, String name, String newName) {
        if (id == null || name == null) {
            Assertions.assertThatThrownBy(() -> Symptom.create(id, name)).isInstanceOf(IllegalArgumentException.class);
        } else {
            Symptom symptom = Symptom.create(id, name);
            Assertions.assertThat(symptom.getId()).isEqualTo(id);
            Assertions.assertThat(symptom.getName()).isEqualTo(name);
            if (newName == null) {
                Assertions.assertThatThrownBy(() -> symptom.setName(newName)).isInstanceOf(IllegalArgumentException.class);
            } else {
                symptom.setName(newName);
                Assertions.assertThat(symptom.getName()).isEqualTo(newName);
            }
        }
    }
}
