package ru.hse.akinator.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.hse.akinator.TestUtils;
import ru.hse.akinator.model.Disease;
import ru.hse.akinator.model.Doctor;
import ru.hse.akinator.model.DoctorType;
import ru.hse.akinator.repository.Repository;

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
        List<Disease> allDiseases = TestUtils.diseases(10, 10);
        List<DoctorType> allDoctorTypes = TestUtils.doctorTypes(allDiseases, doctorTypeDiseaseDependencies);

        List<DoctorType> actualDoctorTypes = DoctorService.getAllRelevantTypes(allDoctorTypes, allDiseases.get(diseaseNumber));

        Set<DoctorType> expected = relevantDoctorTypeNumbers.stream()
                .map(allDoctorTypes::get)
                .collect(Collectors.toSet());
        Set<DoctorType> actual = new HashSet<>(actualDoctorTypes);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static Object[][] testGetAllRelevantDoctors_Source() {
        return new Object[][]{
                {List.of(), List.of(0, 1), List.of()},
                {List.of(0L), List.of(0, 1), List.of(0)},
                {List.of(0L, 0L, 1L, 0L), List.of(0, 2), List.of(0, 1, 3)},
                {List.of(0L, 0L, 1L, 0L), List.of(1), List.of(2)},
                {List.of(0L, 0L, 1L, 0L), List.of(0, 1), List.of(0, 1, 2, 3)}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetAllRelevantDoctors_Source")
    public void testGetAllRelevantDoctors(List<Long> doctorDoctorTypeDependencies, List<Integer> testedDoctorTypeNumbers, List<Integer> relevantDoctorNumbers) {
        List<Disease> allDiseases = TestUtils.diseases(10, 10);
        List<DoctorType> allDoctorTypes = TestUtils.doctorTypes(allDiseases, 10);
        List<Doctor> allDoctors = TestUtils.doctors(allDoctorTypes, doctorDoctorTypeDependencies);

        List<Doctor> actualDoctors = DoctorService.getAllRelevantDoctors(
                allDoctors, testedDoctorTypeNumbers.stream().map(allDoctorTypes::get).collect(Collectors.toList())
        );

        Set<Doctor> expected = relevantDoctorNumbers.stream()
                .map(allDoctors::get)
                .collect(Collectors.toSet());
        Set<Doctor> actual = new HashSet<>(actualDoctors);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static Object[][] testGetLessBusinessDoctor_Source() {
        return new Object[][]{
                {List.of(List.of(0L, 1L, 2L)), List.of(), List.of(), 0, -1},
                {List.of(List.of(0L, 1L, 2L)), List.of(0L), List.of(1.), 3, -1},
                {List.of(List.of(0L, 1L, 2L)), List.of(0L), List.of(1.), 0, 0},
                {List.of(List.of(0L, 1L, 2L)), List.of(0L, 0L), List.of(1., 2.), 1, 0},
                {List.of(List.of(0L, 1L), List.of(0L, 2L)), List.of(0L, 1L), List.of(1., 2.), 0, 0}
        };
    }

    @ParameterizedTest
    @MethodSource("testGetLessBusinessDoctor_Source")
    public void testGetLessBusinessDoctor(
            List<List<Long>> doctorTypeDiseaseDependencies,
            List<Long> doctorDoctorTypeDependencies,
            List<Double> businesses,
            int diseaseNumber,
            int doctorNumber
    ) {
        List<Disease> allDiseases = TestUtils.diseases(10, 10);
        List<DoctorType> allDoctorTypes = TestUtils.doctorTypes(allDiseases, doctorTypeDiseaseDependencies);
        List<Doctor> allDoctors = TestUtils.doctors(allDoctorTypes, doctorDoctorTypeDependencies, businesses);
        Repository<Doctor> doctorRepository = TestUtils.repositoryFromList(allDoctors);

        Doctor expected = doctorNumber == -1 ? null : allDoctors.get(doctorNumber);
        Doctor actual = DoctorService.getLessBusynessDoctor(allDiseases.get(diseaseNumber), doctorRepository);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
