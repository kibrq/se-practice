package ru.hse.akinator.interaction;

import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.Symptom;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InteractionImpl implements Interaction {
    private final BufferedReader reader;
    private final PrintStream printStream;
    private final Function<Symptom, String> questionFormatter;

    public InteractionImpl(InputStream inputStream, PrintStream printStream, Function<Symptom, String> questionFormatter) {
        if (inputStream == null || printStream == null || questionFormatter == null) {
            throw new IllegalArgumentException();
        }
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.printStream = printStream;
        this.questionFormatter = questionFormatter;
    }

    @Override
    public Answer askAboutSymptom(Symptom s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        printStream.print(questionFormatter.apply(s));
        String answer;
        try {
            answer = reader.readLine();
        } catch (IOException e) {
            return Answer.IDK;
        }
        return Answer.valueOf(answer);
    }

    @Override
    public Map<Symptom, Answer> askAboutSymptoms(List<Symptom> symptoms) {
        if (symptoms == null) {
            throw new IllegalArgumentException();
        }
        return symptoms.stream().collect(
                Collectors.toMap(Function.identity(), this::askAboutSymptom)
        );
    }

    @Override
    public void close() throws IOException {
        reader.close();
        printStream.close();
    }
}
