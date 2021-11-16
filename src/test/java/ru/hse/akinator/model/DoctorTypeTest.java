package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import java.util.Set;

public class DoctorTypeTest {

    public static Object[][] testGetters_Source() {
        return new Object[][]{
                {null, null, null},
                {null, "", Set.of()},
                {0L, null, Set.of()},
                {0L, "", null},
                {0L, "", Set.of()},
                {1L, "Jack", TestUtils.diseasesAsSet(10, 3)}
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

    public static Object[][] testSettersAndGetters_Source() {
        return new Object[][]{
                {0L, "", Set.of(), null, null},
                {0L, "", Set.of(), "", Set.of()},
                {0L, "a", Set.of(), "", Set.of()},
                {0L, "", Set.of(), "a", Set.of()},
                {0L, "", Set.of(), "a", TestUtils.diseasesAsSet(10, 1)},
                {0L, "a", TestUtils.diseasesAsSet(10, 3), "b", TestUtils.diseasesAsSet(10, 3)}
        };
    }

    @ParameterizedTest
    @MethodSource("testSettersAndGetters_Source")
    public void testSettersAnsGetters(Long id, String name, Set<Disease> diseasesSet, String name1, Set<Disease> diseasesSet1) {
        if (id == null || name == null || diseasesSet == null) {
            Assertions.assertThatThrownBy(() -> DoctorType.create(id, name, diseasesSet)).isInstanceOf(IllegalArgumentException.class);
            return;
        }
        DoctorType doctorType = DoctorType.create(id, name, diseasesSet);

        Assertions.assertThat(doctorType.getId()).isEqualTo(id);
        Assertions.assertThat(doctorType.getName()).isEqualTo(name);
        Assertions.assertThat(doctorType.getDiseases()).isEqualTo(diseasesSet);

        if (name1 == null) {
            Assertions.assertThatThrownBy(() -> doctorType.setName(name1)).isInstanceOf(IllegalArgumentException.class);
            Assertions.assertThat(doctorType.getName()).isEqualTo(name);
        } else {
            Assertions.assertThatCode(() -> doctorType.setName(name1)).doesNotThrowAnyException();
            Assertions.assertThat(doctorType.getName()).isEqualTo(name1);
        }

        if (diseasesSet1 == null) {
            Assertions.assertThatThrownBy(() -> doctorType.setDiseases(diseasesSet1)).isInstanceOf(IllegalArgumentException.class);
            Assertions.assertThat(doctorType.getDiseases()).isEqualTo(diseasesSet);
        } else {
            Assertions.assertThatCode(() -> doctorType.setDiseases(diseasesSet1)).doesNotThrowAnyException();
            Assertions.assertThat(doctorType.getDiseases()).isEqualTo(diseasesSet1);
        }

        Assertions.assertThat(doctorType.getId()).isEqualTo(id);
    }
}
