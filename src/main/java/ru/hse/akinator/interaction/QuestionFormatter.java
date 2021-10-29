package ru.hse.akinator.interaction;

import ru.hse.akinator.model.Symptom;

public interface QuestionFormatter {
    String questionAboutSymptom(Symptom symptom);
}
