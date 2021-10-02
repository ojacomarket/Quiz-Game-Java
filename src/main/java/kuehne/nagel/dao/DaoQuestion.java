package kuehne.nagel.dao;

import kuehne.nagel.Question;
import lombok.Data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Data
public class DaoQuestion extends DatabaseConnection implements DaoQuestionI {

    private static final String SQL_QUERY_SEARCH_QUESTION_BY_TOPIC =
            "SELECT name,content,difficulty FROM topic INNER JOIN question ON topic.ID = question.topic_ID WHERE name LIKE ?";
    //private static final String SQL_QUERY_SAVE_NEW_QUESTION;

    public List<Question> searchQuestion(String topic) throws SQLException {
        int track = 0;
        if (topic.isEmpty()) {
            return new LinkedList<>();
        }
        //topic = topic.toLowerCase();
        List<Question> result_question = new LinkedList<>();
        Connection connect = null;
        PreparedStatement def_query = null;

        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return new LinkedList<>();
            }
            def_query = connect.prepareStatement(SQL_QUERY_SEARCH_QUESTION_BY_TOPIC);
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.setString(1, topic);

            //Since def_query is filled with setString we can execute it
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                track++;
                //Read first and second column in table user
                //System.out.println(data.getLong(1) + " ::: " + data.getString(2));
                Question output = new Question(data.getString("name"), data.getString("content"),
                        data.getInt("difficulty"));
                result_question.add(output);
            }
            if (track == 0) {
                //System.out.println("\nNo questions defined in that topic...\n");
                return new LinkedList<> ();
            }
        } catch (SQLException sql_ex) {
            throw new SQLException();
            //throw new DaoException(sql_ex);
            //System.err.println("\nError occurred inside DatabaseConnection class --> getConnectionToDb method\n");
            //return null;
        } finally {
            connect.close();
            def_query.close();
        }
        return result_question;
    }
}
