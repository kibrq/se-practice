package ru.hse.akinator.service;

import ru.hse.akinator.interaction.Interaction;
import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Symptom;
import ru.hse.akinator.repository.Repository;

import java.util.List;
import java.util.Map;

public class DiseaseDeterminantService {

    public static double measureProbability(Disease disease, Map<Symptom, Answer> answerBySymptoms) {
        int counter = 0;
        for (Map.Entry<Symptom, Answer> entry : answerBySymptoms.entrySet()) {
            boolean isSymptomInDisease = disease.getSymptoms().contains(entry.getKey());
            switch (entry.getValue()) {
                case DEFINITELY_YES -> {
                    counter += isSymptomInDisease ? 2 : -2;
                }
                case MORE_YES_THAN_NO -> {
                    counter += isSymptomInDisease ? 1 : -1;
                }
                case MORE_NO_THAN_YES -> {
                    counter += isSymptomInDisease ? -1 : 1;
                }
                case DEFINITELY_NO -> {
                    counter += isSymptomInDisease ? -2 : 2;
                }
            }
        }
        return (1 + (double) (counter) / (answerBySymptoms.size() * 2)) / 2;
    }

    public static void sortByRelevance(List<Disease> diseases, Map<Symptom, Answer> answerBySymptoms) {
        throw new UnsupportedOperationException();
    }

    public static List<Disease> diseasesSortedByRelevance(Interaction interaction, Repository<Symptom> symptomRepository, Repository<Disease> diseaseRepository) {
        throw new UnsupportedOperationException();
    }
}
