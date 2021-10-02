package kuehne.nagel;

import lombok.Data;

@Data
public class Response {

    private int id;
    private String answer;
    private boolean correct;
    private int topic_id;
}
