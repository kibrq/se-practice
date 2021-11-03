package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

public class DoctorTest {
    public static Object[][] testGetters_Source() {
        return new Object[][] {
                {null, null, null, -1},
                {null, "", TestUtils.doctorTypeWithRandomName(), 1},
                {0L, null, TestUtils.doctorTypeWithRandomName(), 1},
                {0L, "", null, 1},
                {0L, "", TestUtils.doctorTypeWithRandomName(), -1},
                {0L, "", TestUtils.doctorTypeWithRandomName(), 1},
        };
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testGetters(Long id, String name, DoctorType type, double busyness) {
        if (id == null || name == null || type == null || busyness <= 0) {
            Assertions.assertThatThrownBy(() -> Doctor.create(id, name, type, busyness)).isInstanceOf(IllegalArgumentException.class);
            return;
        }
        Doctor doctor = Doctor.create(id, name, type, busyness);

        Assertions.assertThat(doctor.getId()).isEqualTo(id);
        Assertions.assertThat(doctor.getName()).isEqualTo(name);
        Assertions.assertThat(doctor.getType()).isEqualTo(type);
        Assertions.assertThat(doctor.getBusyness()).isEqualTo(busyness);
    }

}
