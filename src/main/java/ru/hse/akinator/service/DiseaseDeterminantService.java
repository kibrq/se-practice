
package ru.hse.akinator.service;

import ru.hse.akinator.interaction.Interaction;
import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Symptom;
import ru.hse.akinator.repository.Repository;

import java.util.*;

public class DiseaseDeterminantService {

    public static double measureProbability(Disease disease, Map<Symptom, Answer> answerBySymptoms) {
        int counter = 0;
        for (Map.Entry<Symptom, Answer> entry : answerBySymptoms.entrySet()) {
            String name = entry.getKey().getName();
            boolean isSymptomInDisease = disease.getSymptoms().stream().anyMatch(symptom -> Objects.equals(symptom.getName(), name));
            switch (entry.getValue()) {
                case DEFINITELY_YES -> counter += isSymptomInDisease ? 2 : -2;
                case MORE_YES_THAN_NO -> counter += isSymptomInDisease ? 1 : -1;
                case MORE_NO_THAN_YES -> counter += isSymptomInDisease ? -1 : 1;
                case DEFINITELY_NO -> counter += isSymptomInDisease ? -2 : 2;
            }
        }
        return (1 + (double) (counter) / (answerBySymptoms.size() * 2)) / 2;
    }

    public static void sortByRelevance(List<Disease> diseases, Map<Symptom, Answer> answerBySymptoms) {
        class DiseaseRelevance {
            final Disease disease;
            final Double relevance;

            public DiseaseRelevance(Disease disease, double relevance) {
                this.disease = disease;
                this.relevance = relevance;
            }
        }
        ;
        List<DiseaseRelevance> relevances = new LinkedList<>();
        for (Disease disease : diseases) {
            relevances.add(new DiseaseRelevance(disease, measureProbability(disease, answerBySymptoms)));
        }
        relevances.sort(Collections.reverseOrder(Comparator.comparing(obj -> obj.relevance)));
        diseases.clear();
        for (DiseaseRelevance diseaseRelevance : relevances) {
            diseases.add(diseaseRelevance.disease);
        }
    }

    public static List<Disease> diseasesSortedByRelevance(Interaction interaction, Repository<Symptom> symptomRepository, Repository<Disease> diseaseRepository) {
        throw new UnsupportedOperationException();
    }
}
