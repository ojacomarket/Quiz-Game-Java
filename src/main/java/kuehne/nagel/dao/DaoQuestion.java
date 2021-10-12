package kuehne.nagel.dao;

import kuehne.nagel.*;
import kuehne.nagel.exceptions.ApplicationPropertiesException;
import kuehne.nagel.exceptions.CannotReachDatabaseException;
import kuehne.nagel.exceptions.InternalDatabaseException;
import lombok.Data;

import java.sql.*;
import java.util.*;

import static kuehne.nagel.SQLQueryType.SAVE_QUERY;
import static kuehne.nagel.SQLQueryType.SEARCH_QUERY;
import static kuehne.nagel.SqlQueries.*;
import static kuehne.nagel.utils.ValidateInput.*;

@Data
public class DaoQuestion implements DaoQuestionI {

  /*  @Override
    public int deleteQuestion(String topic, String question) {
        int question_ID;
        question_ID = getExistingQuestionIdUtil(question);
        if (question_ID == 0) {
            System.out.println("No such a question...\n");
        }
        try (Connection connect = DatabaseConnection.getConnectionToDb(0);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_TO_DELETE_QUESTION);
        ) {
            def_query.setInt(1, question_ID);
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
        return 1;
    }*/

   /* @Override
    public int updateQuestion(String topic, String old_question, String new_content, int difficulty, String... new_answers) {
        if (new_content.isEmpty()) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM ::: Cannot replace old question with empty string");
            return -3;
        }
        List<Response> answers = prepareAnswers(new_answers);
        int validation = validateJDBCSaveQueryBeforeDbConnection(topic, old_question, difficulty, answers);
        if (validation < 1) {
            return validation;
        }
        int duplicates = verifyQuestionDuplicateUtil(topic.toLowerCase(), old_question.toLowerCase());
        if (duplicates == -4) {
            // printConnectivityErrorMessage(NO_TOPIC_OR_QUESTION);
            return duplicates;
        }
        int question_ID;

        question_ID = getExistingQuestionIdUtil(old_question.toLowerCase());
        if (question_ID < 0) {
            return question_ID;
        }
        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
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
        int delete = deleteAnswer(question_ID);
        if (delete < 1) {
            return delete;
        }
        int add = addResponse(answers, question_ID);
        if (add < 1) {
            return add;
        }
        return 1;
    }*/


    /*private int getTopicIdFromQuestionTable(String question) {
        int question_topic_ID = 0;

        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_TOPIC_ID_FROM_QUESTION_TABLE);
        ) {
            def_query.setString(1, question);
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                question_topic_ID = data.getInt("topic_ID");
            }
            if (question_topic_ID == 0) {
                printConnectivityErrorMessage(NO_TOPIC_OR_QUESTION);
                return -4;
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
        return question_topic_ID;
    }*/


    @Override
    public int searchAllQuestionsByTopic(String topic) {
        Collection<DbRecord> queryResult;
        Collection<Question> printData = new LinkedHashSet<>();
        int counter = 0;
        try {
            queryResult = searchAllQuestionsByTopicUtil(topic.toLowerCase(), SEARCH_QUERY);
            for (DbRecord rec : queryResult
            ) {
                printData.add(new Question(
                        rec.getTopic().getName(),
                        rec.getQuestion().getContent(),
                        rec.getQuestion().getDifficulty())
                );
            }
            if (queryResult.isEmpty()) {
                return 0;
            }
        } catch (InternalDatabaseException idex) {
            idex.cannotProcessSqlQuery();
            return -1;
        }
        for (Question question : printData) {
            counter++;
            System.out.println("(" + counter + ") Record:");
            System.out.printf("\tTopic name ::: %s\n\tQuestion ::: %s\n\tDifficulty (1-5) ::: %d\n\n", topic,
                    question.getContent(), question.getDifficulty());
        }
        return 1;
    }


