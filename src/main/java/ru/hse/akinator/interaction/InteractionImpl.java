package ru.hse.akinator.interaction;

import ru.hse.akinator.model.Answer;
import ru.hse.akinator.model.Symptom;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
        return null;
    }

    @Override
    public void close() throws IOException {
        reader.close();
        printStream.close();
    }
}
