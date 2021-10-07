package kuehne.nagel;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {

    private int id;
    @NonNull
    private String answer;
    @NonNull
    private int question_id;

    public Response (String answer) {
        this.answer = answer;
    }
}
