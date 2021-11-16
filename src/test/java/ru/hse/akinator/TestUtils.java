package ru.hse.akinator;

import ru.hse.akinator.interaction.Interaction;
import ru.hse.akinator.model.Model;
import ru.hse.akinator.model.Symptom;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.DoctorType;
import ru.hse.akinator.model.Doctor;

import ru.hse.akinator.repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Objects;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
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

    public static List<Symptom> symptoms(List<String> names) {
        Function<Long, Symptom> create = id -> {
            var s = mock(Symptom.class);
            when(s.getId()).thenReturn(id);
            when(s.getName()).thenReturn(names.get(id.intValue()));
            return s;
        };
        return createWithIds(create, names.size());
    }

    public static List<Symptom> symptoms(int size) {
        return symptoms(
                randomListOfAlphabeticStrings(size, 4)
        );
    }

    public static List<Disease> diseases(List<Symptom> allSymptoms, List<String> names, List<List<Long>> ids) {
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

    public static List<Disease> diseases(List<Symptom> allSymptoms, List<List<Long>> ids) {
        return diseases(allSymptoms, randomListOfAlphabeticStrings(ids.size(), 4), ids);
    }

    public static List<Disease> diseases(int nSymptoms, int nDiseases) {
        List<Symptom> symptoms = symptoms(nSymptoms);
        List<Long> ids = LongStream.range(0, nSymptoms).boxed().collect(Collectors.toList());
        List<List<Long>> idSymptoms = Stream.generate(() -> {
                    Collections.shuffle(ids);
                    return ids.subList(0, random.nextInt(nSymptoms - 1) + 1);
                })
                .limit(nDiseases)
                .collect(Collectors.toList());
        return diseases(symptoms, idSymptoms);
    }

    public static Set<Disease> diseasesAsSet(int nSymptoms, int nDiseases) {
        return new HashSet<>(diseases(nSymptoms, nDiseases));
    }

    public static DoctorType doctorType() {
        var d = mock(DoctorType.class);
        Set<Disease> diseases = diseasesAsSet(5, 3);
        when(d.getDiseases()).thenReturn(diseases);
        when(d.getName()).thenReturn(randomAlphabeticString(4));
        when(d.getId()).thenReturn(1L);
        return d;
    }

    public static List<DoctorType> doctorTypes(List<Disease> allDiseases, List<List<Long>> doctorTypeDiseaseDependencies, List<String> names) {

        Map<Long, Disease> diseasesById = allDiseases.stream().collect(
                Collectors.toMap(
                        Disease::getId,
                        Function.identity())
        );

        Function<Long, DoctorType> create = id -> {
            var d = mock(DoctorType.class);
            when(d.getId()).thenReturn(id);
            when(d.getDiseases()).thenReturn(
                    doctorTypeDiseaseDependencies.get(id.intValue()).stream()
                            .map(diseasesById::get)
                            .collect(Collectors.toSet())
            );
            when(d.getName()).thenReturn(names.get(id.intValue()));
            return d;
        };

        return createWithIds(create, names.size());
    }

    public static List<DoctorType> doctorTypes(List<Disease> allDiseases, List<List<Long>> ids) {
        List<String> names = randomListOfAlphabeticStrings(ids.size(), 4);
        return doctorTypes(allDiseases, ids, names);
    }

    public static List<DoctorType> doctorTypes(List<Disease> allDiseases, int nDoctorTypes) {
        List<Long> ids = allDiseases.stream().map(Model::getId).collect(Collectors.toList());
        List<List<Long>> doctorTypeDiseaseDependencies = Stream.generate(() -> {
                    Collections.shuffle(ids);
                    return ids.subList(0, random.nextInt(allDiseases.size() - 1) + 1);
                })
                .limit(nDoctorTypes)
                .collect(Collectors.toList());
        return doctorTypes(allDiseases, doctorTypeDiseaseDependencies);
    }

    public static List<Doctor> doctors(List<String> names, List<DoctorType> allDoctorTypes, List<Long> doctorTypesIds, List<Double> businesses) {
        Function<Long, Doctor> create = id -> {
            var d = mock(Doctor.class);
            when(d.getId()).thenReturn(id);
            when(d.getName()).thenReturn(names.get(id.intValue()));
            when(d.getType()).thenReturn(allDoctorTypes.get(doctorTypesIds.get(id.intValue()).intValue()));
            when(d.getBusyness()).thenReturn(businesses.get(id.intValue()));
            return d;
        };

        return createWithIds(create, names.size());
    }

    public static List<Doctor> doctors(List<DoctorType> allDoctorTypes, List<Long> doctorTypeIds, List<Double> businesses) {
        List<String> names = randomListOfAlphabeticStrings(doctorTypeIds.size(), 4);
        return doctors(names, allDoctorTypes, doctorTypeIds, businesses);
    }

    public static List<Doctor> doctors(List<DoctorType> allDoctorTypes, List<Long> doctorTypeIds) {
        List<Double> businesses = random.doubles().limit(doctorTypeIds.size()).boxed().collect(Collectors.toList());
        return doctors(allDoctorTypes, doctorTypeIds, businesses);
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
