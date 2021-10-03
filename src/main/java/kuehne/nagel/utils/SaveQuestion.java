package kuehne.nagel.utils;

import kuehne.nagel.Response;
import kuehne.nagel.dao.DaoQuestion;
import lombok.Getter;

import java.sql.SQLException;
import java.util.List;

public class SaveQuestion {

    public int saveQuestion(String question, int difficulty, String topic, List<Response> answers) {

        int topic_ID;
        int queryResult;
        DaoQuestion daoq = new DaoQuestion();
        SearchTopicIDbyTopicName search_topic_id = new SearchTopicIDbyTopicName();
        topic_ID = search_topic_id.searchTopicIDbyTopicName(topic);

        if (topic_ID == 0) {
            System.err.println("Cannot add question to the topic that doesn't exist...\n");
            return 0;
        } else if (question.isEmpty()) {
            System.err.println("\nSeems, that you forgot to store a question...\n");
            return 0;
        } else if (difficulty == 0) {
            System.err.println("Lowest difficulty level should begin with value of 1...");
            return 0;
        } else if (answers.isEmpty()) {
            System.err.println("How user can answer a question, if there isn't/aren't answers on it/them...");
            return 0;
        }

        try {
            queryResult = daoq.saveQuestion(question, difficulty, topic, answers, topic_ID);
            if (queryResult == 1) {
                System.out.println("\nNo question on that topic...\n");
                return queryResult;
            }

        } catch (SQLException exep) {
            return 1;
        }
        return queryResult;
    }
}
