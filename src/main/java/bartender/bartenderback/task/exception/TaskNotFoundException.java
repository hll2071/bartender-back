package bartender.bartenderback.task.exception;

import bartender.bartenderback.global.exception.BusinessException;
import bartender.bartenderback.global.exception.ErrorCode;

public class TaskNotFoundException extends BusinessException {

    public TaskNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND);
    }
}