    private Collection<DbRecord> searchAllQuestionsByTopicUtil(String topic, SQLQueryType searchType) throws InternalDatabaseException {

        int userInputValidation = validateJDBCSearchQueryBeforeDbConnection(topic);
        if (userInputValidation < 1) {
            return new LinkedHashSet<>();
        }

        Collection<DbRecord> queryResult = new LinkedHashSet<>();
        try (Connection connect = DatabaseConnection.getConnectionToDb()) {
            if (searchType.equals(SEARCH_QUERY)) {
                queryResult = findOnlyQuestions(topic, connect);
            } else if (searchType.equals(SAVE_QUERY)) {
                queryResult = findAllData(topic, connect);
            }

            if (queryResult.isEmpty()) {
                return queryResult;
            }

        } catch (ApplicationPropertiesException apex) {
            apex.printApplicationPropertiesError();
            return new LinkedHashSet<>();
        } catch (CannotReachDatabaseException crdex) {
            crdex.cannotReachDatabaseError();
            return new LinkedHashSet<>();
        } catch (SQLException sqlex) {
            throw new InternalDatabaseException();
        }
        return queryResult;
    }


    public int saveQuestion(String topic, String questionContent, int difficulty, String... responses) {
        Collection<String> tranformedAnswers = new LinkedHashSet<>(Arrays.asList(responses));
        tranformedAnswers.forEach(String::toLowerCase);
        int validateInput = validateJDBCSaveQueryBeforeDbConnection(
                topic,
                questionContent,
                difficulty,
                tranformedAnswers
        );

        if (validateInput < 1) {
            return validateInput;
        }
        return saveQuestionUtil(topic.toLowerCase(), questionContent.toLowerCase(), difficulty, tranformedAnswers, SAVE_QUERY);
    }


    private int saveQuestionUtil(String topic, String question, int difficulty, Collection<String> answers, SQLQueryType searchType) {
        Collection<DbRecord> result_data;
        try (Connection connect = DatabaseConnection.getConnectionToDb()) {
            result_data = searchAllQuestionsByTopicUtil(topic, SAVE_QUERY);
            if (result_data.isEmpty()) {
                lazyAdd(topic, question, difficulty, answers, connect);
                //return lazyAdd(topic, question, difficulty, answers, connect);
            } else {

                addQuestionRecord(result_data, connect, topic, question, difficulty);


                result_data = searchAllQuestionsByTopicUtil(topic, SAVE_QUERY);
                for (DbRecord d : result_data
                ) {
                    System.out.println(d.getQuestion().getContent());
                }
                System.out.println(question);
                for (DbRecord response : result_data
                ) {
                    if (response
                            .getQuestion()
                            .getContent()
                            .equals(question)) {
                        System.out.println("Hello!");
                        addResponseRecordById(response.getQuestion().getId(), connect, answers);
                    }
                }
            }
        } catch (ApplicationPropertiesException apex) {
            apex.printApplicationPropertiesError();
            return -1;
        } catch (CannotReachDatabaseException crdex) {
            crdex.cannotReachDatabaseError();
            return -1;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return -1;
        } catch (InternalDatabaseException idex) {
            idex.cannotProcessSqlQuery();
            return -1;
        }
        return 1;
    }


    /**
     * Execute UPDATE QUERY on database
     *
     * @param existingQuestion
     * @param newQuestion
     * @param topic
     * @param difficulty
     * @param answers
     * @return if query has been successfully executed, return 1, otherwise 0
     */
    public int updateQuestion(String existingQuestion, String newQuestion, String topic, int difficulty, String... answers) {
        Collection<String> fromArrayToSet = new LinkedHashSet<>(Arrays.asList(answers));
        int validationResult = validateJDBCSaveQueryBeforeDbConnection(
                topic,
                existingQuestion,
                difficulty,
                fromArrayToSet
        );
        if (newQuestion.isEmpty()) {
            return 0;
        }
        if (validationResult < 1) {
            return 0;
        }
        return updateQuestionUtil(existingQuestion, newQuestion, topic, difficulty, fromArrayToSet, SQLQueryType.UPDATE_QUERY);
    }


