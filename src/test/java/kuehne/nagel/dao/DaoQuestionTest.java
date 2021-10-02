package kuehne.nagel.dao;

import kuehne.nagel.Question;
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
    public static final Question CARS1 = new Question("Cars", "What is the fastest car on a planet?", 4);
    public static final Question CARS2 = new Question("Cars", "What is the first car invented?", 5);
    public static final Question CARS3 = new Question("Cars", "Which car has a horse as its logo?", 1);

    @Test
    @DisplayName("PART 1: Search question by topic: TEST 1: If we input empty string on topic query" +
            " we will get empty list")
    void no_topic_return_empty_list() {
        SearchQuestionByTopic new_search = new SearchQuestionByTopic();
        assertEquals(EMPTY_LIST, new_search.searchQuestionByTopic(""));
    }
     @Test
     @DisplayName("PART 1: Search question by topic: TEST 2: If topic doesn't exist we have nothing to do" +
             " with questions, since they simply don't exist either")
     void if_topic_doesnt_exist_return_empty_list() {
         SearchQuestionByTopic new_search = new SearchQuestionByTopic();
         assertEquals(EMPTY_LIST, new_search.searchQuestionByTopic("3rf3rg"));
     }
     @Test
     @DisplayName("PART 1: Search question by topic: TEST 3: If topic does exist we get data from it," +
             " namely topic name, content of question and difficulty level")
     void if_topic_does_exist_return_list_with_topic_question_and_difficulty_lvl() {
         LIST_TO_BE_FILLED.add(CARS1);
         LIST_TO_BE_FILLED.add(CARS2);
         LIST_TO_BE_FILLED.add(CARS3);
         SearchQuestionByTopic new_search = new SearchQuestionByTopic();
         assertEquals(LIST_TO_BE_FILLED, new_search.searchQuestionByTopic("Cars"));
     }

}
