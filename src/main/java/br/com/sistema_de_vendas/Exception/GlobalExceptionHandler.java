/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.Exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.sistema_de_vendas.DTOs.ErroDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

   /**
     * Converte erros de validação de campos em uma lista de DTOs.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroDTO>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErroDTO> errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(f -> new ErroDTO(f.getField(), f.getDefaultMessage()))
            .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Converte uma exceção de regras de negócio em resposta 409.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroDTO> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(409).body(new ErroDTO("geral", ex.getMessage()));
    }

    /**
     * Retorna um erro genérico para exceções inesperadas do servidor.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> handleGenericException(Exception ex) {
        return ResponseEntity.status(500).body(new ErroDTO("geral", "Erro interno do servidor"));
    }

}
