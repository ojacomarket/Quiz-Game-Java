package kuehne.nagel.utils;

import static kuehne.nagel.utils.DatabaseFailures.*;

public abstract class DatabaseConnectivityProblems {
    public static void printConnectivityErrorMessage(DatabaseFailures problem) {
        if (problem.equals(APPLICATION_PROPERTIES_FILE_FAILURE)) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM 1 ::: Check application.properties file, there could be an error\n\t\t\t>>>" +
                    " PROBLEM 2 ::: Database can be DOWN, so you never reach it");
        } else if (problem.equals(TYPO_APPLICATION_PROPERTIES_FILE_NAME)) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM ::: Typo in application.properties file nime");
        }
        else if (problem.equals(DATABASE_ABSENCE)) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM ::: Fill database appropriately, then query");
        } else if (problem.equals(INVALID_SQL_QUERY)) {
            System.err.println("APPLICATION >>> Aborting to establish database connection...\n\t\t\t>>>" +
                    " PROBLEM 1 ::: Check you SQL query syntax\n\t\t\t>>>" +
                    " PROBLEM 2 ::: Requested TABLE is gone");
        }
    }
}
