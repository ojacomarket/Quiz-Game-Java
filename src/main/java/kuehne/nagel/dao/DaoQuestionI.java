package kuehne.nagel.dao;

import kuehne.nagel.Question;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public interface DaoQuestionI {

    public List<Question> searchQuestion(String topic) throws SQLException;//throws DaoException;

  //  public void saveQuestion(Question question, Response answers_for_question) throws DaoException;
}
