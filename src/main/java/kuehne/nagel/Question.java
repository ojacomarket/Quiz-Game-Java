package kuehne.nagel;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Question {

    private int id;
    @NonNull
    private String content;
    @NonNull
    private int difficulty;
    private int topic_id;
    @NonNull
    private String topic_name;

    public Question (String topic_name, String content, int difficulty) {
        this.topic_name = topic_name;
        this.content = content;
        this.difficulty = difficulty;
        this.topic_id = topic_id;
    }
}
