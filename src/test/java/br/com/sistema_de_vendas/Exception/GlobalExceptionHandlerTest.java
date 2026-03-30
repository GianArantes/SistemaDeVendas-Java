/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.sistema_de_vendas.DTOs.ErroDTO;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleValidationErrors_deveRetornarBadRequest() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("cliente", "cnpj", "CNPJ inválido");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<List<ErroDTO>> response = handler.handleValidationErrors(exception);

        assertEquals(400, response.getStatusCode().value(), "Deve retornar 400 para erro de validação");
        assertEquals(1, response.getBody().size(), "Deve retornar uma lista com a mensagem de erro");
        assertEquals("cnpj", response.getBody().get(0).campo());
    }

    @Test
    void handleBusiness_deveRetornarConflito() {
        BusinessException exception = new BusinessException("Regra de negócio violada");

        ResponseEntity<ErroDTO> response = handler.handleBusiness(exception);

        assertEquals(409, response.getStatusCode().value(), "Deve retornar status 409 para business exception");
        assertEquals("Regra de negócio violada", response.getBody().mensagem());
    }

    @Test
    void handleGenericException_deveRetornarInternalServerError() {
        Exception exception = new RuntimeException("Erro inesperado");

        ResponseEntity<ErroDTO> response = handler.handleGenericException(exception);

        assertEquals(500, response.getStatusCode().value(), "Deve retornar status 500 para exceção genérica");
        assertEquals("Erro interno do servidor", response.getBody().mensagem());
    }
}
