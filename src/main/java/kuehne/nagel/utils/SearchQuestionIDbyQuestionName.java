package kuehne.nagel.utils;

import kuehne.nagel.dao.DaoQuestion;
import lombok.Getter;

import java.sql.SQLException;

public class SearchQuestionIDbyQuestionName {
    @Getter
    private int queryResult;

    public int searchQuestionIdByQuestionName (String question) {
        DaoQuestion daoq = new DaoQuestion();
        try {
            queryResult = daoq.getExistingQuestionId(question);
            if (queryResult == 0) {
                //System.out.println("\nNo such a question...\n");
                return queryResult;
            }
        } catch (
                SQLException exep) {
            return 0;
        }
        return queryResult;
    }
}
