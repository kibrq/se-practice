package ru.hse.akinator.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;
import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Symptom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DiseaseDeterminantServiceTest {
    public static Object[][] testMeasureProbability_SameAnswersDifferentDiseases_Source() {
        return new Object[][]{
                {
                        "First disease has definitely Yes",
                        List.of(0L, 1L),
                        List.of(1L, 2L),
                        List.of(Answer.DEFINITELY_YES, Answer.MORE_YES_THAN_NO, Answer.IDK),
                },
        };
    }

    private static Map<Symptom, Answer> fromAnswerListToAnswerMap(List<Symptom> symptoms, List<Answer> answers) {
        Map<Symptom, Answer> answerMap = new HashMap<>();
        for (int i = 0; i < answers.size(); i++) {
            answerMap.put(symptoms.get(i), answers.get(i));
        }
        return answerMap;
    }

    @ParameterizedTest
    @MethodSource("testMeasureProbability_SameAnswersDifferentDiseases_Source")
    public void testMeasureProbability_SameAnswersDifferentDiseases(String description,
                                                                    List<Long> firstDiseaseSymptoms,
                                                                    List<Long> secondDiseaseSymptoms,
                                                                    List<Answer> answersBySymptomId) {
        List<Symptom> symptoms = TestUtils.symptomsFromRandomNames(answersBySymptomId.size());
        List<Disease> diseases = TestUtils.diseasesFromSymptomsWithRandomNames(
                symptoms, List.of(firstDiseaseSymptoms, secondDiseaseSymptoms)
        );

        Map<Symptom, Answer> answerMap = fromAnswerListToAnswerMap(symptoms, answersBySymptomId);

        double probability1 = DiseaseDeterminantService.measureProbability(diseases.get(0), answerMap);
        double probability2 = DiseaseDeterminantService.measureProbability(diseases.get(1), answerMap);

        Assertions.assertThat(probability1).isFinite();
        Assertions.assertThat(probability2).isFinite();

        Assertions.assertThat(probability1).isGreaterThan(probability2);
    }
}
