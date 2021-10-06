package kuehne.nagel.utils;

import kuehne.nagel.dao.DaoQuestion;

import java.sql.SQLException;

public class VerifyQuestionDuplicate {
    int queryResult;

    public int verifyQuestionDuplicate (String topic, String question) {
        DaoQuestion daoq = new DaoQuestion();
        int q_t_id;
        int t_id;
        try {
            q_t_id = daoq.getTopicIdFromQuestionTable(question);
            t_id = daoq.getExistingTopicId(topic);
            System.out.println(q_t_id);
            System.out.println(t_id);
            if (q_t_id == t_id) {
                return 3;
            }
            /*if (queryResult == 0) {
                //System.out.println("\nNo such a question...\n");
                return queryResult;
            }*/
        } catch (
                SQLException exep) {
            return 0;
        }
        return 5;
    }
}
