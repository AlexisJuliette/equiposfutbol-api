package com.unicartagena.equiposfutbol.entrypoint.rest.v1.errors;

import com.unicartagena.equiposfutbol.domain.exceptions.DuplicateEquipoException;
import com.unicartagena.equiposfutbol.domain.exceptions.EquipoNotFoundException;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;
import com.unicartagena.equiposfutbol.domain.exceptions.DuplicateUserException;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidCredentialsException;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;
import com.unicartagena.equiposfutbol.domain.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    private final Clock clock;

    public GlobalRestExceptionHandler(Clock clock) {
        this.clock = clock;
    }

    @ExceptionHandler(EquipoNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            EquipoNotFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(DuplicateEquipoException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicate(
            DuplicateEquipoException exception, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(InvalidEquipoDataException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidData(
            InvalidEquipoDataException exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(
            UserNotFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiErrorResponse> handleUserDuplicate(
            DuplicateUserException exception, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<ApiErrorResponse> handleUserInvalidData(
            InvalidUserDataException exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException exception, HttpServletRequest request) {
        return build(HttpStatus.UNAUTHORIZED, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errores = new TreeMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            String mensaje = fieldError.getDefaultMessage();
            errores.putIfAbsent(fieldError.getField(), mensaje == null ? "Valor invalido" : mensaje);
        }
        return build(HttpStatus.BAD_REQUEST, "La solicitud no es valida", request, errores);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadable(HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud no es valido", request, Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(
            Exception exception, HttpServletRequest request) {
        log.error("Error inesperado procesando la solicitud", exception);
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrio un error inesperado", request, Map.of());
    }

    private ResponseEntity<ApiErrorResponse> build(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            Map<String, String> validationErrors) {
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(clock),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                Map.copyOf(validationErrors));
        return ResponseEntity.status(status).body(body);
    }
}
