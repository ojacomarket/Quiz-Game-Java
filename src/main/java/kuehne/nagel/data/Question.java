package kuehne.nagel.data;

import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Question extends DbRecord {

    @NonNull
    private int id;
    @NonNull
    private String content;
    @NonNull
    private int difficulty;
    @NonNull
    private int topic_id;
    private String topic_name;
    private Collection<Response> responses;

    public Question (String topic_name, String content, int difficulty) {
        this.topic_name = topic_name;
        this.content = content;
        this.difficulty = difficulty;
    }

    public Question (String content, int difficulty) {
        this.content = content;
        this.difficulty = difficulty;
    }
}
