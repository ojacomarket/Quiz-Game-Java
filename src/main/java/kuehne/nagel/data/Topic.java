package kuehne.nagel.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Topic extends DbRecord {

    @NonNull
    private int id;
    @NonNull
    private String name;
    private Collection<Question> questions;

    public Topic (String name) {
        this.name = name;
    }
}
