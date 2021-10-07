package kuehne.nagel;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Answer {

    private int id;
    @NonNull
    private String answer;
    @NonNull
    private int question_id;

    public Answer(String answer) {
        this.answer = answer;
    }
}
