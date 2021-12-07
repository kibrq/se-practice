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
				{new ByteArrayInputStream("DEFINITELY_YES\n".getBytes()), new PrintStream(new ByteArrayOutputStream()), TestUtils.symptoms(List.of("kek")).get(0), Answer.DEFINITELY_YES},
				{new ByteArrayInputStream("IDK\n".getBytes()), new PrintStream(new ByteArrayOutputStream()), TestUtils.symptoms(List.of("ama")).get(0), Answer.IDK}
		};
	}

	@ParameterizedTest
	@MethodSource("testAskAboutSymptom_Source")
	public void testCreateInteraction(InputStream is, PrintStream out, Symptom symptom, Answer expectedAns) {
		Assertions.assertThatThrownBy(() -> Interaction.create(null, out, s -> "Do you have " + s.getName())).isInstanceOf(IllegalArgumentException.class);
		Assertions.assertThatThrownBy(() -> Interaction.create(is, null, s -> "Do you have " + s.getName())).isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@MethodSource("testAskAboutSymptom_Source")
	public void testAskAboutSymptomNull(InputStream is, PrintStream out, Symptom symptom, Answer expectedAns) {
		Interaction interaction = Interaction.create(is, out, s -> "Do you have " + s.getName());
		Assertions.assertThatThrownBy(() -> interaction.askAboutSymptom(null)).isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@MethodSource("testAskAboutSymptom_Source")
	public void testAskAboutSymptom(InputStream is, PrintStream out, Symptom symptom, Answer expectedAns) {
		Interaction interaction = Interaction.create(is, out, s -> "Do you have " + s.getName());
		Assertions.assertThat(interaction.askAboutSymptom(symptom)).isEqualTo(expectedAns);
	}

	public static Object[][] testAskAboutSymptoms_Source() {
		return new Object[][]{
				{new ByteArrayInputStream("DEFINITELY_YES\nIDK\n".getBytes()), new PrintStream(new ByteArrayOutputStream()), TestUtils.symptoms(List.of("kek", "lol")), List.of(Answer.DEFINITELY_YES, Answer.IDK)},
				{new ByteArrayInputStream("IDK\nMORE_NO_THAN_YES\n".getBytes()), new PrintStream(new ByteArrayOutputStream()), TestUtils.symptoms(List.of("kek", "lol")), List.of(Answer.IDK, Answer.MORE_NO_THAN_YES)}
		};
	}

	@ParameterizedTest
	@MethodSource("testAskAboutSymptoms_Source")
	public void testAskAboutSymptomsNull(InputStream is, PrintStream out, List<Symptom> symptoms, List<Answer> expectedAns) {
		Interaction interaction = Interaction.create(is, out, s -> "Do you have " + s.getName());
		Assertions.assertThatThrownBy(() -> interaction.askAboutSymptoms(null)).isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@MethodSource("testAskAboutSymptoms_Source")
	public void testAskAboutSymptoms(InputStream is, PrintStream out, List<Symptom> symptoms, List<Answer> expectedAns) {
		Interaction interaction = Interaction.create(is, out, s -> "Do you have " + s.getName());
		var answersBySymptom = interaction.askAboutSymptoms(symptoms);
		List<Answer> actual = symptoms.stream().map(answersBySymptom::get).collect(Collectors.toList());
		Assertions.assertThat(actual).isEqualTo(expectedAns);
	}
}
