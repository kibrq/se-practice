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
import java.util.List;
import java.util.stream.Collectors;

public class InteractionTest {

    public static Object[][] testAskAboutSymptom_Source() {
        return new Object[][]{
                {null, null, null, null},
                {new ByteArrayInputStream("DEFINITELY_YES\n".getBytes()), new PrintStream(new ByteArrayOutputStream()), null, Answer.DEFINITELY_YES},
                {new ByteArrayInputStream("DEFINITELY_YES\n".getBytes()), new PrintStream(new ByteArrayOutputStream()), TestUtils.symptomsFromNames(List.of("kek")).get(0), Answer.DEFINITELY_YES}
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

    public static Object[][] testAskAboutSymptoms_Source() {
        return new Object[][]{
                {null, null, null, null},
                {new ByteArrayInputStream("DEFINITELY_YES\nIDK\n".getBytes()), new PrintStream(new ByteArrayOutputStream()), null, List.of(Answer.DEFINITELY_YES, Answer.IDK)},
                {new ByteArrayInputStream("DEFINITELY_YES\nIDK\n".getBytes()), new PrintStream(new ByteArrayOutputStream()), TestUtils.symptomsFromNames(List.of("kek", "lol")), List.of(Answer.DEFINITELY_YES, Answer.IDK)}
        };
    }

    @ParameterizedTest
    @MethodSource("testAskAboutSymptoms_Source")
    public void testAskAboutSymptoms(InputStream is, PrintStream out, List<Symptom> symptoms, List<Answer> expectedAns) {
        if (is == null || out == null) {
            Assertions.assertThatThrownBy(() -> Interaction.create(is, out, s -> "Do you have " + s.getName())).isInstanceOf(IllegalArgumentException.class);
        } else {
            Interaction interaction = Interaction.create(is, out, s -> "Do you have " + s.getName());
            if (symptoms == null) {
                Assertions.assertThatThrownBy(() -> interaction.askAboutSymptoms(symptoms)).isInstanceOf(IllegalArgumentException.class);
            } else {
                var answersBySymptom = interaction.askAboutSymptoms(symptoms);
                List<Answer> actual = symptoms.stream().map(answersBySymptom::get).collect(Collectors.toList());
                Assertions.assertThat(actual).isEqualTo(expectedAns);
            }
        }
    }
}
