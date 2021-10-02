package kuehne.nagel.dao;

import kuehne.nagel.Question;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public interface DaoQuestionI {

    public List<Question> searchQuestion(String topic) throws DaoException;

  //  public void saveQuestion(String topic, String response) throws DaoException;
}
