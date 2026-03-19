package ru.kolidgio.bankapp.transfer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import ru.kolidgio.bankapp.transfer.service.errors.OperationBlockedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ProblemDetail handleBadRequest(HttpClientErrorException.BadRequest e) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Некорректный запрос", e);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ProblemDetail handleNotFound(HttpClientErrorException.NotFound e) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, "Ресурс не найден", e);
    }

    @ExceptionHandler(OperationBlockedException.class)
    public ProblemDetail handleBlocked(OperationBlockedException e) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setTitle("Операция заблокирована");
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException e) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Некорректный запрос");
        return problemDetail;
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String title, HttpClientErrorException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, extractDetail(e));
        problemDetail.setTitle(title);
        return problemDetail;
    }

    private String extractDetail(HttpClientErrorException e) {
        try {
            JsonNode root = objectMapper.readTree(e.getResponseBodyAsString());
            JsonNode detail = root.get("detail");
            if (detail != null && !detail.isNull()) {
                return detail.asText();
            }
        } catch (Exception ignored) {
        }
        return e.getMessage();
    }
}