    private int updateQuestionUtil(String existingQuestion, String newQuestion, String topic, int difficulty, Collection<String> answers, SQLQueryType searchType) {

        //Set all to lower case
        existingQuestion = existingQuestion.toLowerCase();
        newQuestion = newQuestion.toLowerCase();
        topic = topic.toLowerCase();

        Collection<DbRecord> queryResult;
        try (Connection connect = DatabaseConnection.getConnectionToDb();
             PreparedStatement query = connect.prepareStatement(SQL_UPDATE_QUESTION_TABLE)) {
            //SAVE_QUERY is chosen, because the output of it is large enough to start processing data
            queryResult = searchAllQuestionsByTopicUtil(topic, SAVE_QUERY);
            if (queryResult.isEmpty()) {
                return 0;
            }
            //only 1 iteration will execute
            for (DbRecord d : queryResult
            ) {
                query.setString(1, newQuestion);
                query.setInt(2, difficulty);
                query.setInt(3, d.getTopic().getId());
                query.setString(4, existingQuestion);
                //before 'break' execute SQL command
                query.addBatch();
                query.executeUpdate();
                break;
            }
            //Updated data has to be queried again, in order to bound answers
            queryResult = searchAllQuestionsByTopicUtil(topic, SAVE_QUERY);
            for (DbRecord response : queryResult
            ) {
                if (response
                        .getQuestion()
                        .getContent()
                        .equals(newQuestion)
                ) {
                    deleteResponseRecordById(response
                            .getQuestion()
                            .getId(),
                            connect

                    );
                    addResponseRecordById(response.getQuestion().getId(), connect,answers);
                    }

                }

        } catch (ApplicationPropertiesException apex) {
            apex.printApplicationPropertiesError();
            apex.printStackTrace();
            return -1;
        } catch (CannotReachDatabaseException crdex) {
            crdex.cannotReachDatabaseError();
            crdex.printStackTrace();
            return -1;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return -1;
        } catch (InternalDatabaseException idex) {
            idex.cannotProcessSqlQuery();
            idex.printStackTrace();
            return -1;
        }
        return 1;
    }


    private int updateResponse(int question_ID, Connection connect, Collection<String> answer) throws SQLException {

        try (
                PreparedStatement def_query = connect.prepareStatement(SQL_UPDATE_RESPONSE_TABLE)
        ) {
            for (String str : answer) {
                def_query.setString(1, str);
                def_query.setInt(2, question_ID);
                //allows us to execute SQL at place and not at the end of connection!
                def_query.addBatch();
                def_query.executeUpdate();
            }

        } catch (SQLException sq) {
            throw new SQLException();
        }
        return 1;
    }

    private int addQuestionRecord(Collection<DbRecord> result_data, Connection connect, String topic, String question, int difficulty) throws SQLException {
        try (
                PreparedStatement query = connect.prepareStatement(SQL_QUERY_SAVE_QUESTION_TO_EXISTING_TOPIC)
        ) {
            for (DbRecord dbr : result_data) {
                query.setString(1, question);
                query.setInt(2, difficulty);
                query.setInt(3, dbr.getTopic().getId());
                query.addBatch();
                //trick to get only first value and not proceed forward
                break;
            }
            query.executeUpdate();
        }
        return 1;
    }

    private int deleteResponseRecordById(int recordId, Connection connect) throws SQLException {
        try (
                PreparedStatement query = connect.prepareStatement(SQL_DELETE_RESPONSE_RECORD)
        ) {
            query.setInt(1,recordId);
            query.addBatch();
            query.executeUpdate();

        } catch (SQLException sqlex) {
            throw new SQLException();
        }
        return 1;
    }

    private int addResponseRecordById(int question_ID, Connection connect, Collection<String> answer) throws SQLException {
        try (
                PreparedStatement def_query = connect.prepareStatement(SQL_ADD_RESPONSE_RECORD)
        ) {
            for (String str : answer) {
                System.out.println(str);
                def_query.setString(1, str);
                def_query.setInt(2, question_ID);
                //allows us to execute SQL at place and not at the end of connection!
                def_query.addBatch();
                def_query.executeUpdate();
            }
        } catch (SQLException sq) {
            throw new SQLException();
        }
        return 1;
    }

