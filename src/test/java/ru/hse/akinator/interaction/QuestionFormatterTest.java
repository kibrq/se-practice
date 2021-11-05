package ru.hse.akinator.interaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;
import ru.hse.akinator.model.Symptom;

public class QuestionFormatterTest {
    public static Object[][] testGetters_Source() {
        return new Object[][]{
                {null}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetters_Source")
    public void testFormatter(Symptom symptom) {
        if (symptom == null || symptom.getName() == null) {
            Assertions.assertThatThrownBy(() -> QuestionFormatter.questionAboutSymptom(symptom)).isInstanceOf(IllegalArgumentException.class);
        } else {
            Assertions.assertThat(QuestionFormatter.questionAboutSymptom(symptom)).isEqualTo("Do you have " + symptom.getName());
        }
    }
}
