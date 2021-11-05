package ru.hse.akinator.interaction;

import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.Symptom;

import java.io.Closeable;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface Interaction extends Closeable {

    static Interaction create(InputStream is, PrintStream outputStream, Function<Symptom, String> questionFormatter) {
        throw new UnsupportedOperationException();
    }

    Answer askAboutSymptom(Symptom s);

    Map<Symptom, Answer> askAboutSymptoms(List<Symptom> symptoms);
}
