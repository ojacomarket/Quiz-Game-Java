package kuehne.nagel;

import kuehne.nagel.config.Config;
import kuehne.nagel.dao.DaoQuestion;
import kuehne.nagel.dao.DatabaseConnection;

import java.sql.SQLException;

public class JavaquizApplication {

	public static void main(String[] args) throws Exception {
		DatabaseConnection.getConnectionToDb();
	}

}
