package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

import java.util.Set;

public class DoctorTypeTest {

    public static Object[][] testGetters_Source() {
        return new Object[][] {
                {0L, "", Set.of()},
                {0L, "", Set.of()},
                {1L, TestUtils.randomAlphabeticString(3), TestUtils.diseasesAsSet(10, 3)}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name, Set<Disease> diseasesSet) {
        DoctorType doctorType = TestUtils.checkNullsAndApply(DoctorType::create, id, name, diseasesSet);

        Assertions.assertThat(doctorType.getId()).isEqualTo(id);
        Assertions.assertThat(doctorType.getName()).isEqualTo(name);
        Assertions.assertThat(doctorType.getDiseases()).isEqualTo(diseasesSet);
    }

    public static Object[][] testSettersAndGetters_Source() {
        return new Object[][] {
                {0L, "", Set.of(), "", Set.of()},
                {0L, "a", Set.of(), "", Set.of()},
                {0L, "", Set.of(), "a", Set.of()},
                {0L, "", Set.of(), TestUtils.randomAlphabeticString(3), TestUtils.diseasesAsSet(10, 1)},
                {0L, TestUtils.randomAlphabeticString(3), TestUtils.diseasesAsSet(10, 3), "b", TestUtils.diseasesAsSet(10, 3)}
        };
    }

    @ParameterizedTest
    @MethodSource("testSettersAndGetters_Source")
    public void testSettersAnsGetters(Long id, String name, Set<Disease> diseasesSet, String name1, Set<Disease> diseasesSet1) {
        DoctorType doctorType = TestUtils.checkNullsAndApply(DoctorType::create, id, name, diseasesSet);

        Assertions.assertThat(doctorType.getId()).isEqualTo(id);
        Assertions.assertThat(doctorType.getName()).isEqualTo(name);
        Assertions.assertThat(doctorType.getDiseases()).isEqualTo(diseasesSet);

        TestUtils.checkNullsAndConsume(doctorType::setName, name1);
		TestUtils.checkNullsAndConsume(doctorType::setDiseases, diseasesSet1);

        Assertions.assertThat(doctorType.getId()).isEqualTo(id);
    }
}
