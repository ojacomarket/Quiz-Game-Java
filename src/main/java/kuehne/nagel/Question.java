package kuehne.nagel;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Question {

    private int id;
    @NonNull
    private String topic_name;
    @NonNull
    private String content;
    @NonNull
    private int difficulty;
    private int topic_id;
}
