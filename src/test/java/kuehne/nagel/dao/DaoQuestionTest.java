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
    void no_topic_returns_ERROR_CODE_minus_3() {
        assertEquals(-3, interactWithDb.saveQuestion("", "What sports do michael phelps?", 2, "Swimming"));
    }
    @Test
    void no_question_on_returns_ERROR_CODE_minus_3() {
        assertEquals(-3, interactWithDb.saveQuestion("Cars", "", 5, "2003"));
    }
    @Test
    void out_of_boundaries_difficulty_lvl_returns_ERROR_CODE_minus_3() {
        assertEquals(-3, interactWithDb.saveQuestion("Sport", "What sports do michael phelps?", 2, "Swimming"));
        assertEquals(-3, interactWithDb.saveQuestion("Cars", "What is the year of tesla company foundation?", 5, "2003"));
    }
    @Test
    void no_answers_returns_ERROR_CODE_minus_3() {
        assertEquals(-3, interactWithDb.saveQuestion("Cars", "What is the year of tesla company foundation?", 5, ""));
    }
    @Test
    void if_topic_doesnt_exist_returns_ERROR_CODE_minus_4() {
        assertEquals(-4, interactWithDb.saveQuestion("Cartoons", "How old is Cartoon Network?", 5, "25 years"));
    }
    @Test
    void if_everything_is_valid_then_returns_1_and_data_will_be_stored_in_database() {
        assertEquals(1, interactWithDb.saveQuestion("Cars", "What is the fastest car on a planet?", 4, "Thrust SSC"));
    }
    //TODO: BEFORE RUN, ENSURE, THAT QUESTION ALREADY EXISTS IN THE DATABASE
    /*@Test
    void if_everything_is_valid_but_user_duplicates_question_on_given_topic_then_returns_ERROR_CODE_minus_5() {
        assertEquals(-5, interactWithDb.saveQuestion("Cars", "What is the fastest car on a planet?", 4, "Thrust SSC"));
    }*/
    //TODO: BEFORE RUN MAKE UP TYPO IN APPLICATION.PROPERTIES FILE NAME
    /*@Test
    void type_in_app_properties_file_returns_ERROR_CODE_minus_0() {
        assertEquals(0, interactWithDb.saveQuestion("Cars", "How old is Mitsubishi Motors?", 4, "53"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL QUESTIONS FROM QUESTION TABLE
    /*@Test
    void if_empty_question_table_returns_1_and_fill_it_with_new_data() {
        assertEquals(1, interactWithDb.saveQuestion("Sport", "How old is Pele?", 5, "80"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED TOPIC TABLE DATA FROM A TOPIC TABLE
   /*@Test
    void all_tables_are_empty_returns_ERROR_CODE_minus_4
   () {
        assertEquals(-4, interactWithDb.saveQuestion("Math", "What sin of 90 degrees?", 1, "1"));;
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED ALL TABLES FROM A DATABASE
   /*@Test
    void if_tables_are_down_returns_ERROR_CODE_minus_4() {
       assertEquals(-4, interactWithDb.saveQuestion("Math", "How many kg in 1 liter?", 2, "1"));;
   }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE DELETED DATABASE ITSELF
    /*@Test
    void if_database_is_down_returns_ERROR_CODE_minus_4() {
        assertEquals(-4, interactWithDb.saveQuestion("Cars", "What is the most stable model of Hyundai?", 3, "Getz"));;
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
