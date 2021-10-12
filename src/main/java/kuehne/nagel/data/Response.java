package kuehne.nagel.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Response extends DbRecord {
    @NonNull
    private String answer;
    @NonNull
    private int question_id;
}
