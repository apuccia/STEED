package it.unipi.trustgraphmanager.exceptions;

import it.unipi.trustgraphmanager.dtos.ErrorDTO;
import it.unipi.trustgraphmanager.utilities.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HexFormat;


@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String LOG_ERROR_DECODED = "Decoded error message: ";

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleConflict(final Exception exception, final WebRequest webRequest) {
        log.error(exception.getMessage(), exception.getCause());

        return handleExceptionInternal(exception, ErrorDTO.builder().errorMessage(ErrorMessages.GENERIC_ERROR),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    @ExceptionHandler({ ControllerException.class })
    protected ResponseEntity<Object> handleControllerException(final ControllerException controllerException,
            final WebRequest webRequest) {
        final String decodedRevertReason = new String(HexFormat.of()
                .parseHex(controllerException.getErrorMessage().substring(11))).trim();
        log.error(LOG_ERROR_DECODED + decodedRevertReason, controllerException.getCause());
        final ErrorDTO error = ErrorDTO.builder().errorMessage(decodedRevertReason).build();

        return handleExceptionInternal(controllerException, error, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }
}
