package br.com.sistema_de_vendas.Exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.sistema_de_vendas.DTOs.ErroDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroDTO>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErroDTO> errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(f -> new ErroDTO(f.getField(), f.getDefaultMessage()))
            .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    // Exceção personalizada (ex: Email já existe)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroDTO> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(409).body(new ErroDTO("geral", ex.getMessage()));
    }

    // Tratamento genérico para outras exceções
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> handleGenericException(Exception ex) {
        return ResponseEntity.status(500).body(new ErroDTO("geral", "Erro interno do servidor"));
    }

}
