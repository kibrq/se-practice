package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import java.util.List;

public class DiseaseTest {

    public static Object[][] testGetters_Source() {
        return new Object[][] {
                {null, null, null},
                {null, "", List.of()},
                {0L, null, List.of()},
                {0L, "Debil", null},
                {0L, "You are Maxim", TestUtils.symptomsFromNames(List.of("0iq", "Tupovat"))},
                {1L, "", TestUtils.symptomsFromRandomNames(5)}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name, List<Symptom> symptoms) {
        if (id == null || name == null || symptoms == null) {
            Assertions.assertThatThrownBy(() -> Disease.create(id, name, symptoms)).isInstanceOf(IllegalArgumentException.class);
        } else {
            Disease disease = Disease.create(id, name, symptoms);
            Assertions.assertThat(disease.getId()).isEqualTo(id);
            Assertions.assertThat(disease.getName()).isEqualTo(name);
            Assertions.assertThat(disease.getSymptoms()).isEqualTo(symptoms);
        }
    }
}
