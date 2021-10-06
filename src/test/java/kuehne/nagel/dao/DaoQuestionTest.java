package kuehne.nagel.dao;

import kuehne.nagel.Question;
import kuehne.nagel.Response;
import kuehne.nagel.utils.SaveQuestion;
import kuehne.nagel.utils.SearchQuestionByTopic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


 class DaoQuestionTest {
    public static final List<Question> EMPTY_LIST = new LinkedList <> ();
    public static final List<Question> LIST_TO_BE_FILLED = new LinkedList <> ();
    public static final List<Response> LIST_TO_BE_FILLED2 = new LinkedList <> ();
    public static final Question CARS1 = new Question("Cars", "What is the fastest car on a planet?", 4);
    public static final Question CARS2 = new Question("Cars", "What is the first car invented?", 5);
    public static final Question CARS3 = new Question("Cars", "Which car has a horse as its logo?", 1);

    DaoQuestion interactWithDb = new DaoQuestion();

    @Test
    @DisplayName("PART 1: Search question by topic: TEST 1: If we input empty string on topic query" +
            " we will get empty list")
    void no_topic_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.searchQuestion(""));
    }
     @Test
     @DisplayName("PART 1: Search question by topic: TEST 2: If topic doesn't exist we have nothing to do" +
             " with questions, since they simply don't exist either")
     void if_topic_doesnt_exist_return_empty_list() {
         assertEquals(EMPTY_LIST, interactWithDb.searchQuestion("3rf3rg"));
     }
    /* @Test
     @DisplayName("PART 1: Search question by topic: TEST 3: If topic does exist we get data from it," +
             " namely topic name, content of question and difficulty level")
     void if_topic_does_exist_return_list_with_topic_question_and_difficulty_lvl() {
         LIST_TO_BE_FILLED.add(CARS1);
         LIST_TO_BE_FILLED.add(CARS2);
         LIST_TO_BE_FILLED.add(CARS3);
         SearchQuestionByTopic new_search = new SearchQuestionByTopic();
         assertEquals(LIST_TO_BE_FILLED, new_search.searchQuestionByTopic("Cars"));
     }
     @Test
     @DisplayName("PART 2: Save question: TEST 1: If we input empty string on saving query" +
             " we will get 0")
     void no_topic_on_question_returns_0() {
         SaveQuestion saveQuestion = new SaveQuestion();
         LIST_TO_BE_FILLED2.add(new Response("Tuatara"));
         LIST_TO_BE_FILLED2.add(new Response("Bugatti"));
         assertEquals(0, saveQuestion.saveQuestion("","What is the fastest car on a planet?",5,LIST_TO_BE_FILLED2));
     }
     @Test
     @DisplayName("PART 2: Save question: TEST 2: If we input empty question content on saving query "+
             "we will get 0")
     void no_question_on_question_returns_0() {
         SaveQuestion saveQuestion = new SaveQuestion();
         LIST_TO_BE_FILLED2.add(new Response("Tuatara"));
         LIST_TO_BE_FILLED2.add(new Response("Bugatti"));
         assertEquals(0, saveQuestion.saveQuestion("Cars","",5,LIST_TO_BE_FILLED2));
     }
     @Test
     @DisplayName("PART 2: Save question: TEST 3: If we leave empty difficulty level on saving query "+
             "we will get 0")
     void no_difficulty_lvl_on_question_returns_0() {
         SaveQuestion saveQuestion = new SaveQuestion();
         LIST_TO_BE_FILLED2.add(new Response("Tuatara"));
         LIST_TO_BE_FILLED2.add(new Response("Bugatti"));
         assertEquals(0, saveQuestion.saveQuestion("Cars","What is the fastest car on a planet?",0,LIST_TO_BE_FILLED2));
     }
     @Test
     @DisplayName("PART 2: Save question: TEST 4: If we have no answers on question then "+
             "we will get 0")
     void no_answers_on_question_returns_0() {
         SaveQuestion saveQuestion = new SaveQuestion();
         assertEquals(0, saveQuestion.saveQuestion("Cars","What is the fastest car on a planet?",3,new LinkedList<>()));
     }
     @Test
     @DisplayName("PART 2: Save question: TEST 5: If we have all needed values passed, but"+
             "put topic on input, that doesn't exist, we will get 0")
     void if_topic_doesnt_exist_returns_0() {
         SaveQuestion saveQuestion = new SaveQuestion();
         LIST_TO_BE_FILLED2.add(new Response("Tuatara"));
         LIST_TO_BE_FILLED2.add(new Response("Bugatti"));
         assertEquals(0, saveQuestion.saveQuestion("Cartoons","What is the fastest car on a planet?",5,LIST_TO_BE_FILLED2));
     }
     @Test
     @DisplayName("PART 2: Save question: TEST 6: If we have all needed values passed and"+
             " data is valid, then return 3")
     void if_everything_is_valid_then_return_3_and_data_will_be_stored_in_database() {
         SaveQuestion saveQuestion = new SaveQuestion();
         LIST_TO_BE_FILLED2.add(new Response("Tuatara"));
         LIST_TO_BE_FILLED2.add(new Response("Bugatti"));
         assertEquals(3, saveQuestion.saveQuestion("Cars","What is the fastest car on a planet?",5,LIST_TO_BE_FILLED2));
     }*/

}
