package kuehne.nagel.utils;

import kuehne.nagel.Answer;
import kuehne.nagel.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AddResponse {
    public int addResponse (String query, List<Answer> answers, int question_ID) throws SQLException {
        Connection connect = null;
        PreparedStatement def_query;
        PreparedStatement def_query2;

        try {
            connect = DatabaseConnection.getConnectionToDb(1);
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 2;
            }
            connect.setAutoCommit(false);
        def_query2 = connect.prepareStatement(query);
        for (Answer answer:answers
        ) {
            def_query2.setString(1,answer.getAnswer());
            def_query2.setInt(2, question_ID);
            //TODO: Add UPDATE CASCADE to ALL FOREIGN KEYS IN SQL
            def_query2.executeQuery();
        }
        connect.commit();
    } catch (Exception q) {
        connect.rollback();
        q.printStackTrace();
    }
        return 11;
    }
}
