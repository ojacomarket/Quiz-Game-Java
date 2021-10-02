package kuehne.nagel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Question {

    private int id;
    @NonNull
    private String content;
    @NonNull
    private int difficulty;
    private int topid_id;
    @NonNull
    private String topic_name;
}
