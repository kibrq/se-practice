package ru.hse.akinator;

import ru.hse.akinator.interaction.Interaction;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Symptom;
import ru.hse.akinator.repository.Repository;
import ru.hse.akinator.service.DiseaseDeterminantService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Akinator {
	private static Repository<Symptom> getSymptomRepository() {
		Repository<Symptom> symptomRepository = Repository.create(Symptom.class);
		List<String> symptomNames = List.of("soar throat", "0iq", "stomachache", "pain in the ass");

		IntStream.range(0, symptomNames.size()).forEach(id -> {
			var symptom = Symptom.create((long) id, symptomNames.get(id));
			symptomRepository.add(symptom);
		});

		return symptomRepository;
	}

	private static <T> List<T> randomSubList(Random random, List<T> list) {
		list = new ArrayList<>(list);
		Collections.shuffle(list, random);
		return list.subList(0, random.nextInt(list.size()));
	}

	private static Repository<Disease> getDiseaseRepository(Repository<Symptom> symptomRepository) {
		Repository<Disease> diseaseRepository = Repository.create(Disease.class);
		List<String> diseaseNames = List.of("cancer", "acute navel failure", "yet another disease", "rab jetbrains");
		List<Symptom> allSymptoms = symptomRepository.getAll();
		Random random = new Random();

		IntStream.range(0, diseaseNames.size()).forEach(id -> {
			var disease = Disease.create((long) id, diseaseNames.get(id), randomSubList(random, allSymptoms));
			diseaseRepository.add(disease);
		});

		return diseaseRepository;
	}

	public static void main(String[] args) {
		Function<Symptom, String> questionFormatter = s -> "Do you have " + s.getName() + "?\n";
		Interaction interaction = Interaction.create(System.in, System.out, questionFormatter);
		Repository<Symptom> symptomRepository = getSymptomRepository();
		Repository<Disease> diseaseRepository = getDiseaseRepository(symptomRepository);
		List<Disease> relevantDiseases =
				DiseaseDeterminantService.diseasesSortedByRelevance(interaction, symptomRepository, diseaseRepository);
		System.out.println("\nWe think the most relevant disease is " + relevantDiseases.get(0).getName());
	}
}
