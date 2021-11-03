package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

public class SymptomTest {

    public static Object[][] testGetters_Source() {
        return new Object[][]{
                {null, null}
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
}
