package kuehne.nagel.utils;

import kuehne.nagel.dao.DaoQuestion;
import lombok.Getter;

import java.sql.SQLException;
@Deprecated
public class GetUnusedId {
    @Getter
    private int queryResult;

   /* public int getUnusedId (String table) {
        DaoQuestion daoq = new DaoQuestion();
        try {
            queryResult = daoq.getUnusedIDs(table);
            if (queryResult == 0) {
                System.out.println("\nDatabase connection problems... Check property file!\n");
                return queryResult;
            }
        } catch (
                SQLException exep) {
            return 0;
        }
        return queryResult;
    }*/
}
