package ru.hse.akinator.service;

import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Drug;
import ru.hse.akinator.repository.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class DrugService {
    public static List<Drug> getAllDrugsForDisease(List<Drug> allDrugs, Disease disease) {
        return allDrugs.stream()
                .filter(drug -> drug.getDiseases().contains(disease))
                .collect(Collectors.toList());
    }

    public static List<Drug> getAllDrugsForDiseaseFromRepo(Repository<Drug> drugsRepo, Disease disease) {
        return getAllDrugsForDisease(drugsRepo.getAll(), disease);
    }
}
