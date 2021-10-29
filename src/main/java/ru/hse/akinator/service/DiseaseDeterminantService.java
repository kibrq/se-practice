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
        throw new UnsupportedOperationException();
    }

    public static void sortByRelevance(List<Disease> diseases, Map<Symptom, Answer> answerBySymptoms) {
        throw new UnsupportedOperationException();
    }

    public static List<Disease> diseasesSortedByRelevance(Interaction interaction, Repository<Symptom> symptomRepository, Repository<Disease> diseaseRepository) {
        throw new UnsupportedOperationException();
    }
}
