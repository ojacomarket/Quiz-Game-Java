package kuehne.nagel.utils;

import kuehne.nagel.Question;
import kuehne.nagel.dao.DaoQuestion;
import lombok.Getter;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
@Deprecated
public class SearchTopicIDbyTopicNameUtil {

  /*  @Getter
    private int queryResult;

    public int searchTopicIDbyTopicName (String topic) {
    DaoQuestion daoq = new DaoQuestion();
        try {
        queryResult = daoq.getExistingTopicId(topic);
        if (queryResult == 0) {
            //System.err.println("\nNo such a topic...\n");
            return queryResult;
        } else if (queryResult == 1) {
            return queryResult;
        }
        else if (queryResult == 2) {
            return queryResult;
        }
    } catch (SQLException exep) {
        return 0;
    }
        return queryResult;
}*/
}
