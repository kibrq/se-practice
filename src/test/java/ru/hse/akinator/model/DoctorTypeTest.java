package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import java.util.Set;

public class DoctorTypeTest {

    public static Object[][] testGetters_Source() {
        return new Object[][] {
                {null, null, null},
                {null, "", Set.of()},
                {0L, null, Set.of()},
                {0L, "", null},
                {0L, "", Set.of()},
                {1L, "Jack", TestUtils.randomDiseasesAsSet(10, 3)}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name, Set<Disease> diseasesSet) {
        if (id == null || name == null || diseasesSet == null) {
            Assertions.assertThatThrownBy(() -> DoctorType.create(id, name, diseasesSet)).isInstanceOf(IllegalArgumentException.class);
            return;
        }
        DoctorType doctorType = DoctorType.create(id, name, diseasesSet);

        Assertions.assertThat(doctorType.getId()).isEqualTo(id);
        Assertions.assertThat(doctorType.getName()).isEqualTo(name);
        Assertions.assertThat(doctorType.getDiseases()).isEqualTo(diseasesSet);
    }
}
