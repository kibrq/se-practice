package ru.hse.akinator.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;
import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Symptom;

import java.util.*;


public class DiseaseDeterminantServiceTest {

    // Test measureProbability

    public static Object[][] testMeasureProbability_firstDiseaseIsGreaterThanSecond_Source() {
        return new Object[][]{
                {
                        List.of(0L, 1L),
                        List.of(1L, 2L),
                        List.of(Answer.DEFINITELY_YES, Answer.MORE_YES_THAN_NO, Answer.IDK),
                },
                {
                        List.of(0L),
                        List.of(1L),
                        List.of(Answer.MORE_YES_THAN_NO, Answer.MORE_NO_THAN_YES),
                },
                {
                        List.of(2L, 1L, 0L),
                        List.of(2L, 3L),
                        List.of(Answer.DEFINITELY_YES, Answer.MORE_NO_THAN_YES, Answer.IDK, Answer.IDK),
                }
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
    @MethodSource("testMeasureProbability_firstDiseaseIsGreaterThanSecond_Source")
    public void testMeasureProbability_firstDiseaseIsGreaterThanSecond(List<Long> firstDiseaseSymptoms,
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

    public static Object[][] testMeasureProbability_firstAnswerIsGreaterThanSecond_Source() {
        return new Object[][]{
                {
                        List.of(0L, 1L, 2L),
                        List.of(Answer.DEFINITELY_YES, Answer.DEFINITELY_YES, Answer.DEFINITELY_YES),
                        List.of(Answer.DEFINITELY_NO, Answer.DEFINITELY_NO, Answer.DEFINITELY_NO)
                },
                {
                        List.of(0L),
                        List.of(Answer.MORE_YES_THAN_NO),
                        List.of(Answer.IDK)
                },
                {
                        List.of(0L, 1L),
                        List.of(Answer.MORE_YES_THAN_NO, Answer.IDK),
                        List.of(Answer.MORE_NO_THAN_YES, Answer.MORE_NO_THAN_YES)
                }
        };
    }

    @ParameterizedTest
    @MethodSource("testMeasureProbability_firstAnswerIsGreaterThanSecond_Source")
    public void testMeasureProbability_firstAnswerIsGreaterThanSecond(List<Long> diseaseSymptoms,
                                                                      List<Answer> firstAnswers,
                                                                      List<Answer> secondAnswers) {
        Assertions.assertThat(firstAnswers.size()).isEqualTo(secondAnswers.size());

        List<Symptom> symptoms = TestUtils.symptomsFromRandomNames(firstAnswers.size());
        Disease disease = TestUtils.diseasesFromSymptomsWithRandomNames(symptoms, List.of(diseaseSymptoms)).get(0);
        Map<Symptom, Answer> firstAnswerMap = fromAnswerListToAnswerMap(symptoms, firstAnswers);
        Map<Symptom, Answer> secondAnswerMap = fromAnswerListToAnswerMap(symptoms, secondAnswers);

        double probability1 = DiseaseDeterminantService.measureProbability(disease, firstAnswerMap);
        double probability2 = DiseaseDeterminantService.measureProbability(disease, secondAnswerMap);

        Assertions.assertThat(probability1).isFinite();
        Assertions.assertThat(probability2).isFinite();

        Assertions.assertThat(probability1).isGreaterThan(probability2);
    }

    public static Object[][] testMeasureProbability_firstDiseaseIsEqualSecond_Source() {
        return new Object[][]{
                {
                        List.of(0L, 1L),
                        List.of(1L, 2L),
                        List.of(Answer.DEFINITELY_YES, Answer.DEFINITELY_YES, Answer.DEFINITELY_YES),
                },
                {
                        List.of(0L),
                        List.of(1L),
                        List.of(Answer.MORE_YES_THAN_NO, Answer.MORE_YES_THAN_NO),
                },
                {
                        List.of(0L, 1L),
                        List.of(1L, 2L),
                        List.of(Answer.IDK, Answer.DEFINITELY_NO, Answer.IDK),
                }
        };
    }

    @ParameterizedTest
    @MethodSource("testMeasureProbability_firstDiseaseIsEqualSecond_Source")
    public void testMeasureProbability_firstDiseaseIsEqualSecond(List<Long> firstDiseaseSymptoms,
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

        Assertions.assertThat(probability1).isEqualTo(probability2);
    }

    public static Object[][] testMeasureProbability_firstAnswerIsEqualToSecond_Source() {
        return new Object[][]{
                {
                        List.of(0L, 1L, 2L),
                        List.of(Answer.DEFINITELY_YES, Answer.IDK, Answer.DEFINITELY_YES),
                        List.of(Answer.DEFINITELY_YES, Answer.DEFINITELY_YES, Answer.IDK)
                },
                {
                        List.of(0L),
                        List.of(Answer.MORE_YES_THAN_NO),
                        List.of(Answer.MORE_YES_THAN_NO)
                },
                {
                        List.of(0L, 1L),
                        List.of(Answer.IDK, Answer.IDK),
                        List.of(Answer.IDK, Answer.IDK)
                }
        };
    }

    @ParameterizedTest
    @MethodSource("testMeasureProbability_firstAnswerIsEqualToSecond_Source")
    public void testMeasureProbability_firstAnswerIsEqualToSecond(List<Long> diseaseSymptoms,
                                                                  List<Answer> firstAnswers,
                                                                  List<Answer> secondAnswers) {
        Assertions.assertThat(firstAnswers.size()).isEqualTo(secondAnswers.size());

        List<Symptom> symptoms = TestUtils.symptomsFromRandomNames(firstAnswers.size());
        Disease disease = TestUtils.diseasesFromSymptomsWithRandomNames(symptoms, List.of(diseaseSymptoms)).get(0);
        Map<Symptom, Answer> firstAnswerMap = fromAnswerListToAnswerMap(symptoms, firstAnswers);
        Map<Symptom, Answer> secondAnswerMap = fromAnswerListToAnswerMap(symptoms, secondAnswers);

        double probability1 = DiseaseDeterminantService.measureProbability(disease, firstAnswerMap);
        double probability2 = DiseaseDeterminantService.measureProbability(disease, secondAnswerMap);

        Assertions.assertThat(probability1).isFinite();
        Assertions.assertThat(probability2).isFinite();

        Assertions.assertThat(probability1).isEqualTo(probability2);
    }

    // ============================================================================================================
    // Test sortByRelevance

    public static Object[][] testSortByRelevance_Source() {
        return new Object[][]{
                {
                        List.of(
                                List.of(0L, 1L, 2L),
                                List.of(1L, 2L),
                                List.of(2L)
                        ),
                        List.of(Answer.DEFINITELY_YES, Answer.MORE_YES_THAN_NO, Answer.IDK),
                        List.of(0, 1, 2)
                },
                {
                        List.of(
                                List.of(2L),
                                List.of(1L),
                                List.of(0L)
                        ),
                        List.of(Answer.DEFINITELY_YES, Answer.MORE_YES_THAN_NO, Answer.MORE_NO_THAN_YES),
                        List.of(2, 1, 0)
                },
                {
                        List.of(
                                List.of(2L),
                                List.of(1L, 2L),
                                List.of(0L, 2L)
                        ),
                        List.of(Answer.MORE_NO_THAN_YES, Answer.MORE_YES_THAN_NO, Answer.IDK),
                        List.of(1, 0, 2)
                }
        };
    }

    @ParameterizedTest
    @MethodSource("testSortByRelevance_Source")
    public void testSortByRelevance(List<List<Long>> diseaseSymptoms,
                                    List<Answer> answersBySymptomId,
                                    List<Integer> correctOrder) {
        int size = answersBySymptomId.size();
        Assertions.assertThat(correctOrder.size()).isEqualTo(size);

        List<Symptom> symptoms = TestUtils.symptomsFromRandomNames(size);
        List<Disease> diseases = TestUtils.diseasesFromSymptomsWithRandomNames(
                symptoms, diseaseSymptoms
        );
        List<Disease> correctDiseases = new ArrayList<>();
        correctOrder.forEach(i -> correctDiseases.add(diseases.get(i)));

        Map<Symptom, Answer> answerMap = fromAnswerListToAnswerMap(symptoms, answersBySymptomId);

        DiseaseDeterminantService.sortByRelevance(diseases, answerMap);

        Assertions.assertThat(diseases).isEqualTo(correctDiseases);
    }

    public static Object[][] testSortByRelevance_consistentWithMeasureProbability_Source() {
        return new Object[][]{
                {
                        List.of(
                                List.of(0L, 1L, 2L),
                                List.of(1L, 2L),
                                List.of(2L)
                        ),
                        List.of(Answer.DEFINITELY_YES, Answer.MORE_YES_THAN_NO, Answer.IDK),
                },
                {
                        List.of(
                                List.of(2L),
                                List.of(1L),
                                List.of(0L)
                        ),
                        List.of(Answer.DEFINITELY_YES, Answer.MORE_YES_THAN_NO, Answer.MORE_NO_THAN_YES),
                },
                {
                        List.of(
                                List.of(2L),
                                List.of(1L, 2L),
                                List.of(0L, 2L)
                        ),
                        List.of(Answer.MORE_NO_THAN_YES, Answer.MORE_YES_THAN_NO, Answer.IDK),
                }
        };
    }

    @ParameterizedTest
    @MethodSource("testSortByRelevance_consistentWithMeasureProbability_Source")
    public void testSortByRelevance_consistentWithMeasureProbability(List<List<Long>> diseaseSymptoms,
                                                                     List<Answer> answersBySymptomId) {
        List<Symptom> symptoms = TestUtils.symptomsFromRandomNames(answersBySymptomId.size());
        List<Disease> diseases = TestUtils.diseasesFromSymptomsWithRandomNames(
                symptoms, diseaseSymptoms
        );

        Map<Symptom, Answer> answerMap = fromAnswerListToAnswerMap(symptoms, answersBySymptomId);

        List<Disease> correctDiseases = new ArrayList<>(diseases);
        correctDiseases.sort((d1, d2) -> {
            double prob1 = DiseaseDeterminantService.measureProbability(d1, answerMap);
            double prob2 = DiseaseDeterminantService.measureProbability(d2, answerMap);
            return -Double.compare(prob1, prob2);
        });

        DiseaseDeterminantService.sortByRelevance(diseases, answerMap);

        Assertions.assertThat(diseases).isEqualTo(correctDiseases);
    }


}
