package io.github.LibraryAPI.controller.common;

import io.github.LibraryAPI.controller.dto.ErroCampo;
import io.github.LibraryAPI.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        List<FieldError> fieldErrors = ex.getFieldErrors();


        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(
                fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Erro de validação",
                listaErros);
    }
}
