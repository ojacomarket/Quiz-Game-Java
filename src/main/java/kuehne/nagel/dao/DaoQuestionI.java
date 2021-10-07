package kuehne.nagel.dao;

import kuehne.nagel.Question;

import java.sql.SQLException;
import java.util.List;

public interface DaoQuestionI {

    List<Question> showAllQuestionsByTopic(String topic) throws SQLException;

    //  public void saveQuestion(Question question, Answer answers_for_question) throws DaoException;
}
