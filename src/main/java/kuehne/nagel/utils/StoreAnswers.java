package kuehne.nagel.utils;

import kuehne.nagel.Response;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StoreAnswers {
    List<Response> storedAnswers = new LinkedList<>();

    public List<Response> saveAnswerLocally(String ... answers) {
        for (String answer : answers) {
            storedAnswers.add(new Response(answer));
        }
        return storedAnswers;
    }
}
