package kuehne.nagel.data;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class DbRecord {
    @NonNull
    private Topic topic;
    @NonNull
    private Question question;
    private Response response;


}
