package kuehne.nagel.dao;

import kuehne.nagel.Question;
import kuehne.nagel.Response;
import kuehne.nagel.utils.GetUnusedId;
import kuehne.nagel.utils.SearchQuestionIDbyQuestionName;
import kuehne.nagel.utils.SearchTopicIDbyTopicName;
import kuehne.nagel.utils.VerifyQuestionDuplicate;
import lombok.Data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Data
public class DaoQuestion extends DatabaseConnection implements DaoQuestionI {

    private static final String SQL_QUERY_SEARCH_QUESTION_BY_TOPIC =
            "SELECT name,content,difficulty FROM topic INNER JOIN question ON topic.ID = question.topic_ID WHERE name LIKE ?";
    private static final String SQL_QUERY_SAVE_QUESTION_TO_EXISTING_TOPIC = "INSERT INTO question (content, difficulty, topic_ID) VALUES (?,?,?)";
    private static final String SQL_QUERY_TOPIC_ID_BY_TOPIC_NAME = "SELECT ID FROM topic WHERE name=?";
    private static final String SQL_QUERY_QUESTION_ID_BY_QUESTION_NAME = "SELECT ID FROM question WHERE content=?";
    private static final String SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION = "INSERT INTO response (answer, question_ID) VALUES (?,?)";
    private static final String SQL_QUERY_RETRIEVE_UNUSED_ID = "SELECT MAX(ID) FROM ";
    private static final String SQL_QUERY_CREATE_TOPIC = "INSERT INTO topic (name) VALUES (?)";
    private static final String SQL_QUERY_TOPIC_ID_FROM_QUESTION_TABLE = "SELECT topic_ID FROM question WHERE content=?";

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
                return new LinkedList<>();
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

    public int saveQuestion (String question, int difficulty, String topic, List<Response> answers, int topic_ID) throws SQLException {

        //int topic_ID;
        int question_ID;
        int verify;

        /*SearchTopicIDbyTopicName search_topic_id = new SearchTopicIDbyTopicName();
        topic_ID = search_topic_id.searchTopicIDbyTopicName(topic);*/
        SearchQuestionIDbyQuestionName search_question_id = new SearchQuestionIDbyQuestionName();
        question_ID = search_question_id.searchQuestionIdByQuestionName(question);
        System.out.println("QUESTION ID IS "+question_ID);

        if (/*topic_ID == 0 || */question_ID == 0) {
            //if (question_ID == 0) {
                GetUnusedId new_q_id = new GetUnusedId();
                question_ID = new_q_id.getUnusedId("question") + 1;
            } else {
            VerifyQuestionDuplicate verification = new VerifyQuestionDuplicate();
            verify = verification.verifyQuestionDuplicate(topic,question);
            if (verify == 3) {
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL\n");
                return 0;
            }
            //return 1;
        }
        //}
       /* else if (question_ID > 0) {
            return 0;
        }*/
     //   System.err.println(question_ID);
     //   Question result_question;
        Connection connect = null;
        PreparedStatement def_query;
        PreparedStatement def_query2 = null;

        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 2;
            }
            connect.setAutoCommit(false);
            def_query = connect.prepareStatement(SQL_QUERY_SAVE_QUESTION_TO_EXISTING_TOPIC);
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.setString(1, question);
            def_query.setInt(2, difficulty);
            def_query.setInt(3, topic_ID);
            //ResultSet data = def_query.executeUpdate();
            def_query.executeUpdate();
            def_query2 = connect.prepareStatement(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION);
            for (Response answer:answers
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
        return 3;
    }
    public int createTopic (String topic) throws SQLException {
        int topic_ID;
        int track = 0;

        Connection connect = null;
        PreparedStatement def_query;

        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                return 2;
            }

            def_query = connect.prepareStatement(SQL_QUERY_CREATE_TOPIC);
            def_query.setString(1,topic);
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.executeUpdate();

        } catch (Exception q) {
            q.printStackTrace();
        }
        return 0;
    }
    public int getExistingTopicId(String topic) throws SQLException {

        int track = 0;
        int topic_ID = 0;

        if (topic.isEmpty()) {
            return 2;
        }
        Connection connect = null;
        PreparedStatement def_query = null;

        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
               // return 0; //TODO: added new error code return 1
                return 1;
            }
            def_query = connect.prepareStatement(SQL_QUERY_TOPIC_ID_BY_TOPIC_NAME);
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.setString(1, topic);

            //Since def_query is filled with setString we can execute it
            ResultSet data = def_query.executeQuery();
            //TODO: freshly added
            //topic_ID = data.getInt("ID");
            //TODO: commented out
            while (data.next()) {
                track++;

                topic_ID = data.getInt("ID");
            }
            /*if (track == 0) {
                //System.out.println("\nNo questions defined in that topic...\n");
                return 0;
            }*/
        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SQLException();
            //throw new DaoException(sql_ex);
            //System.err.println("\nError occurred inside DatabaseConnection class --> getConnectionToDb method\n");
            //return null;
        } finally {
            connect.close();
            def_query.close();
        }
        return topic_ID;
    }

    public int getExistingQuestionId(String question) throws SQLException {
        int track = 0;
        if (question.isEmpty()) {
            return 0;
        }
        int question_ID = 0;
        Connection connect = null;
        PreparedStatement def_query = null;

        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 0;
            }
            def_query = connect.prepareStatement(SQL_QUERY_QUESTION_ID_BY_QUESTION_NAME);
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.setString(1, question);


            //Since def_query is filled with setString we can execute it
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                track++;

                question_ID = data.getInt("ID");
            }
            if (track == 0) {
                //System.out.println("\nNo questions defined in that topic...\n");
                return 0;
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
        return question_ID;
    }
    public int getTopicIdFromQuestionTable(String question) throws SQLException {
        int track = 0;
        if (question.isEmpty()) {
            return 0;
        }
        int question_topic_ID = 0;
        Connection connect = null;
        PreparedStatement def_query = null;

        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 0;
            }
            def_query = connect.prepareStatement(SQL_QUERY_TOPIC_ID_FROM_QUESTION_TABLE);
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.setString(1, question);


            //Since def_query is filled with setString we can execute it
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                track++;

                question_topic_ID = data.getInt("topic_ID");
            }
            if (track == 0) {
                //System.out.println("\nNo questions defined in that topic...\n");
                return 0;
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
        return question_topic_ID;
    }
    public int getUnusedIDs(String table) throws SQLException {
        int track = 0;
        if (table.isEmpty()) {
            return 0;
        }
        int unused_ID = 0;
        Connection connect = null;
        PreparedStatement def_query = null;

        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                return 0;
            }

            def_query = connect.prepareStatement(SQL_QUERY_RETRIEVE_UNUSED_ID + table);

            ResultSet data = def_query.executeQuery();

            while (data.next()) {
                track++;

                unused_ID = data.getInt("MAX(ID)");
            }
            if (track == 0) {
                return 0;
            }
        } catch (SQLException sql_ex) {
            throw new SQLException();
        } finally {
            connect.close();
            def_query.close();
        }
        return unused_ID;
    }
}