    public int deleteQuestion (String question) {
        int validation = validateJDBCSearchQueryBeforeDbConnection(question);
        if (validation < 1) {
            return -1;
        } else
            return deleteQuestionUtil(question);
    }
    private int deleteQuestionUtil (String question) {
        try (Connection connect = DatabaseConnection.getConnectionToDb();
        PreparedStatement def_query = connect.prepareStatement(SQL_DELETE_RECORD);
        ) {
            def_query.setString(1, question);
            def_query.executeUpdate();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return -1;
        } catch (ApplicationPropertiesException apex) {
            apex.printApplicationPropertiesError();
            apex.printStackTrace();
            return -1;
        } catch (CannotReachDatabaseException crdex) {
            crdex.cannotReachDatabaseError();
            crdex.printStackTrace();
            return -1;
        }
        return 1;
    }
    /*private Collection<String> prepareAnswers (String... answers) {
        Collection<String> storedAnswers = new LinkedHashSet<>();
        for (String answer : answers) {
            if (answer.isEmpty()) {
                return new LinkedList<>();
            }
            answer = answer.toLowerCase();
            storedAnswers.add(answer);
        }
        return storedAnswers;
    }*/

    // @Override
    // public int saveQuestion(String topic, String question, int difficulty, String... answers) {

       /* int validation;
        List<Response> ready_answer;
        ready_answer = prepareAnswers(answers);
        validation = validateJDBCSaveQueryBeforeDbConnection(topic, question, difficulty, ready_answer);
        if (validation == -3) {
            return validation;
        }
        topic = topic.toLowerCase();
        question = question.toLowerCase();*/
        /*int topic_ID;
        int queryResult;
        System.out.println("APPLICATION >>> Data is valid!\n\t\t\t>>> Trying to connect to the database...");
        topic_ID = getTopicIdByTopicNameUtil(topic, question, difficulty, answers);
        if (topic_ID == 0) {
            return 0;
        }*/
      /*  int queryResult;
        queryResult = saveQuestionUtil(question.toLowerCase(), difficulty, topic.toLowerCase(), answers);
        return queryResult;
    }*/

    //private int getTopicIdByTopicNameUtil(String topic) {
       /* int validation;

        validation = validateJDBCSaveQueryBeforeDbConnection(topic, question, difficulty, answers);
        if (validation == 0) {
            return 0;
        }*/

