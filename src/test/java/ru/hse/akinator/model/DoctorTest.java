package ru.hse.akinator.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;

public class DoctorTest {
    public static Object[][] testGetters_Source() {
        return new Object[][] {
                {null, null, null, -1},
                {null, "", TestUtils.doctorType(), 1},
                {0L, null, TestUtils.doctorType(), 1},
                {0L, "", null, 1},
                {0L, "", TestUtils.doctorType(), -1},
                {0L, "", TestUtils.doctorType(), 1},
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

    public static Object[][] testSettersAndGetters_Source() {
        return new Object[][] {
                {null, null, null, -1, null, null, -1},
                {null, "", TestUtils.doctorType(), 1, null, null, -1},
                {0L, null, TestUtils.doctorType(), 1, null, null, -1},
                {0L, "", null, 1, null, null, -1},
                {0L, "", TestUtils.doctorType(), -1, null, null, -1},
                {0L, "", TestUtils.doctorType(), 1, null, null, -1},
                {0L, "", TestUtils.doctorType(), 1, "a", null, -1},
                {0L, "", TestUtils.doctorType(), 1, "a", TestUtils.doctorType(), -1},
                {0L, "", TestUtils.doctorType(), 1, "a", TestUtils.doctorType(), 2},
        };
    }

    @ParameterizedTest
    @MethodSource("testSettersAndGetters_Source")
    public void testSettersAnsGetters(Long id, String name, DoctorType type, double busyness, String name1, DoctorType type1, double busyness1) {
        if (id == null || name == null || type == null || busyness <= 0) {
            Assertions.assertThatThrownBy(() -> Doctor.create(id, name, type, busyness)).isInstanceOf(IllegalArgumentException.class);
            return;
        }
        Doctor doctor = Doctor.create(id, name, type, busyness);

        Assertions.assertThat(doctor.getId()).isEqualTo(id);
        Assertions.assertThat(doctor.getName()).isEqualTo(name);
        Assertions.assertThat(doctor.getType()).isEqualTo(type);
        Assertions.assertThat(doctor.getBusyness()).isEqualTo(busyness);

        if (name1 == null) {
            Assertions.assertThatThrownBy(() -> doctor.setName(name1)).isInstanceOf(IllegalArgumentException.class);
            Assertions.assertThat(doctor.getName()).isEqualTo(name);
        } else {
            Assertions.assertThatCode(() -> doctor.setName(name1)).doesNotThrowAnyException();
            Assertions.assertThat(doctor.getName()).isEqualTo(name1);
        }
        if (type1 == null) {
            Assertions.assertThatThrownBy(() -> doctor.setType(type1)).isInstanceOf(IllegalArgumentException.class);
            Assertions.assertThat(doctor.getType()).isEqualTo(type);
        } else {
            Assertions.assertThatCode(() -> doctor.setType(type1)).doesNotThrowAnyException();
            Assertions.assertThat(doctor.getType()).isEqualTo(type1);
        }
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
