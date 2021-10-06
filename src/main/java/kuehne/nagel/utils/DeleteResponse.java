package kuehne.nagel.utils;

import kuehne.nagel.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteResponse {

    public int deleteResponse(String query, int question_ID) throws SQLException {
        Connection connect = null;
        PreparedStatement def_query;
        PreparedStatement def_query2;
        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 2;
            }
            connect.setAutoCommit(false);
            def_query2 = connect.prepareStatement(query);
            def_query2.setInt(1, question_ID);
            def_query2.executeUpdate();
            connect.commit();
        } catch (SQLException throwables) {
            return 1;
        } finally {
            connect.close();
        }
        return 88;
    }
}