    // topic = topic.toLowerCase();
    //question = question.toLowerCase();
       /* int topic_ID = 0;
        try (Connection connect = DatabaseConnection.getConnectionToDb(0);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_TOPIC_ID_BY_TOPIC_NAME)) {
            def_query.setString(1, topic.toLowerCase());
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                topic_ID = data.getInt("ID");
            }
            if (topic_ID == 0) {
                printConnectivityErrorMessage(NO_TOPIC_OR_QUESTION);
                return 0;
            }
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return 0;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return 0;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return 0;
        }
        return topic_ID;
    }

    private int saveQuestionUtil(String question, int difficulty, String topic, String... answers) {
        int topicTable = pingDatabaseTable("topic");
        if (topicTable < 1) {
            return topicTable;
        }
        int questionTable = pingDatabaseTable("question");
        if (questionTable < 0) {
            return questionTable;
        }
        int responseTable = pingDatabaseTable("response");
        if (responseTable < 0) {
            return responseTable;
        }

        int topic_ID;
        List<Response> ready_answer;
        ready_answer = prepareAnswers(answers);
        int validation;
        validation = validateJDBCSaveQueryBeforeDbConnection(topic, question, difficulty, ready_answer);
        if (validation == 0) {
            return 0;
        }
        System.out.println("\t\t\t>>> Data is valid!");




        List<Question> list = searchAllQuestionsByTopic(topic);
        for (Question q:list
             ) {
            System.out.println(q.getContent());
        }
        if (list.isEmpty()) {
            return -1;
        }
        if(list.stream().anyMatch(q -> q.getContent().equals(question))) {
            System.err.println("Question already exists in a db!\n");
            return -1;
        }
         topic_ID = getTopicIdByTopicNameUtil(topic);
        if (topic_ID == 0) {
            return 0;
        }
        int question_ID;
        question_ID = getExistingQuestionIdUtil(question);

        if (question_ID == 0) {
            question_ID = assignUnusedIdUtil("question");
        } else if (question_ID == -1) {
            return -1;
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
            return -1;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return -1;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return addResponse(ready_answer, question_ID);
   }


    private int getExistingQuestionIdUtil(String question) {
        int question_ID = 0;
        try (
                Connection connect = DatabaseConnection.getConnectionToDb(1);
                PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_QUESTION_ID_BY_QUESTION_NAME);
        ) {
            def_query.setString(1, question);
            ResultSet data = def_query.executeQuery();
            while (data.next()) {
                question_ID = data.getInt("ID");
            }
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -1;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return -1;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        return question_ID;
    }

    private int addResponse(List<Response> answers, int question_ID) {
        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query2 = connect.prepareStatement(SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION);
        ) {
            for (Response answer : answers
            ) {
                def_query2.setString(1, answer.getAnswer());
                def_query2.setInt(2, question_ID);
                System.out.println(def_query2);
                def_query2.executeUpdate();
            }
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -1;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return -1;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        System.out.println("APPLICATION >>> SQL SAVE QUERY has been successfully executed....");
        return 1;
    }

    public List<String> getResponse(int question_ID) {
        List<String> answers = new LinkedList<>();
        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query2 = connect.prepareStatement(SQL_QUERY_TO_GET_RESPONSE);
        ) {
            def_query2.setInt(1, question_ID);
            ResultSet got_answers = def_query2.executeQuery();

            while (got_answers.next()) {

                answers.add(got_answers.getString("answer"));
            }

            if (answers.isEmpty()) {
                return null;
            }
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return new LinkedList<>();
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return new LinkedList<>();
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return new LinkedList<>();
        }
        return answers;
    }

    public int addResponseOrAbort(int question_ID, String question) {
        List<String> answers = getResponse(question_ID);
        if (answers.isEmpty()) {
            return 0;
        } else if (answers.stream().anyMatch(answerName -> answerName.equals(question))) {
            return 1;
        } else if (answers == null) {

        }
        return -1;
    }

    private int assignUnusedIdUtil(String table) {
        int unused_ID = 0;
        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_RETRIEVE_UNUSED_ID)) {
            def_query.setString(1, table);
            ResultSet data = def_query.executeQuery();

            while (data.next()) {
                unused_ID = data.getInt("AUTO_INCREMENT");
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

    private int verifyQuestionDuplicateUtil(String topic, String question) {
        int q_t_id;
        int t_id;

        t_id = getTopicIdByTopicNameUtil(topic);
        if (t_id < 1) {
            return t_id;
        }
        q_t_id = getTopicIdFromQuestionTable(question);
        if (q_t_id < 1) {
            return q_t_id;
        } else if (q_t_id == t_id) {
            return 2;
        } else
            return -4;
    }

    private int deleteAnswer(int question_ID) {
        try (Connection connect = DatabaseConnection.getConnectionToDb(1);
             PreparedStatement def_query = connect.prepareStatement(SQL_DELETE_ANSWERS_OF_EXISTING_QUESTION)) {
            def_query.setInt(1, question_ID);
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
        return 1;
    }

    public int pingDatabaseTable(String table) {
        System.out.println("APPLICATION >>> Establishing handshake with a database...");
        List<Integer> identifications = new LinkedList<>();
        try (
                Connection connect = DatabaseConnection.getConnectionToDb(0);
                PreparedStatement def_query = connect.prepareStatement(SQL_QUERY_TO_PING_TABLE + table.toLowerCase())
        ) {
            System.out.println("\t\t\t>>> Pinging (" + table.toLowerCase() + ") table...");
            ResultSet answer_from_db = def_query.executeQuery();
            while (answer_from_db.next()) {
                identifications.add(answer_from_db.getInt("id"));
            }
            if (identifications.isEmpty()) {
                if (table.equals("topic"))
                    printConnectivityErrorMessage(NO_TOPIC_OR_QUESTION);
                return 0;
            }
        } catch (SQLException sqlex) {
            printConnectivityErrorMessage(INVALID_SQL_QUERY);
            return -1;
        } catch (ApplicationPropertiesException apex) {
            printConnectivityErrorMessage(TYPO_APPLICATION_PROPERTIES_FILE_NAME);
            return -1;
        } catch (CannotReachDatabaseException crdex) {
            printConnectivityErrorMessage(APPLICATION_PROPERTIES_FILE_FAILURE);
            return -1;
        }
        System.out.println("\t\t\t>>> Table (" + table + ") exists in a database!");
        return 1;
    }*/
    private Collection<DbRecord> findAllData(String topic, Connection connect) throws SQLException, CannotReachDatabaseException, ApplicationPropertiesException {
        //topic = topic.toLowerCase();
        Collection<DbRecord> fullListOfQuestions = new LinkedHashSet<>();
        try (PreparedStatement query = connect.prepareStatement(SQL_INNER_JOIN_FOR_SAVE)) {
            query.setString(1, topic);
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Topic topicAsObject = new Topic(
                        queryResult.getInt("TOPIC_ID"),
                        queryResult.getString("name")
                );
                Question questionAsObject = new Question(
                        queryResult.getInt("Q_ID"),
                        queryResult.getString("content"),
                        queryResult.getInt("difficulty"),
                        queryResult.getInt("TOPIC_ID")
                );
                DbRecord record = new DbRecord(topicAsObject, questionAsObject);
                fullListOfQuestions.add(record);
            }
        }
        return fullListOfQuestions;
    }

    /**
     * Will retrieve overview about questions, that are added inside provided topic
     * @param topic
     * @param connect
     * @return filled with data Set, if everything is good, otherwise - empty Set
     * @throws SQLException
     * @throws CannotReachDatabaseException
     * @throws ApplicationPropertiesException
     */
    private Collection<DbRecord> findOnlyQuestions(String topic, Connection connect) throws SQLException, CannotReachDatabaseException, ApplicationPropertiesException {
        Collection<DbRecord> listOfQuestions = new LinkedHashSet<>();
        try (PreparedStatement query = connect.prepareStatement(SQL_QUERY_SEARCH_QUESTION_BY_TOPIC)) {
            query.setString(1, topic);
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Topic topicAsObject = new Topic(
                        queryResult.getString("name")
                );
                Question questionAsObject = new Question(
                        queryResult.getString("content"),
                        queryResult.getInt("difficulty")
                );
                DbRecord record = new DbRecord(topicAsObject, questionAsObject);
                listOfQuestions.add(record);
            }
        }
        return listOfQuestions;
    }



    private int lazyAdd(String topic, String question, int difficulty, Collection<String> answers, Connection connect) throws SQLException {
        try (
                PreparedStatement insertAnswers = connect.prepareStatement("INSERT INTO response(answer,question_ID)VALUES(?,(SELECT ID FROM question WHERE content=?))");
                PreparedStatement insertQuestion = connect.prepareStatement("INSERT INTO question(content,difficulty,topic_ID) VALUES(?,?,(SELECT ID FROM topic WHERE name=?))")
        ) {
            insertQuestion.setString(1, question);
            insertQuestion.setInt(2, difficulty);
            insertQuestion.setString(3, topic);
            insertQuestion.addBatch();
            insertQuestion.executeUpdate();

            for (String ans : answers
            ) {
                insertAnswers.setString(1, ans);
                insertAnswers.setString(2, question);
                insertAnswers.addBatch();
                insertAnswers.executeUpdate();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return -1;
        }
        return 1;
    }
}
