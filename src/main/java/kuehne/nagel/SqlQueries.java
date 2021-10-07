package kuehne.nagel;

public abstract class SqlQueries {

    public static final String SQL_QUERY_SEARCH_QUESTION_BY_TOPIC = "SELECT name,content,difficulty FROM topic INNER JOIN question ON topic.ID = question.topic_ID WHERE name LIKE LOWER(?)";
    public static final String SQL_QUERY_SAVE_QUESTION_TO_EXISTING_TOPIC = "INSERT INTO question (content, difficulty, topic_ID) VALUES (LOWER(?),LOWER(?),LOWER(?))";
    public static final String SQL_QUERY_TOPIC_ID_BY_TOPIC_NAME = "SELECT ID FROM topic WHERE name=?";
    public static final String SQL_QUERY_QUESTION_ID_BY_QUESTION_NAME = "SELECT ID FROM question WHERE content=?";
    public static final String SQL_QUERY_SAVE_ANSWER_TO_GIVEN_QUESTION = "INSERT INTO response (answer, question_ID) VALUES (?,?)";
    public static final String SQL_QUERY_RETRIEVE_UNUSED_ID = "SELECT MAX(ID) FROM ";
    public static final String SQL_QUERY_CREATE_TOPIC = "INSERT INTO topic (name) VALUES (?)";
    public static final String SQL_QUERY_TOPIC_ID_FROM_QUESTION_TABLE = "SELECT topic_ID FROM question WHERE content=?";
    public static final String SQL_UPDATE_QUESTION_TO_EXISTING_TOPIC = "UPDATE question SET content=?, difficulty=? WHERE ID=?";
    public static final String SQL_DELETE_ANSWERS_OF_EXISTING_QUESTION = "DELETE FROM response WHERE question_ID=?";
    public static final String SQL_QUERY_TO_DELETE_QUESTION = "DELETE FROM question WHERE ID=?";
}
