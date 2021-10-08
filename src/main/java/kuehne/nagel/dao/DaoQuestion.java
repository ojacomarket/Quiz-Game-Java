package kuehne.nagel.dao;

import kuehne.nagel.Answer;
import kuehne.nagel.Question;
import kuehne.nagel.exceptions.ApplicationPropertiesException;
import kuehne.nagel.exceptions.CannotReachDatabaseException;
import lombok.Data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static kuehne.nagel.SqlQueries.*;
import static kuehne.nagel.utils.DatabaseConnectivityProblems.printConnectivityErrorMessage;
import static kuehne.nagel.utils.ValidateInput.*;
import static kuehne.nagel.utils.DatabaseFailures.*;

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


    public int deleteQuestion(String topic, String question) {

        val
        //int topic_ID;
        int question_ID;
        int verify;

        /*SearchTopicIDbyTopicNameUtil search_topic_id = new SearchTopicIDbyTopicNameUtil();
        topic_ID = search_topic_id.searchTopicIDbyTopicName(topic);*/
        //SearchQuestionIDbyQuestionName search_question_id = new SearchQuestionIDbyQuestionName();
        question_ID = getExistingQuestionIdUtil(question);


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
        // Connection connect = null;
        //PreparedStatement def_query;
        // PreparedStatement def_query2;

        try (Connection connect = DatabaseConnection.getConnectionToDb(0);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_TO_DELETE_QUESTION);
        ) {
            //connect = DatabaseConnection.getConnectionToDb(0);
            /*if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 2;
            }*/
            // connect.setAutoCommit(false);
            //  def_query = connect.prepareStatement(SQL_QUERY_TO_DELETE_QUESTION);
            //plug topic into '?' of SQL_QUERY_SEARCH...

            def_query.setInt(1, question_ID);

            // System.out.println();
            //ResultSet data = def_query.executeUpdate();
            def_query.executeUpdate();

            /*def_query2 = connect.prepareStatement(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION);
            for (Answer answer:answers
                 ) {
                def_query2.setString(1,answer.getAnswer());
                def_query2.setInt(2, question_ID);
                //TODO: Add UPDATE CASCADE to ALL FOREIGN KEYS IN SQL
                def_query2.executeQuery();
            }*/
            //   connect.commit();
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return 1;
    }

    /*public int saveQuestion (String question, int difficulty, String topic, List<Answer> answers, int topic_ID) throws SQLException {

        //int topic_ID;
        int question_ID;
        int verify;


        SearchQuestionIDbyQuestionName search_question_id = new SearchQuestionIDbyQuestionName();
        question_ID = search_question_id.searchQuestionIdByQuestionName(question);
        System.out.println("QUESTION ID IS "+question_ID);

        if (question_ID == 0) {
            //if (question_ID == 0) {
               // GetUnusedId new_q_id = new GetUnusedId();
                question_ID = getUnusedIdUtil("question") + 1;
            } else {
            VerifyQuestionDuplicate verification = new VerifyQuestionDuplicate();
            verify = verification.verifyQuestionDuplicate(topic,question);
            if (verify == 3) {
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL\n");
                return 0;
            }
            //return 1;
        }

     //   System.err.println(question_ID);
     //   Question result_question;
        Connection connect = null;
        PreparedStatement def_query;
        PreparedStatement def_query2;

        try {
            connect = DatabaseConnection.getConnectionToDb(0);
            if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 2;
            }
            connect.setAutoCommit(false);
            def_query = connect.prepareStatement(SQL_QUERY_SAVE_QUESTION_TO_EXISTING_TOPIC);


            def_query.setString(1, question);

            def_query.setInt(2, difficulty);

            def_query.setInt(3, topic_ID);

            def_query.executeUpdate();


            connect.commit();
        } catch (Exception q) {
            connect.rollback();
            q.printStackTrace();
        }
        AddResponse response = new AddResponse();
        response.addResponse(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION,answers,question_ID);
        return 3;
    }*/
    public int updateQuestion(String topic, String old_question, String new_content, int difficulty, String ... new_answers){ //List<Answer> answers) {
        if (new_content.isEmpty()) {
            System.err.println("APPLICATION >>> Aborting to run (updateQuestion)...\n\t\t\t>>>" +
                    " PROBLEM ::: Cannot replace old question with empty string");
            return 0;
        }
        List<Answer> answers = prepareAnswers(new_answers);
        int validation = validateInputQuery(topic,old_question,difficulty,answers);
        if (validation < 1) {
            return validation;
        }
        int notVerified = verifyQuestionDuplicateUtil(topic, old_question);
        if (notVerified == 1) {
            System.err.println("APPLICATION >>> Aborting to run SQL UPDATE query...\n\t\t\t>>>" +
                    " PROBLEM 1 ::: Question, that doesn't exist, cannot be updated\n\t\t\t>>>" +
                    " PROBLEM 2 ::: Topic, that doesn't exist, cannot be queried");
            return notVerified;
        }
        int question_ID;
        question_ID = getExistingQuestionIdUtil(old_question);
        //List<Answer> answers = prepareAnswers(new_answers);
        try (Connection connect = DatabaseConnection.getConnectionToDb(0);
             PreparedStatement def_query = connect.prepareStatement(SQL_UPDATE_QUESTION_TO_EXISTING_TOPIC);
        ) {
            def_query.setString(1, new_content);
            def_query.setInt(2, difficulty);
            def_query.setInt(3, question_ID);
            def_query.executeUpdate();

        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        deleteAnswer(SQL_DELETE_ANSWERS_OF_EXISTING_QUESTION, question_ID);
        addAnswer(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION, answers, question_ID);
        return 3;
    }

    public int createTopic(String topic) throws SQLException {
        int topic_ID;
        int track = 0;

        Connection connect = null;
        PreparedStatement def_query;

        try {
            connect = DatabaseConnection.getConnectionToDb(0);
            if (connect == null) {
                return 2;
            }

            def_query = connect.prepareStatement(SQL_QUERY_CREATE_TOPIC);
            def_query.setString(1, topic);
            //plug topic into '?' of SQL_QUERY_SEARCH...
            def_query.executeUpdate();

        } catch (Exception q) {
            q.printStackTrace();
        }
        return 0;
    }

    private int getTopicIdByTopicNameUtil(String topic) {
        int topic_ID = 0;

        try (Connection connect = DatabaseConnection.getConnectionToDb(0);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_TOPIC_ID_BY_TOPIC_NAME)) {
            def_query.setString(1, topic);
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                topic_ID = data.getInt("ID");
            }
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return topic_ID;
    }

    public int getExistingQuestionIdUtil(String question) {
        int track = 0;
        if (question.isEmpty()) {
            return -10;
        }
        int question_ID = 0;
        //Connection connect = null;
        // PreparedStatement def_query = null;

        try (
                Connection connect = DatabaseConnection.getConnectionToDb(1);
                PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_QUESTION_ID_BY_QUESTION_NAME);
        ) {
           /* if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 0;
            }*/
            def_query.setString(1, question);
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                track++;

                question_ID = data.getInt("ID");
            }
            if (track == 0) {
                //System.out.println("\nNo questions defined in that topic...\n");
                return -10;
            }
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        //throw new DaoException(sql_ex);
        //System.err.println("\nError occurred inside DatabaseConnection class --> getConnectionToDb method\n");
        //return null;
        return question_ID;
    }

    public int getTopicIdFromQuestionTable(String question) {
        int track = 0;
        if (question.isEmpty()) {
            return 0;
        }
        int question_topic_ID = 0;
        //  Connection connect = null;
        // PreparedStatement def_query = null;

        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_TOPIC_ID_FROM_QUESTION_TABLE);
        ) {
            def_query.setString(1, question);
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                track++;

                question_topic_ID = data.getInt("topic_ID");
            }
            /*if (track == 0) {
                //System.out.println("\nNo questions defined in that topic...\n");
                return 10;
            }*/
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return question_topic_ID;
    }

    /* public int getUnusedID(String table) throws SQLException {

         if (table.isEmpty()) {
             return 0;
         }
         int track = 0;
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
            /* if (track == 0) {
                 return 0;
             }*/
  /*      } catch (SQLException sql_ex) {
            throw new SQLException();
        } finally {
            connect.close();
            def_query.close();
        }
        return unused_ID;
    }
*/
    //TODO: FRESHLY ADDED
    public List<Question> showAllQuestionsByTopic(String topic) {
        if (topic.isEmpty()) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " ERROR ::: Empty string cannot be a topic");
            return new LinkedList<>();
        }
        System.out.println("APPLICATION >>> Data is valid!");
        List<Question> queryResult;
        queryResult = showAllQuestionsByTopicUtil(topic.toLowerCase());
        if (queryResult.isEmpty()) {
            return queryResult;
        }
        int counter = 0;
        System.out.println("\nAPPLICATION >>> Received data:\n");
        for (Question question : queryResult) {
            counter++;
            System.out.println("\t\t\t(" + counter + ") Record:");
            System.out.printf("\t\t\tTopic name ::: %s\n\t\t\tQuestion ::: %s\n\t\t\tDifficulty (1-5) ::: %d\n\n", question.getTopic_name(),
                    question.getContent(), question.getDifficulty());
        }/*catch () {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " ERROR ::: Relevant database OR table in a database doesn't exist");
            return new LinkedList<>();
        }*/
        return queryResult;
    }

    private List<Question> showAllQuestionsByTopicUtil(String topic) {
        int track = 0;
        List<Question> data = new LinkedList<>();
        try (
                Connection connect = DatabaseConnection.getConnectionToDb(0);
                PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_SEARCH_QUESTION_BY_TOPIC)
        ) {
            def_query.setString(1, topic);
            System.out.println(def_query);
            ResultSet answer_from_db = def_query.executeQuery();
            System.out.println(answer_from_db);
            while (answer_from_db.next()) {

                track++;
                Question output = new Question(
                        answer_from_db.getString("name"),
                        answer_from_db.getString("content"),
                        answer_from_db.getInt("difficulty")
                );
                data.add(output);
            }
            if (track == 0) {
                System.err.println("APPLICATION >>> Aborting to execute SQL query...\n\t\t\t>>>" +
                        " ERROR ::: Topic (" + topic + ") doesn't have any questions in a database");
                return new LinkedList<>();
            }
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return new LinkedList<>();
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return new LinkedList<>();
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return new LinkedList<>();
        }
        /*catch (NullPointerException nullex) {
            DatabaseConnectivityProblems.printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return new LinkedList<>();
        }*/
        return data;
    }

    private List<Answer> prepareAnswers(String... answers) {
        List<Answer> storedAnswers = new LinkedList<>();
        for (String answer : answers) {
            if (answer.isEmpty()) {
                return null;
            }
            answer = answer.toLowerCase();
            storedAnswers.add(new Answer(answer));
        }
        return storedAnswers;
    }

    public int saveQuestion(String topic, String question, int difficulty, String... answers) {
        int validation;
        List<Answer> ready_answer;
        ready_answer = prepareAnswers(answers);
        validation = validateInputQuery(topic, question, difficulty, ready_answer);

        if (validation < 1) {
            return validation;
        }
        topic = topic.toLowerCase();
        question = question.toLowerCase();
        int topic_ID;
        int queryResult;
        System.out.println("APPLICATION >>> Data is valid!\n\t\t\t>>> Trying to connect to the database...");

        // try {
        topic_ID = getTopicIdByTopicNameUtil(topic);//searchTopicIDbyTopicNameUtil(topic);
        //validation //= //validateSearchTopicByTopicName(topic_ID);
        if (topic_ID < 1) {
            return topic_ID;
        }
        queryResult = saveQuestionUtil(question, difficulty, topic, ready_answer, topic_ID);
        if (queryResult == 0) {
            System.err.println("APPLICATION >>> Aborting to insert a record...\n\t\t\t>>>" +
                    " PROBLEM ::: Question ("+question+") already exists in ("+topic+") topic");
            return -7;
        }

        // } catch (SQLException exep) {
        //     System.err.println("ERROR! Undefined behaviour of the program... Forcing to shut down!!!");
        //    return -8;
        //  }
        return 1;
    }

    private int saveQuestionUtil(String question, int difficulty, String topic, List<Answer> answers, int topic_ID) {
        int question_ID;
        int verify;
        question_ID = getExistingQuestionIdUtil(question);
        if (question_ID == -10) {
            question_ID = assignUnusedIdUtil("question") + 1;
        } else {
           // VerifyQuestionDuplicate verification = new VerifyQuestionDuplicate();
            verify = verifyQuestionDuplicateUtil(topic, question);
            if (verify == 0) {
                return 0;
            }
        }
        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_SAVE_QUESTION_TO_EXISTING_TOPIC);
        ) {
            def_query.setString(1, question);
            def_query.setInt(2, difficulty);
            def_query.setInt(3, topic_ID);
            def_query.executeUpdate();
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return addAnswer(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION, answers, question_ID);
    }
  /*  private int searchTopicIDbyTopicNameUtil(String topic) {

        int queryResult;
        try {
            queryResult = getTopicIdByTopicNameUtil(topic);
            if (queryResult == -1) {
                return -1;
            } else if (queryResult == -2) {
                return -2;
            }
        } catch (NullPointerException sqlex) {
            return -1;
        }
        return queryResult;
    }*/

    /*private int searchQuestionIdByQuestionName(String question) {
        int queryResult;
        //DaoQuestion daoq = new DaoQuestion();
        try {
            queryResult = getExistingQuestionIdUtil(question);
            if (queryResult == 0) {
                //System.out.println("\nNo such a question...\n");
                return queryResult;
            }
        }
        return queryResult;
    }*/

    public int addAnswer(String query, List<Answer> answers, int question_ID) {
        // Connection connect = null;
        //  PreparedStatement def_query;
        // PreparedStatement def_query2;

        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query2 = connect.prepareStatement(query);
        ) {
            //connect = DatabaseConnection.getConnectionToDb(1);
           /* if (connect == null) {
                //System.err.println("\nError occurred inside Config class --> return_property method\n");
                return 2;
            }*/
            //connect.setAutoCommit(false);
            // def_query2 = connect.prepareStatement(query);
            for (Answer answer : answers
            ) {
                def_query2.setString(1, answer.getAnswer());
                def_query2.setInt(2, question_ID);
                //TODO: Add UPDATE CASCADE to ALL FOREIGN KEYS IN SQL
                def_query2.executeQuery();
            }
            // connect.commit();
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return 1;
    }

    public int assignUnusedIdUtil(String table) {
        int unused_ID = 0;
        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_RETRIEVE_UNUSED_ID + table);) {

            ResultSet data = def_query.executeQuery();

            while (data.next()) {
                unused_ID = data.getInt("MAX(ID)");
            }
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return unused_ID;
    }

    private int getUnusedIdUtil11111(String table) {
        int queryResult;
        queryResult = assignUnusedIdUtil(table);
        if (queryResult == 0) {
            System.out.println("\nDatabase connection problems... Check property file!\n");
            return queryResult;
        }
        return queryResult;
    }

    private int verifyQuestionDuplicateUtil(String topic, String question) {
        int q_t_id;
        int t_id;
        q_t_id = getTopicIdFromQuestionTable(question);
        t_id = getTopicIdByTopicNameUtil(topic);
        System.out.println(q_t_id +"    " + t_id);
        if (q_t_id == t_id) {
            if (q_t_id == 0) {
                return 1;
            }
            return 0;
        }
            /*if (queryResult == 0) {
                //System.out.println("\nNo such a question...\n");
                return queryResult;
            }*/
        return 1;
    }

    /* private int searchQuestionIdByQuestionName (String question) {
         int queryResult;
         DaoQuestion daoq = new DaoQuestion();
         try {
             queryResult = daoq.getExistingQuestionIdUtil(question);
             if (queryResult == 0) {
                 //System.out.println("\nNo such a question...\n");
                 return queryResult;
             }
         } catch (
                 SQLException exep) {
             return 0;
         }
         return queryResult;
     }*/
    private int deleteAnswer(String query, int question_ID) {
        // Connection connect = null;
        // PreparedStatement def_query;
        //PreparedStatement def_query2;
        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query = connect.prepareStatement(query);) {
            //connect = DatabaseConnection.getConnectionToDb(1);
          /* if (connect == null) {
               //System.err.println("\nError occurred inside Config class --> return_property method\n");
               return 2;
           }*/
            //  connect.setAutoCommit(false);
            // def_query = connect.prepareStatement(query);
            def_query.setInt(1, question_ID);
            def_query.executeUpdate();
            connect.commit();
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -2;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return 1;
    }
}

