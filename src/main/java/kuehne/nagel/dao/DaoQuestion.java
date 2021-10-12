package kuehne.nagel.dao;

import kuehne.nagel.data.DbRecord;
import kuehne.nagel.data.Question;
import kuehne.nagel.data.Topic;
import kuehne.nagel.exceptions.ApplicationPropertiesException;
import kuehne.nagel.exceptions.CannotReachDatabaseException;
import kuehne.nagel.exceptions.InternalDatabaseException;
import kuehne.nagel.sql.SQLQueryType;
import lombok.Data;

import java.sql.*;
import java.util.*;

import static kuehne.nagel.sql.SQLQueryType.SAVE_QUERY;
import static kuehne.nagel.sql.SQLQueryType.SEARCH_QUERY;
import static kuehne.nagel.sql.SqlQueries.*;
import static kuehne.nagel.utils.ValidateInput.*;

@Data
public class DaoQuestion implements DaoQuestionI {
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
                    addResponseRecordById(response.getQuestion().getId(), connect, answers);
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
            query.setInt(1, recordId);
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

    public int deleteQuestion(String question) {
        int validation = validateJDBCSearchQueryBeforeDbConnection(question);
        if (validation < 1) {
            return -1;
        } else
            return deleteQuestionUtil(question);
    }

    private int deleteQuestionUtil(String question) {
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

    private Collection<DbRecord> findAllData(String topic, Connection connect) throws SQLException, CannotReachDatabaseException, ApplicationPropertiesException {
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
     *
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
