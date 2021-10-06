package kuehne.nagel.utils;

import kuehne.nagel.Response;
import kuehne.nagel.dao.DaoQuestion;

import java.sql.SQLException;
import java.util.List;

public class DeleteQuestion {
    public int deleteQuestion(String topic, String question) {

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

        try {
            queryResult = daoq.deleteQuestion(topic, question);
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
