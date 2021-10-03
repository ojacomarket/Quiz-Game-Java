package kuehne.nagel.utils;

import kuehne.nagel.dao.DaoQuestion;
import lombok.Getter;

import java.sql.SQLException;

public class GetUnusedId {
    @Getter
    private int queryResult;

    public int getUnusedId (String table) {
        DaoQuestion daoq = new DaoQuestion();
        try {
            queryResult = daoq.getUnusedIDs(table);
            if (queryResult == 0) {
                System.out.println("\nNo id found...\n");
                return queryResult;
            }
        } catch (
                SQLException exep) {
            return 0;
        }
        return queryResult;
    }
}
