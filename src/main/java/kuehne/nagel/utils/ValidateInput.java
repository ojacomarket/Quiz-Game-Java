package kuehne.nagel.utils;

import kuehne.nagel.Answer;

import java.util.List;

public abstract class ValidateInput {
    /**
     * Validates topic, question, difficulty and answers before opening database connection
     * @param topic
     * @param question
     * @param difficulty
     * @param answers
     * @return
     */
    public static int validateInputQuery(String topic, String question, int difficulty, List<Answer> answers) {
        if (difficulty < 1 || difficulty > 5) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM ::: Lowest difficulty level should begin with value of 1 and biggest is 5");
            return -3;
        }
      /*  else if (answers.length == 1) {
            System.err.println("\nHow user can answer a question, if there isn't/aren't answers on it/them...");
            return -3;
        } */else if (question.isEmpty()) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM ::: Question cannot be an empty string");
            return -4;
        } else if (topic.isEmpty()) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM ::: Empty string cannot be a topic");
            return -5;
        }

        if (answers == null) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM ::: How user can answer a question, if there isn't/aren't answers on it/them?");
            return -6;
        }
        return 1;
    }
    /*public static int validateSearchTopicByTopicName (int topic_ID) {
        if (topic_ID == 0) {
            System.err.println("APPLICATION >>> Aborting to receive SQL queries...\n\t\t\t>>>" +
                    " PROBLEM ::: Cannot add question to the topic that doesn't exist");
            return -4;
        }
        else if (topic_ID == -1) {
            System.err.println("APPLICATION >>> Aborting to receive SQL queries...\n\t\t\t>>>" +
                    " PROBLEM ::: Problems with database connection, check property file");
            return -5;
        }
        return 1;
    }*/
}
