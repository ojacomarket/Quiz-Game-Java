package kuehne.nagel.sql;

public abstract class SqlQueries {

    public static final String SQL_QUERY_SEARCH_QUESTION_BY_TOPIC = "SELECT name,content,difficulty FROM topic INNER JOIN question ON topic.ID = question.topic_ID WHERE name LIKE LOWER(?)";
    public static final String SQL_INNER_JOIN_FOR_SAVE = "SELECT t.ID AS TOPIC_ID, q.ID AS Q_ID, t.name, q.content, q.difficulty FROM topic t INNER JOIN question q ON t.ID = q.topic_ID WHERE t.name LIKE LOWER(?)";
    public static final String SQL_QUERY_SAVE_QUESTION_TO_EXISTING_TOPIC = "INSERT INTO question (content, difficulty, topic_ID) VALUES (LOWER(?),?,?)";
    public static final String SQL_UPDATE_QUESTION_TABLE = " update question set content=?, difficulty=?,topic_ID=? WHERE content=?";
    public static final String SQL_UPDATE_RESPONSE_TABLE = "UPDATE response SET answer=? WHERE question_ID=?";
    public static final String SQL_DELETE_RESPONSE_RECORD = "DELETE FROM response WHERE question_ID=?";
    public static final String SQL_DELETE_RECORD = "DELETE FROM question WHERE content=?";
    public static final String SQL_ADD_RESPONSE_RECORD = "INSERT INTO response(answer,question_ID)VALUES(LOWER(?),?)";
}
