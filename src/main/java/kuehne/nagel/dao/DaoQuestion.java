package kuehne.nagel.dao;

import kuehne.nagel.Question;
import kuehne.nagel.Response;
import kuehne.nagel.utils.*;
import lombok.Data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static kuehne.nagel.SqlQueries.*;

@Data
public class DaoQuestion extends DatabaseConnection implements DaoQuestionI {

  /*  private static final String SQL_QUERY_SEARCH_QUESTION_BY_TOPIC =
            "SELECT name,content,difficulty FROM topic INNER JOIN question ON topic.ID = question.topic_ID WHERE name LIKE ?";
    private static final String SQL_QUERY_SAVE_QUESTION_TO_EXISTING_TOPIC = "INSERT INTO question (content, difficulty, topic_ID) VALUES (?,?,?)";
    private static final String SQL_QUERY_TOPIC_ID_BY_TOPIC_NAME = "SELECT ID FROM topic WHERE name=?";
    private static final String SQL_QUERY_QUESTION_ID_BY_QUESTION_NAME = "SELECT ID FROM question WHERE content=?";
    private static final String SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION = "INSERT INTO response (answer, question_ID) VALUES (?,?)";
    private static final String SQL_QUERY_RETRIEVE_UNUSED_ID = "SELECT MAX(ID) FROM ";
    private static final String SQL_QUERY_CREATE_TOPIC = "INSERT INTO topic (name) VALUES (?)";
    private static final String SQL_QUERY_TOPIC_ID_FROM_QUESTION_TABLE = "SELECT topic_ID FROM question WHERE content=?";
    private static final String SQL_UPDATE_QUESTION_TO_EXISTING_TOPIC = "UPDATE question SET content=?, difficulty=? WHERE ID=?";
    private static final String SQL_DELETE_ANSWERS_OF_EXISTING_QUESTION = "DELETE FROM response WHERE question_ID=?";
    //private static final String SQL_TRIGGER_TO_ADD_QUESTION_AND_ANSWER = "DELIMITER ^_^ CREATE TRIGGER trig BEFORE INSERT ON response FOR EACH ROW BEGIN INSERT INTO question(content, difficulty, topic_ID) values (?,?,?); END^_^";
    private static final String SQL_QUERY_TO_DELETE_QUESTION = "DELETE FROM question WHERE ID=?";*/

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
    public int deleteQuestion (String topic, String question) throws SQLException {

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
            System.out.println("No such a question...\n");
        }
        //}
       /* else if (question_ID > 0) {
            return 0;
        }*/
        //   System.err.println(question_ID);
        //   Question result_question;
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
            def_query = connect.prepareStatement(SQL_QUERY_TO_DELETE_QUESTION);
            //plug topic into '?' of SQL_QUERY_SEARCH...

            def_query.setInt(1, question_ID);

            // System.out.println();
            //ResultSet data = def_query.executeUpdate();
            def_query.executeUpdate();

            /*def_query2 = connect.prepareStatement(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION);
            for (Response answer:answers
                 ) {
                def_query2.setString(1,answer.getAnswer());
                def_query2.setInt(2, question_ID);
                //TODO: Add UPDATE CASCADE to ALL FOREIGN KEYS IN SQL
                def_query2.executeQuery();
            }*/
            connect.commit();
        } catch (Exception q) {
            connect.rollback();
            q.printStackTrace();
        }

        return 3;
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
        PreparedStatement def_query2;

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
           // System.out.println();
            //ResultSet data = def_query.executeUpdate();
            def_query.executeUpdate();

            /*def_query2 = connect.prepareStatement(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION);
            for (Response answer:answers
                 ) {
                def_query2.setString(1,answer.getAnswer());
                def_query2.setInt(2, question_ID);
                //TODO: Add UPDATE CASCADE to ALL FOREIGN KEYS IN SQL
                def_query2.executeQuery();
            }*/
            connect.commit();
        } catch (Exception q) {
            connect.rollback();
            q.printStackTrace();
        }
        AddResponse response = new AddResponse();
        response.addResponse(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION,answers,question_ID);
        return 3;
    }
    public int updateQuestion (String topic, int topic_ID, String old_question, String new_content, int difficulty,  List<Response> answers) throws SQLException {

        //int topic_ID;
       int question_ID;
        int verify;

        /*SearchTopicIDbyTopicName search_topic_id = new SearchTopicIDbyTopicName();
        topic_ID = search_topic_id.searchTopicIDbyTopicName(topic);*/
        /*SearchQuestionIDbyQuestionName search_question_id = new SearchQuestionIDbyQuestionName();
        question_ID = search_question_id.searchQuestionIdByQuestionName(old_question);*/
        VerifyQuestionDuplicate verification = new VerifyQuestionDuplicate();
        verify = verification.verifyQuestionDuplicate(topic,old_question);
        if (verify == 5) {
            System.err.println("Question, that doesn't exist, cannot be updated...");
            return 0;
        }
        SearchQuestionIDbyQuestionName search_question_id = new SearchQuestionIDbyQuestionName();
        question_ID = search_question_id.searchQuestionIdByQuestionName(old_question);
       // System.out.println("QUESTION ID IS "+question_ID);

       /* if question_ID == 0) {
            {
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
        }*/
        //}

        Connection connect = null;
        PreparedStatement def_query;
        PreparedStatement def_query2;
        PreparedStatement def_query3;

        try {
            connect = DatabaseConnection.getConnectionToDb();
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 2;
            }
            connect.setAutoCommit(false);
            def_query = connect.prepareStatement(SQL_UPDATE_QUESTION_TO_EXISTING_TOPIC);
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.setString(1, new_content);
            def_query.setInt(2, difficulty);
            def_query.setInt(3, question_ID);
            //ResultSet data = def_query.executeUpdate();
            def_query.executeUpdate();

            //Just deletion

           /* def_query2 = connect.prepareStatement(SQL_DELETE_ANSWERS_OF_EXISTING_QUESTION);
            def_query2.setInt(1, question_ID);
            def_query2.executeUpdate();*/

            /*def_query3 = connect.prepareStatement(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION);
            for (Response answer:answers
            ) {
                def_query3.setString(1,answer.getAnswer());
                def_query3.setInt(2, question_ID);
                //TODO: Add UPDATE CASCADE to ALL FOREIGN KEYS IN SQL
                def_query3.executeQuery();
            }*/
            connect.commit();
        } catch (Exception q) {
            connect.rollback();
            q.printStackTrace();
        }
        DeleteResponse del = new DeleteResponse();
        del.deleteResponse(SQL_DELETE_ANSWERS_OF_EXISTING_QUESTION, question_ID);
        AddResponse ad = new AddResponse();
        ad.addResponse(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION,answers,question_ID);
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

