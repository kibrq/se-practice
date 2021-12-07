package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

public class DoctorTest {
    public static Object[][] testGetters_Source() {
        return new Object[][] {
                {0L, "", TestUtils.doctorType(), 1},
                {0L, "aa", TestUtils.doctorType(), 1},
                {0L, "bbbb", TestUtils.doctorType(), -1},
                {1L, TestUtils.randomAlphabeticString(3), TestUtils.doctorType(), 1},
        };
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name, DoctorType type, double busyness) {
		if (busyness <= 0) {
			Assertions.assertThatThrownBy(() -> Doctor.create(id, name, type, busyness)).isInstanceOf(IllegalArgumentException.class);
			return;
		}
		Doctor doctor = TestUtils.checkNullsAndApply((i, n, t) -> Doctor.create(i, n, t, busyness), id, name, type);

        Assertions.assertThat(doctor.getId()).isEqualTo(id);
        Assertions.assertThat(doctor.getName()).isEqualTo(name);
        Assertions.assertThat(doctor.getType()).isEqualTo(type);
        Assertions.assertThat(doctor.getBusyness()).isEqualTo(busyness);
    }

    public static Object[][] testSettersAndGetters_Source() {
        return new Object[][] {
                {1L, "", TestUtils.doctorType(), -1, "", TestUtils.doctorType(), -1},
                {0L, "", TestUtils.doctorType(), 1, "", TestUtils.doctorType(), -1},
                {0L, "", TestUtils.doctorType(), 1, "a", TestUtils.doctorType(), -1},
                {0L, "", TestUtils.doctorType(), 1, "a", TestUtils.doctorType(), -1},
                {0L, "", TestUtils.doctorType(), 1, TestUtils.randomAlphabeticString(4), TestUtils.doctorType(), 2},
        };
    }

    @ParameterizedTest
    @MethodSource("testSettersAndGetters_Source")
    public void testSettersAnsGetters(Long id, String name, DoctorType type, double busyness, String name1, DoctorType type1, double busyness1) {
		if (busyness <= 0) {
			Assertions.assertThatThrownBy(() -> Doctor.create(id, name, type, busyness)).isInstanceOf(IllegalArgumentException.class);
			return;
		}
		Doctor doctor = TestUtils.checkNullsAndApply((i, n, t) -> Doctor.create(i, n, t, busyness), id, name, type);

        Assertions.assertThat(doctor.getId()).isEqualTo(id);
        Assertions.assertThat(doctor.getName()).isEqualTo(name);
        Assertions.assertThat(doctor.getType()).isEqualTo(type);
        Assertions.assertThat(doctor.getBusyness()).isEqualTo(busyness);

		TestUtils.checkNullsAndConsume(doctor::setName, name1);
		TestUtils.checkNullsAndConsume(doctor::setType, type1);

		Assertions.assertThat(doctor.getName()).isEqualTo(name1);
		Assertions.assertThat(doctor.getType()).isEqualTo(type1);

		if (busyness1 <= 0) {
			Assertions.assertThatThrownBy(() -> doctor.setBusyness(busyness1)).isInstanceOf(IllegalArgumentException.class);
			Assertions.assertThat(doctor.getBusyness()).isEqualTo(busyness);
		} else {
			Assertions.assertThatCode(() -> doctor.setBusyness(busyness1)).doesNotThrowAnyException();
			Assertions.assertThat(doctor.getBusyness()).isEqualTo(busyness1);
		}

        Assertions.assertThat(doctor.getId()).isEqualTo(id);
    }
}
