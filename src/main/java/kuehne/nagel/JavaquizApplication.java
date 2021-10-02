package kuehne.nagel;

import kuehne.nagel.dao.DaoQuestion;
import kuehne.nagel.utils.SearchQuestionByTopic;

import java.sql.SQLException;
import java.util.List;

public class JavaquizApplication {

	public static void main(String[] args) {
		//DaoQuestion daoq = new DaoQuestion();
		List<Question> queryResult;
		SearchQuestionByTopic new_search = new SearchQuestionByTopic();
		new_search.searchQuestionByTopic("");
		new_search.searchQuestionByTopic("Cars");
		new_search.searchQuestionByTopic("34f3gnc");
		/*try {
			queryResult = daoq.searchQuestion("");
			if (queryResult.isEmpty()) {
				//System.out.println("\nNo question on that topic...\n");
				return;
			}
			for (Question question:queryResult) {

				System.out.printf("Topic name ::: %s\nQuestion ::: %s\nDifficulty ::: %d\n\n", question.getTopic_name(),
						question.getContent(), question.getDifficulty());
			}
		} catch (NullPointerException nullPointerException) {
			//System.err.println("No empty input allowed!\n");
		}

		}*/
	}
}
