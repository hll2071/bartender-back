package bartender.bartenderback.global.security.jwt.exception;

import com.example.jwtboilerplate.global.error.ErrorCode;
import com.example.jwtboilerplate.global.error.exception.BusinessBaseException;

public class InvalidJwtTokenException extends BusinessBaseException {
  public static final BusinessBaseException EXCEPTION =
          new InvalidJwtTokenException();

  private InvalidJwtTokenException() { super(ErrorCode.INVALID_JWT); }
}
