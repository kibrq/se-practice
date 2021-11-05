package ru.hse.akinator.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.DoctorType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DoctorServiceTest {

    public static Object[][] testGetAllRelevantTypes_Source() {
        return new Object[][]{
                {List.of(), 1, List.of()},
                {List.of(List.of(0L)), 1, List.of()},
                {List.of(List.of(0L, 1L)), 1, List.of(0)},
                {List.of(List.of(0L), List.of(0L), List.of(0L)), 0, List.of(0, 1, 2)},
                {List.of(List.of(0L), List.of(0L), List.of(0L)), 2, List.of()}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetAllRelevantTypes_Source")
    public void testGetAllRelevantTypes(List<List<Long>> doctorTypeDiseaseDependencies, Integer diseaseNumber, List<Integer> relevantDoctorTypeNumbers) {
        List<Disease> allDiseases = TestUtils.randomDiseases(10, 10);
        List<DoctorType> allDoctorTypes = TestUtils.doctorTypesWithRandomNames(allDiseases, doctorTypeDiseaseDependencies);

        List<DoctorType> actualDoctorTypes = DoctorService.getAllRelevantTypes(allDoctorTypes, allDiseases.get(diseaseNumber));

        Set<DoctorType> expected = relevantDoctorTypeNumbers.stream()
                .map(allDoctorTypes::get)
                .collect(Collectors.toSet());
        Set<DoctorType> actual = new HashSet<>(actualDoctorTypes);
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
