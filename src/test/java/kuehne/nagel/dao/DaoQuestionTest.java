package kuehne.nagel.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaoQuestionTest {

    DaoQuestion interactWithDb = new DaoQuestion();
    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// SEARCH QUERY //////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * Result of 1 means SUCCESS
     * Result of 0 means NO secondary DATA, not critical error, or cannot reach a database
     * Result of -1 means critical error, NO PRIMARY DATA, or SQL syntax invalid
     */
    @Test
    void SEARCH_empty_input_for_topic_returns_0() {
        assertEquals(0, interactWithDb.searchAllQuestionsByTopic(""));
    }

    @Test
    void SEARCH_unknown_topic_returns_0() {
        assertEquals(0, interactWithDb.searchAllQuestionsByTopic("Science"));
    }

    //TODO: BEFORE RUN ENSURE, THAT TOPIC YOU PROVIDE EXISTS IN A TOPIC TABLE
    @Test
    void SEARCH_existing_topic_returns_1() {
        assertEquals(1, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT RESPONSE TABLE IS EMPTY
    @Test
    void SEARCH_existing_topic_but_empty_response_table_returns_1() {
        assertEquals(1, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT RESPONSE TABLE IS DELETED
    @Test
    void SEARCH_existing_topic_but_deleted_response_table_returns_1() {
        assertEquals(1, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT QUESTION TABLE IS EMPTY
    @Test
    void SEARCH_existing_topic_but_empty_question_table_returns_0() {
        assertEquals(0, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT QUESTION TABLE IS DELETED
    @Test
    void SEARCH_existing_topic_but_deleted_question_table_returns_minus_1() {
        assertEquals(-1, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT TOPIC TABLE IS EMPTY
    @Test
    void SEARCH_empty_topic_table_returns_minus_1() {
        assertEquals(-1, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT TOPIC TABLE IS DELETED
    @Test
    void SEARCH_deleted_topic_table_returns_minus_1() {
        assertEquals(-1, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT DATABASE IS DELETED
    @Test
    void SEARCH_deleted_db_returns_0() {
        assertEquals(0, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT YOU HAVE INVALID DATA IN APPLICATION.PROPERTIES FILE
    @Test
    void SEARCH_invalid_app_prop_file_returns_0() {
        assertEquals(0, interactWithDb.searchAllQuestionsByTopic("Math"));
    }

    //TODO: BEFORE RUN ENSURE, THAT YOU MAKE TYPO IN APPLICATION.PROPERTIES FILE (LIKE: APP#E#.PROROF)
    @Test
    void SEARCH_typo_in_app_prop_file_returns_0() {
        assertEquals(0, interactWithDb.searchAllQuestionsByTopic("Cars"));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// SAVE QUERY //////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * Result of 1 means SUCCESS
     * Result of 0 means NO secondary DATA, not critical error, or cannot reach a database
     * Result of -1 means critical error, NO PRIMARY DATA, or SQL syntax invalid
     */
    @Test
    void SAVE_empty_input_for_topic_returns_0() {
        assertEquals(0, interactWithDb.saveQuestion("", "what sports do michael phelps?", 2, "Swimming"));
    }

    @Test
    void SAVE_empty_input_for_question_returns_0() {
        assertEquals(0, interactWithDb.saveQuestion("Sport", "", 2, "Swimming"));
    }

    @Test
    void SAVE_out_of_boundaries_difficulty_level_returns_0() {
        assertEquals(0, interactWithDb.saveQuestion("Sport", "What sports do michael phelps?", 6, "Swimming"));
    }

    @Test
    void SAVE_empty_response_returns_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Sport", "What sports do michael phelps?", 2, ""));
    }
    //TODO: BEFORE RUN ENSURE, THAT TOPIC YOU PROVIDE EXISTS IN A TOPIC TABLE
    /*@Test
    void SAVE_valid_data_returns_1_and_adds_record_into_db() {
        assertEquals(1, interactWithDb.saveQuestion("Sport", "What sports do Usain bolt Jr?", 1, "100 m sprint", "400m relay race"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU CLEAR RESPONSE TABLE
   /* @Test
    void SAVE_valid_data_but_response_table_is_empty_returns_1() {
        assertEquals(1, interactWithDb.saveQuestion("Sport", "What sports do ramon dekkers?", 5, "myai thai", "boxing", "kickboxing"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU DELETE RESPONSE TABLE
   /* @Test
    void SAVE_valid_data_but_response_table_is_deleted_returns_minus_1_but_question_will_store() {
        assertEquals(-1, interactWithDb.saveQuestion("Sport", "What sports do Michael Jordan?", 4, "basketball"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT QUESTION TABLE IS EMPTY OR DELETED
    /*@Test
    void SAVE_valid_data_but_question_table_is_empty_OR_deleted_returns_minus_1() {
        assertEquals(-1, interactWithDb.saveQuestion("Sport", "What sports do usain bolt?", 1, "100 m sprint", "400 m relay race"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT TOPIC AND QUESTION TABLE HAVE EMPTY SETS BOTH IN DB
    /*@Test
    void SAVE_empty_question_and_response_table_will_still_allow_to_add_questions_returns_1() {
        assertEquals(1, interactWithDb.saveQuestion("Sport", "What sports do Ott Tanak?", 3, "rally"));
    }*/
    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// UPDATE QUERY //////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void UPDATE_if_empty_topic_returns_0() {
        assertEquals(0, interactWithDb.updateQuestion("", "Fastest car on a planet?",
                "Slowest car on a planet?", 4, "Dacia Duster"));
    }

    @Test
    void UPDATE_if_empty_old_question_returns_0() {
        assertEquals(0, interactWithDb.updateQuestion("", "Slowest car on a planet?",
                "Cars", 3, "Dacia Duster"));
    }

    @Test
    void UPDATE_if_empty_new_question_returns_0() {
        assertEquals(0, interactWithDb.updateQuestion("What is my favourite car?", "",
                "Cars", 3, "Dacia Duster"));
    }

    @Test
    void UPDATE_if_empty_answers_returns_1_which_means_that_no_errors_but_nothing_added() {
        assertEquals(1, interactWithDb.updateQuestion("Biggest car company?", "What is the fastest car on a planet?",
                "Cars", 3, ""));
    }
    //TODO: BEFORE RUN ENSURE, THAT YOU TOPIC DOESNT EXIST IN DB
   /* @Test
    void UPDATE_if_topic_doesnt_exist_returns_0_since_topic_is_fundamental_for_hierarchy() {
        assertEquals(0, interactWithDb.updateQuestion("Speed", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Dacia Duster"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT SUCH  QUESTION DOESNT EXIST IN DB
   /* @Test
    void UPDATE_if_question_doesnt_exist_returns_1_just_no_data() {
        assertEquals(1, interactWithDb.updateQuestion("Hello?", "Fastest car on a planet?",
                "Cars", 3, "Dacia Duster"));
    }*/
   /* @Test
    void UPDATE_if_all_data_valid_returns_1() {
        assertEquals(1, interactWithDb.updateQuestion("What is the year of Tesla company foundation?", "Hello world?",
                "Cars", 3, "2003"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU CLEAR RESPONSE TABLE
    /*@Test
    void UPDATE_if_all_data_valid_but_response_table_is_empty_returns_1_since_adds_data_to_db() {
        assertEquals(1, interactWithDb.updateQuestion("What is the year of Tesla company foundation?", "Hello world?",
                "Cars", 3, "Base C"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT YOU DELETE RESPONSE TABLE
    /*@Test
    void UPDATE_if_all_data_valid_but_response_table_is_data_is_deleted_returns_minus_1() {
        assertEquals(-1, interactWithDb.updateQuestion("What is the year of Tesla company foundation?", "Hello world?",
                "Cars", 3, "Base C"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT YOU CLEAR QUESTION TABLE
    /*@Test
    void UPDATE_if_all_data_valid_but_question_table_is_empty_returns_0() {
        assertEquals(0, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU DELETE QUESTION TABLE
    /*@Test
    void UPDATE_if_all_data_valid_but_question_table_is_down_returns_minus_1() {
        assertEquals(-1, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU CLEAR TOPIC TABLE
    /*@Test
   void UPDATE_if_all_data_valid_but_topic_table_is_empty_returns_minus_1() {
        assertEquals(-1, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }*/
    //TODO: BEFORE RUN ENSURE, THAT YOU DELETE TOPIC TABLE
    /*@Test
    void UPDATE_if_all_data_valid_but_topic_table_is_deleted_returns_0() {
        assertEquals(-1, interactWithDb.updateQuestion("Cars", "Fastest car on a planet?",
                "Slowest car on a planet?", 3, "Ymaaha"));
    }*/
    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// DELETE QUERY //////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void DELETE_if_question_empty_returns_0() {
        assertEquals(-1, interactWithDb.deleteQuestion(""));
    }

    @Test
    void DELETE_if_all_data_valid_delete_question_and_cascade_its_responses_returns_1() {
        assertEquals(1, interactWithDb.deleteQuestion("what sports do michael phelps?"));
    }

    //TODO: BEFORE RUN ENSURE, THAT RESPONSES ARE DELETED
    /*@Test
    void DELETE_no_responses_table_still_able_to_delete_returns_1() {
        assertEquals(1, interactWithDb.deleteQuestion("what sports do michael phelps?"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT QUESTION RECORDS ARE CLEARED
    /*@Test
    void DELETE_no_question_records_returns_1_since_we_have_nothing_to_delete() {
        assertEquals(1, interactWithDb.deleteQuestion("what sports do michael phelps?"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT QUESTION TABLE IS DELETED
    /*@Test
    void DELETE_if_question_table_is_down_returns_minus_1 ()

    {
        assertEquals(-1, interactWithDb.deleteQuestion("what sports do michael phelps?"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT TOPIC TABEL is DELETED/CLEARED
    /*@Test
    void DELETE_no_topic_table_return_minus_1() {
        assertEquals(-1, interactWithDb.deleteQuestion("what sports do michael phelps?"));
    }*/

    //TODO: BEFORE RUN ENSURE, THAT DB IS DELETED
    /*@Test
    void DELETE_no_db_returns_0() {
        assertEquals(-1, interactWithDb.deleteQuestion("what sports do michael phelps?"));
    }*/
}
