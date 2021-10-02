
package kuehne.nagel.utils;

import kuehne.nagel.Question;
import kuehne.nagel.dao.DaoQuestion;
import lombok.Getter;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SearchQuestionByTopic {

    @Getter
    private List<Question> queryResult;

    public List<Question> searchQuestionByTopic(String topic) {
        DaoQuestion daoq = new DaoQuestion();
        try {
            queryResult = daoq.searchQuestion(topic);
            if (queryResult.isEmpty()) {
                System.out.println("\nNo question on that topic...\n");
                return queryResult;
            }
            for (Question question : queryResult) {

                System.out.printf("Topic name ::: %s\nQuestion ::: %s\nDifficulty ::: %d\n\n", question.getTopic_name(),
                        question.getContent(), question.getDifficulty());
            }
        } catch (SQLException exep) {
            return new LinkedList<>();
        }
        return queryResult;
    }
}

