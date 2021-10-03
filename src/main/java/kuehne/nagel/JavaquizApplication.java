package kuehne.nagel;

import kuehne.nagel.utils.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JavaquizApplication {

	public static void main(String[] args) {
		List<Question> queryResult;
		//SearchQuestionByTopic new_search = new SearchQuestionByTopic();
		//new_search.searchQuestionByTopic("");
		//queryResult = new_search.searchQuestionByTopic("Cars");
		//new_search.searchQuestionByTopic("34f3gnc");

		//SearchTopicIDbyTopicName new_s2 = new SearchTopicIDbyTopicName();
		//SearchQuestionIDbyQuestionName new_s3 = new SearchQuestionIDbyQuestionName();
		//System.out.println(new_s3.searchQuestionIdByQuestionName("What is the first car invented?"));
		SaveQuestion save1 = new SaveQuestion();
		List<Response> ANSWERS_TO_GO = new LinkedList<>();
		ANSWERS_TO_GO.add(new Response("m000ke"));
		ANSWERS_TO_GO.add(new Response("myke"));
		save1.saveQuestion("Which is my colocxzxcr?", 4,"Cars", ANSWERS_TO_GO);
		/*CreateTopic ct = new CreateTopic();
		ct.createTopicIfEmpty("Guns");*/
		/*GetUnusedId gu = new GetUnusedId();
		System.out.println(gu.getUnusedId("topic"));*/

	}
}
