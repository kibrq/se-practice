package ru.hse.akinator;

import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.DoctorType;
import ru.hse.akinator.model.Model;
import ru.hse.akinator.model.Symptom;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {
    private static final Random random = new Random();

    public static String randomAlphabeticString(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static List<String> randomListOfAlphabeticStrings(int size, int stringSize) {
        return Stream.generate(() -> randomAlphabeticString(stringSize))
                .limit(size)
                .collect(Collectors.toList());
    }

    public static <T extends Model> List<T> createWithIds(Function<Long, T> create, int size) {
        return IntStream.range(0, size)
                .mapToObj(id -> create.apply((long) id))
                .collect(Collectors.toList());
    }


    public static List<Symptom> symptomsFromNames(List<String> names) {
        Function<Long, Symptom> create = id -> {
            var s = mock(Symptom.class);
            when(s.getId()).thenReturn(id);
            when(s.getName()).thenReturn(names.get(id.intValue()));
            return s;
        };
        return createWithIds(create, names.size());
    }

    public static List<Symptom> symptomsFromRandomNames(int size) {
        return symptomsFromNames(
                randomListOfAlphabeticStrings(size, 4)
        );
    }

    public static List<Disease> diseasesFromSymptoms(List<Symptom> allSymptoms, List<String> names, List<List<Long>> ids) {
        if (names.size() != ids.size()) {
            throw new IllegalArgumentException();
        }

        Map<Long, Symptom> symptomById = allSymptoms.stream().collect(Collectors.toMap(
                Model::getId, Function.identity()
        ));

        Function<Long, Disease> create = id -> {
            var d = mock(Disease.class);
            when(d.getSymptoms()).thenReturn(ids.get(id.intValue()).stream()
                    .map(symptomById::get)
                    .collect(Collectors.toList()));
            when(d.getName()).thenReturn(names.get(id.intValue()));
            when(d.getId()).thenReturn(id);
            return d;
        };

        return createWithIds(create, names.size());
    }

    public static List<Disease> diseasesFromSymptomsWithRandomNames(List<Symptom> allSymptoms, List<List<Long>> ids) {
        return diseasesFromSymptoms(allSymptoms, randomListOfAlphabeticStrings(ids.size(), 4), ids);
    }

    public static List<Disease> randomDiseases(int nSymptoms, int nDiseases) {
        List<Symptom> symptoms = symptomsFromRandomNames(nSymptoms);
        List<Long> ids = LongStream.range(0, nSymptoms).boxed().collect(Collectors.toList());
        List<List<Long>> idSymptoms = Stream.generate(() -> {
                    Collections.shuffle(ids);
                    return ids.subList(0, random.nextInt(nSymptoms - 1) + 1);
                })
                .limit(nDiseases)
                .collect(Collectors.toList());
        return diseasesFromSymptomsWithRandomNames(symptoms, idSymptoms);
    }

    public static Set<Disease> randomDiseasesAsSet(int nSymptoms, int nDiseases) {
        return new HashSet<>(randomDiseases(nSymptoms, nDiseases));
    }

    public static DoctorType doctorTypeWithRandomName() {
        var d = mock(DoctorType.class);
        Set<Disease> diseases = randomDiseasesAsSet(5, 3);
        when(d.getDiseases()).thenReturn(diseases);
        when(d.getName()).thenReturn(randomAlphabeticString(4));
        when(d.getId()).thenReturn(1L);
        return d;
    }
}
