package bartender.bartenderback.user.exception;

import bartender.bartenderback.global.exception.BusinessException;
import bartender.bartenderback.global.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND);
    }
}
