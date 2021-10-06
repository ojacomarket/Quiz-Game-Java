package kuehne.nagel.utils;

import kuehne.nagel.Response;
import kuehne.nagel.dao.DaoQuestion;

import java.sql.SQLException;
import java.util.List;

public class UpdateQuestion {
    public int updateQuestion(String topic, String old_question, String new_question, int difficulty, List<Response> answers) {

        int topic_ID;
        int queryResult;

        DaoQuestion daoq = new DaoQuestion();
        SearchTopicIDbyTopicName search_topic_id = new SearchTopicIDbyTopicName();
        //If topic exists in db, the ID of that topic is returned, so we can proceed forward
        topic_ID = search_topic_id.searchTopicIDbyTopicName(topic);
        System.out.println("TOPIC ID IS "+topic_ID);

        if (topic_ID == 0) {
            System.err.println("Cannot update a question of the topic that doesn't exist...\n");
            return 0;
        } //TODO: Added new error code 1
        else if (topic_ID == 1) {
            System.err.println("Problems with database connection, check property file\n");
            return 0;
        }
        else if (topic_ID == 2) {
            System.err.println("Cannot use empty string as a topic...\n");
            return 0;
        }
        else if (difficulty == 0) {
            System.err.println("Lowest difficulty level should begin with value of 1...");
            return 0;
        } else if (answers.isEmpty()) {
            System.err.println("How user can answer a question, if there isn't/aren't answers on it/them...");
            return 0;
        }

        try {
            queryResult = daoq.updateQuestion(topic, topic_ID, old_question, new_question, difficulty, answers);
            if (queryResult == 0) {
                System.err.println("This question already exists... Cannot duplicate!\n");
                return queryResult;
            }

        } catch (SQLException exep) {
            return 1;
        }
        return queryResult;
    }
}
