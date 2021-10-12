package kuehne.nagel.dao;

import java.sql.SQLException;

public interface DaoQuestionI {

    int searchAllQuestionsByTopic(String topic) throws SQLException;

    int saveQuestion(String topic, String question, int difficulty, String... answers);

    int updateQuestion(String topic, String old_question, String new_content, int difficulty, String... new_answers);

    int deleteQuestion(String question);
}
