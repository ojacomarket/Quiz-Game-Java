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
    //Only lowercase allowed, otherwise test will fail
    public static final Question MATH = new Question("math", "what is the opposite to differentiating?", 3); //Integrating

    DaoQuestion interactWithDb = new DaoQuestion();

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// SEARCH QUERY //////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void empty_topic_returns_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic(""));
    }

    @Test
    void topic_doesnt_exist_OR_has_no_questions_returns_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Science"));
    }

    //TODO: BEFORE RUN ENSURE, THAT QUESTIONS YOU PROVIDE EXIST IN A QUERIED TABLE
   /*@Test
    void topic_exist_questions_exist_returns_filled_list() {
        LIST_TO_BE_FILLED.add(MATH);
        assertEquals(LIST_TO_BE_FILLED, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT YOU MAKE TYPO IN APPLICATION.PROPERTIES FILE (LIKE: APP#E#.PROROF)
    /*@Test
    void if_problems_with_property_file_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL QUESTION FROM QUESTION TABLE (FIRST DELETE RESPONSES!)
    /*@Test
    void if_topic_does_exist_but_question_table_is_empty_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL TOPICS FROM TOPIC TABLE
   /*@Test
    void if_topic_table_doesnt_exist_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL TABLES FROM A DATABASE
    /*@Test
    void no_tables_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED DATABASE ITSELF
    /*@Test
    void if_database_is_down_return_empty_list() {
        assertEquals(EMPTY_LIST, interactWithDb.showAllQuestionsByTopic("Cars"));
    }*/
    //////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// SAVE QUERY //////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////
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
    /*@Test
    @DisplayName("PART 2: Save question: TEST 7: If we have problems with database connection, we return -5")
    void db_connection_lost_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is Mitsubishi Motors?", 4, "53 years"));
    }*/
    //OK
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL RECORDS FROM RESPONSE TABLE
    /*@Test
    @DisplayName("PART 2: Save question: TEST 8: If we delete response table, then" +
            " query will return 0")
    void if_empty_reponse_table_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL QUESTION FROM QUESTIONS TABLE
    /*@Test
    @DisplayName("PART 2: Save question: TEST 9: If we delete all questions from" +
            " question table, then -2 is returned")
    void if_empty_question_table_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED TOPIC TABLE FROM A DATABASE
   /*@Test
    @DisplayName("PART 2: Save question: TEST 10: If topic doesn't exist in a database," +
            " and the topic table itself is empty, return -1")
    void all_tables_are_empty_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));;
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL TABLES FROM A DATABASE
   /*@Test
    @DisplayName("PART 2: Save question: TEST 11: If tables in a QUIZ database doesn't" +
            " exist, return -1")
    void if_tables_are_down_return_empty_list() {
       assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));;
   }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED DATABASE ITSELF
    /*@Test
    @DisplayName("PART 2: Save question: TEST 12: If QUIZ database doesn't" +
            " exist, return -1")
    void if_database_is_down_return_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Cars", "How old is MyDean", 1, "6 years"));;
    }*/
    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// UPDATE QUERY //////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("PART 3: Update question: TEST 1: If empty topic name passed, then return -5")
    void if_empty_topic_return_minus_5() {
        assertEquals(-5, interactWithDb.updateQuestion("", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Toyota Lanzz3"));
    }

    @Test
    @DisplayName("PART 3: Update question: TEST 2: If empty old question passed, return -4")
    void if_empty_old_question_return_minus_4() {
        assertEquals(-4, interactWithDb.updateQuestion("Cars", "",
                "Slowest car on a planet?", 3, "Toyota Lanzz3"));
    }

    @Test
    @DisplayName("PART 3: Update question: TEST 3: If empty new question passed, return 0")
    void if_empty_old_question_return_0() {
        assertEquals(0, interactWithDb.updateQuestion("Cars", "What is my favourite car?",
                "", 3, "Toyota Lanzz3"));
    }

    @Test
    @DisplayName("PART 3: Update question: TEST 4: If empty answers passed, return 0")
    void if_empty_answers_return_0() {
        assertEquals(-6, interactWithDb.updateQuestion("Cars", "What is the fastest car on a planet?",
                "Slowest car on a planet?", 3, ""));
    }

    @Test
    @DisplayName("PART 3: Update question: TEST 5: If topic doesn't exist return 1")
    void if_topic_doesnt_exist_return_1() {
        assertEquals(1, interactWithDb.updateQuestion("Caers", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Toyota Lanzz3"));
    }

    @Test
    @DisplayName("PART 3: Update question: TEST 6: If question of particular topic doesn't exist," +
            " return 1")
    void if_question_doesnt_exist_return_1() {
        assertEquals(1, interactWithDb.updateQuestion("Cars", "Fassdtest car on a planet?",
                "Slowest car on a planet?", 3, "Toyota Lanzz3"));
    }

    @Test
    @DisplayName("PART 3: Update question: TEST 7: If all data is valid, execute UPDATE query and" +
            " return 3")
    void if_all_data_valid_return_3() {
        assertEquals(3, interactWithDb.updateQuestion("Cars", "Slowest car on a planet?",
                "Fastest car on a planet?", 3, "Porsche"));
    }

    @Test
    @DisplayName("PART 3: Update question: TEST 8: If all data is valid, but response table is empty" +
            " return 3 (we are still able to add answers)")
    void if_all_data_valid_but_response_table_is_empty_return_3() {
        assertEquals(3, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }

    //add todo
    //ADD TO ERROR MESSAGE: RESPONSE TABLE IS DOWN
    @Test
    @DisplayName("PART 3: Update question: TEST 9: If all data is valid, but response table is deleted" +
            " return 3 (we are still able to add answers)")
    void if_all_data_valid_but_response_table_is_deleted_return_1() {
        assertEquals(1, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }

    //add todo
    @Test
    @DisplayName("PART 3: Update question: TEST 10: If all data is valid, but question" +
            " table is empty, return 1")
    void if_all_data_valid_but_question_table_is_empty_return_1() {
        assertEquals(1, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }

    //add todo
    @Test
    @DisplayName("PART 3: Update question: TEST 11: If all data is valid, but question" +
            " table is down, return 1")
    void if_all_data_valid_but_question_table_is_down_return_1() {
        assertEquals(1, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }

    //add todo
    @Test
    @DisplayName("PART 3: Update question: TEST 12: If all data is valid, but topic" +
            " table is empty, return 1")
    void if_all_data_valid_but_topic_table_is_empty_return_1() {
        assertEquals(1, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }

    //add todo
    //when we delete table, print err message of INVALID_SQL_QUERY!! to all cases
    @Test
    @DisplayName("PART 3: Update question: TEST 13: If all data is valid, but topic" +
            " table is down, return -2")
    void if_all_data_valid_but_topic_table_is_deleted_return_minus_2() {
        assertEquals(-2, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }

    //add todo
    //strange behaviour 4 times called error message of APPLICATION PROPERTIES FILE, wtf??
    //when we delete table, print err message of INVALID_SQL_QUERY!! to all cases
    @Test
    @DisplayName("PART 3: Update question: TEST 14: If all data is valid, but database" +
            " down, return -2")
    void if_all_data_valid_but_database_is_down_return_minus_2() {
        assertEquals(-1, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// DELETE QUERY //////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("PART 4: Delete question: TEST 1: If topic is empty, return ")
    void dele() {
        assertEquals(1, interactWithDb.deleteQuestion("", "Fastest car on a planet?"));
    }
}
