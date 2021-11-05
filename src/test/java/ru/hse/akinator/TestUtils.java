package ru.hse.akinator;

import ru.hse.akinator.interaction.Interaction;
import ru.hse.akinator.model.*;
import ru.hse.akinator.repository.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {
    public static String randomAlphabeticString(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

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

    public static <T extends Model> Repository<T> repositoryFromList(List<T> items) {
        return new Repository<>() {
            @Override
            public List<T> getAll() {
                return items;
            }

            @Override
            public T getById(Long id) {
                return items.stream().filter(t -> Objects.equals(t.getId(), id)).findFirst().orElse(null);
            }

            @Override
            public boolean add(T value) {
                boolean added = getById(value.getId()) != null;
                items.add(value);
                return added;
            }
        };
    }

    public static Interaction interactionFromSymptomAnswerMap(Map<Symptom, Answer> map) {
        return new Interaction() {
            @Override
            public void close() {}

            @Override
            public Answer askAboutSymptom(Symptom s) {
                return map.get(s);
            }

            @Override
            public Map<Symptom, Answer> askAboutSymptoms(List<Symptom> symptoms) {
                var result = new HashMap<Symptom, Answer>();
                symptoms.forEach(s -> result.put(s, map.get(s)));
                return result;
            }
        };
    }
}
