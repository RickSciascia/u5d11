package ricksciascia.u5d11.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import ricksciascia.u5d11.payloads.ErrorDTO;
import ricksciascia.u5d11.payloads.ErrorsListDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ValException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request
    public ErrorsListDTO handleValEx(ValException ex) {
        return new ErrorsListDTO(ex.getMessage(), LocalDateTime.now(),ex.getListaErrori());
    }

    @ExceptionHandler(BadReqException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request
    public ErrorDTO handleBadReqEx(BadReqException ex) {
        return new ErrorDTO(ex.getMessage(),LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404 Not Found
    public ErrorDTO handleNotFoundEx(NotFoundException ex) {
        return new ErrorDTO(ex.getMessage(),LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
    public ErrorDTO handleGenericError(Exception ex) {
        ex.printStackTrace();
        return new ErrorDTO("C'è stato un problema con il server! Siamo al lavoro per sistemarlo!", LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 401 Unauthorized (non autenticato anche se termine fuorviante)
    public ErrorDTO handleUnauthorizedEx(UnauthorizedException ex) {
        return new ErrorDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403 Forbidden (vietato)
    public ErrorDTO handleForbidden(AuthorizationDeniedException ex) {
        return new ErrorDTO("Non hai i permessi per eseguire questa azione!",LocalDateTime.now());
    }
}
