package ru.hse.akinator.interaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;
import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.Symptom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class InteractionTest {

    public static Object[][] testAskAboutSymptom_Source() {
        return new Object[][]{
                {null, null, null, null}
        };
    }

    @ParameterizedTest
    @MethodSource("testAskAboutSymptom_Source")
    public void testAskAboutSymptom(InputStream is, PrintStream out, Symptom symptom, Answer expectedAns) {
        if (is == null || out == null) {
            Assertions.assertThatThrownBy(() -> Interaction.create(is, out, s -> "Do you have " + s.getName())).isInstanceOf(IllegalArgumentException.class);
        } else {
            Interaction interaction = Interaction.create(is, out, s -> "Do you have " + s.getName());
            if (symptom == null) {
                Assertions.assertThatThrownBy(() -> interaction.askAboutSymptom(symptom)).isInstanceOf(IllegalArgumentException.class);
            } else {
                Assertions.assertThat(interaction.askAboutSymptom(symptom)).isEqualTo(expectedAns);
            }
        }
    }
}
