package kuehne.nagel.dao;

import kuehne.nagel.Question;
import lombok.Data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Data
public class DaoQuestion extends DatabaseConnection implements DaoQuestionI {

    private static final String SQL_QUERY_SEARCH_QUESTION_BY_TOPIC =
            "SELECT name, content, difficulty FROM topic INNER JOIN question ON " +
                    "topic.ID = question.topic_ID WHERE UPPER(name) LIKE UPPER(?)";
    //private static final String SQL_QUERY_SAVE_NEW_QUESTION;

    public List<Question> searchQuestion(String topic) throws DaoException {

        List<Question> result_question = new LinkedList<>();

        try (
                Connection connect = DatabaseConnection.getConnectionToDb();
                PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_SEARCH_QUESTION_BY_TOPIC);
        ) {
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.setString(1, topic);

            //Since def_query is filled with setString we can execute it
            ResultSet data = def_query.executeQuery();

            while (data.next()) {
                //Read first and second column in table user
                //System.out.println(data.getLong(1) + " ::: " + data.getString(2));
                Question output = new Question(data.getString("content"), data.getInt("difficulty"),
                        data.getString("name"));
                result_question.add(output);
            }
        } catch (Exception sql_ex) {
            throw new DaoException(sql_ex);
        }
        return result_question;
    }
}
