package ee.himaster.core.service.exception;

/**
 * Exception is thrown when wrong in anything business logic.
 *
 * @author ilnaz-92@yandex.ru
 * Created on 03.01.2022
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
