package kuehne.nagel.utils;

import kuehne.nagel.Answer;

import java.util.LinkedList;
import java.util.List;

@Deprecated
public class StoreAnswers {
    List<Answer> storedAnswers = new LinkedList<>();

    public List<Answer> saveAnswerLocally(String ... answers) {
        List<Answer> storedAnswers = new LinkedList<>();
        for (String answer : answers) {
            storedAnswers.add(new Answer(answer));
        }
        return storedAnswers;
    }
}
