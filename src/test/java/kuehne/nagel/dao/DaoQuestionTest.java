package kuehne.nagel.dao;

import kuehne.nagel.Answer;
import kuehne.nagel.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DaoQuestionTest {
    public static final List<Question> EMPTY_LIST = new LinkedList<>();
    public static final List<Question> LIST_TO_BE_FILLED = new LinkedList<>();

    public static final List<Answer> LIST_TO_BE_FILLED2 = new LinkedList<>();
    public static final Question CARS1 = new Question("cars", "most popular color of nissan?",1);
    public static final Question CARS2 = new Question("cars", "less popular color of nissan?", 1);
    public static final Question CARS3 = new Question("cars", "less popular color of yamaha?", 5);

    DaoQuestion interactWithDb = new DaoQuestion();

    //////////////////////////////////////////////////////////////////////////////////////////////
    // !!! Testing SEARCH QUESTION BY TOPIC QUERY

    @Test
    @DisplayName("PART 1: Search question by topic: TEST 1: If we input empty string on topic query" +
            " we will get empty list")
    void no_topic_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic(""));
    }

    @Test
    @DisplayName("PART 1: Search question by topic: TEST 2: If topic doesn't exist we have nothing to do" +
            " with questions' query, since they simply don't exist either")
    void if_topic_doesnt_exist_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("3rf3rg"));
    }
    //TODO: BEFORE RUN ENSURE, THAT YOU GOT FILLED ALL QUESTIONS INTO GIVEN TOPIC CORRECTLY
   /* @Test
    @DisplayName("PART 1: Search question by topic: TEST 3: If topic does exist we get data from it," +
            " namely topic name, content of question and difficulty level")
    /*void if_topic_does_exist_return_list_with_topic_question_and_difficulty_lvl() {
        LIST_TO_BE_FILLED.add(CARS1);
       // LIST_TO_BE_FILLED.add(CARS2);
       // LIST_TO_BE_FILLED.add(CARS3);
        assertEquals(LIST_TO_BE_FILLED, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/
   /* //TODO: BEFORE RUN ENSURE, THAT YOU MESS UP (CHANGE IN A BAD WAY) WITH APPLICATION.PROPERTIES FILE
    @Test
    @DisplayName("PART 1: Search question by topic: TEST 4: If problems with property file," +
            " return empty list")
    void if_problems_with_property_file_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL QUESTION FROM QUESTIONS TABLE
    /*@Test
    @DisplayName("PART 1: Search question by topic: TEST 5: If topic does exist BUT questions' table," +
            " is empty, then we return empty list")
    void if_topic_does_exist_but_question_table_is_empty_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED TOPIC TABLE FROM A DATABASE
   /*@Test
    @DisplayName("PART 1: Search question by topic: TEST 6: If topic doesn't exist in a database," +
            " and the topic table itself is empty, return empty list")
    void all_tables_are_empty_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL TABLES FROM A DATABASE
   /* @Test
    @DisplayName("PART 1: Search question by topic: TEST 7: If tables in a QUIZ database doesn't" +
            " exist, return empty list")
    void if_tables_are_down_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED DATABASE ITSELF
    /*@Test
    @DisplayName("PART 1: Search question by topic: TEST 8: If database QUIZ doesn't" +
            " exist, return empty list")
    void if_db_is_down_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/
    //////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////
    // !!! Testing SAVE QUESTION QUERY
    @Test
    @DisplayName("PART 2: Save question: TEST 1: If we input empty string as a topic on saving query" +
            " we will get -5 as error code indicator")
    void no_topic_on_question_returns_0() {
        assertEquals(-5, interactWithDb.saveQuestion("", "What is the fastest car on a planet?", 5, "Hurracan"));
    }

    @Test
    @DisplayName("PART 2: Save question: TEST 2: If we input empty question content on saving query " +
            "we will get -4 as error code")
    void no_question_on_question_returns_minus_one() {
        assertEquals(-4, interactWithDb.saveQuestion("Cars", "", 5, "In 1996"));
    }

    @Test
    @DisplayName("PART 2: Save question: TEST 3: If we input difficulty level outside" +
            "a range from 1 to 5 on saving query we will get -3 as error code")
    void no_difficulty_lvl_on_question_returns_minus_two() {
        assertEquals(-3, interactWithDb.saveQuestion("Cars", "What is the fastest car on a planet?", 0, "Yes"));
        assertEquals(-3, interactWithDb.saveQuestion("Cars", "What is the fastest car on a planet?", 6, "At 2250 km/h"));
    }

    @Test
    @DisplayName("PART 2: Save question: TEST 4: If we have no answers on question then " +
            "we will get -6")
    void no_answers_on_question_returns_minus_three() {
        assertEquals(-6, interactWithDb.saveQuestion("Cars", "What is the fastest car on a planet?", 3, ""));
    }

    @Test
    @DisplayName("PART 2: Save question: TEST 5: If we have all needed values passed, but" +
            "put topic on input, that doesn't exist, we will get -4")
    void if_topic_doesnt_exist_returns_minus_four() {
        assertEquals(0, interactWithDb.saveQuestion("Cartoons", "How old is Cartoon Network?", 5, "25 years"));
    }

    @Test
    @DisplayName("PART 2: Save question: TEST 6: If we have topic inside db and" +
            " passed to store data is valid, then return 1")
    void if_everything_is_valid_then_return_1_and_data_will_be_stored_in_database() {
        assertEquals(1, interactWithDb.saveQuestion("Cars", "What is your favourite car?", 5, "Cabrio"));
    }
    //OK
    //TODO: BEFORE RUN MESS UP WITH APPLICATION.PROPERTIES FILE
    @Test
    @DisplayName("PART 2: Save question: TEST 7: If we have problems with database connection, we return -5")
    void db_connection_lost_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is Mitsubishi Motors?", 4, "53 years"));
    }
    //OK
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL RECORDS FROM RESPONSE TABLE
    @Test
    @DisplayName("PART 2: Save question: TEST 8: If we delete response table, then" +
            " query will return 0")
    void if_empty_reponse_table_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));
    }
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL QUESTION FROM QUESTIONS TABLE
    @Test
    @DisplayName("PART 2: Save question: TEST 9: If we delete all questions from" +
            " question table, then -2 is returned")
    void if_empty_question_table_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));
    }
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED TOPIC TABLE FROM A DATABASE
   @Test
    @DisplayName("PART 1: Save question: TEST 10: If topic doesn't exist in a database," +
            " and the topic table itself is empty, return -1")
    void all_tables_are_empty_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));;
    }
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL TABLES FROM A DATABASE
   @Test
    @DisplayName("PART 1: Save question: TEST 11: If tables in a QUIZ database doesn't" +
            " exist, return -1")
    void if_tables_are_down_return_empty_list() {
       assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));;
   }
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED DATABASE ITSELF
    @Test
    @DisplayName("PART 1: Save question: TEST 12: If QUIZ database doesn't" +
            " exist, return -1")
    void if_database_is_down_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));;
    }

}
