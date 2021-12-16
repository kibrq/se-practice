package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import java.util.List;

public class DrugTest {

    public static Object[][] testGetters_Source() {
        return new Object[][]{
                {null, null, null},
                {null, "", List.of()},
                {0L, null, List.of()},
                {0L, "", null},
                {0L, "Podorozhnik", TestUtils.diseases(3, 3)},
                {1L, "", TestUtils.diseases(5, 5)}
        };
    }

    public static Object[][] testSetters_Source() {
        return new Object[][]{
                {null, null, null, null, null},
                {null, "", List.of(), null, null},
                {0L, null, List.of(), null, null},
                {0L, "A", null, null, null},
                {0L, "Podorozhnik", TestUtils.diseases(3, 3), null, TestUtils.diseases(3, 3)},
                {0L, "Podorozhnik", TestUtils.diseases(3, 3), "A", null},
                {0L, "Podorozhnik", TestUtils.diseases(3, 3), "A", TestUtils.diseases(3, 3)},
                {1L, TestUtils.randomAlphabeticString(10), TestUtils.diseases(3, 3), TestUtils.randomAlphabeticString(10), TestUtils.diseases(3, 3)}
        };
    }


    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name, List<Disease> diseases) {
        if (id == null || name == null || diseases == null) {
            Assertions.assertThatThrownBy(() -> Drug.create(id, name, diseases)).isInstanceOf(IllegalArgumentException.class);
        } else {
            Drug drug = Drug.create(id, name, diseases);
            Assertions.assertThat(drug.getId()).isEqualTo(id);
            Assertions.assertThat(drug.getName()).isEqualTo(name);
            Assertions.assertThat(drug.getDiseases()).isEqualTo(diseases);
        }
    }

    @ParameterizedTest
    @MethodSource("testSetters_Source")
    public void testSetters(Long id, String name, List<Disease> diseases, String newName, List<Disease> newDiseases) {
        if (id == null || name == null || diseases == null) {
            Assertions.assertThatThrownBy(() -> Drug.create(id, name, diseases)).isInstanceOf(IllegalArgumentException.class);
        } else {
            Drug drug = Drug.create(id, name, diseases);
            Assertions.assertThat(drug.getId()).isEqualTo(id);
            Assertions.assertThat(drug.getName()).isEqualTo(name);
            Assertions.assertThat(drug.getDiseases()).isEqualTo(diseases);
            if (newName == null) {
                Assertions.assertThatThrownBy(() -> drug.setName(newName)).isInstanceOf(IllegalArgumentException.class);
            } else {
                drug.setName(newName);
                Assertions.assertThat(drug.getName()).isEqualTo(newName);
            }
            if (newDiseases == null) {
                Assertions.assertThatThrownBy(() -> drug.setDiseases(newDiseases)).isInstanceOf(IllegalArgumentException.class);
            } else {
                drug.setDiseases(newDiseases);
                Assertions.assertThat(drug.getDiseases()).isEqualTo(newDiseases);
            }
        }
    }
}
