package kuehne.nagel.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DaoException extends Exception {

    public DaoException(String err_msg) {
        super(err_msg);
    }

    public DaoException(String err_msg, Throwable cause) {
        super(err_msg, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
