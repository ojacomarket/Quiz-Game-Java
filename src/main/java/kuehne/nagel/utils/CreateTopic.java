package kuehne.nagel.utils;

import kuehne.nagel.Response;
import kuehne.nagel.dao.DaoQuestion;
import lombok.Getter;

import java.sql.SQLException;
import java.util.List;

public class CreateTopic {
    @Getter
    private int queryResult;

    public int createTopicIfEmpty(String topic) {
        DaoQuestion daoq = new DaoQuestion();
        int topic_ID;
        SearchTopicIDbyTopicName search_topic_id = new SearchTopicIDbyTopicName();
        topic_ID = search_topic_id.searchTopicIDbyTopicName(topic);

        if (topic_ID != 0) {
            System.err.println("\nThis topic already exists, aborting to add...\n");
        }
        try {
            queryResult = daoq.createTopic(topic);
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
