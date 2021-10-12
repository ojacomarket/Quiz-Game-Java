package kuehne.nagel.utils;

import java.util.Collection;

public abstract class ValidateInput {

    /**
     * User input validation, when wish to start interaction with database using SAVE QUERY
     *
     * @param topic
     * @param question
     * @param difficulty
     * @param answers
     * @return if passed validation = 1, otherwise = 0
     */
    public static int validateJDBCSaveQueryBeforeDbConnection(String topic, String question, int difficulty, Collection<String> answers) {
        if (difficulty < 1 || difficulty > 5) {
            System.err.println("Lowest difficulty level should begin with value of 1 and biggest is 5");
            return 0;
        } else if (question.isEmpty()) {
            System.err.println("Question cannot be an empty string");
            return 0;
        } else if (topic.isEmpty()) {
            System.err.println("Empty string cannot be a topic");
            return 0;
        } else if (answers.isEmpty()) {
            System.err.println("How user can answer a question, if there isn't/aren't answers on it/them?");
            return 0;
        }
        return 1;
    }

    /**
     * User input validation, when wish to start interaction with database using SEARCH QUERY
     *
     * @param topic
     * @return if passed validation = 1, otherwise = 0
     */
    public static int validateJDBCSearchQueryBeforeDbConnection(String topic) {
        if (topic.isEmpty()) {
            System.err.println("Topic cannot be an empty string");
            return 0;
        }
        return 1;
    }
}
