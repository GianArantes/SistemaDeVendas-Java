package br.com.sistema_de_vendas.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Pattern;

import br.com.sistema_de_vendas.DTOs.EnderecoDTO;
import br.com.sistema_de_vendas.Exception.BusinessException;

public final class ValidationUtils {

    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern NCM_PATTERN = Pattern.compile("\\d{8}");
    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{8}");
    private static final Pattern STATE_PATTERN = Pattern.compile("[A-Za-z]{2}");

    private ValidationUtils() {
        // Utilitário estático
    }

    public static void assertNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new BusinessException(fieldName + " é obrigatório");
        }
    }

    public static void assertMinLength(String value, int minLength, String fieldName) {
        if (value == null || value.length() < minLength) {
            throw new BusinessException(fieldName + " deve ter ao menos " + minLength + " caracteres");
        }
    }

    public static void assertEmail(String value, String fieldName) {
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new BusinessException(fieldName + " deve ser um email válido");
        }
    }

    public static void assertCnpj(String value, String fieldName) {
        if (!CNPJ_PATTERN.matcher(value).matches()) {
            throw new BusinessException(fieldName + " deve conter exatamente 14 dígitos numéricos");
        }
    }

    public static void assertValidDate(String value, String fieldName) {
        try {
            LocalDate.parse(value);
        } catch (DateTimeParseException ex) {
            throw new BusinessException(fieldName + " deve estar no formato yyyy-MM-dd");
        }
    }

    public static void assertValidUUID(String value, String fieldName) {
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            throw new BusinessException(fieldName + " deve ser um UUID válido");
        }
    }
    public static void validateEnderecoDTO(EnderecoDTO dto, String fieldName) {
        if (dto == null) {
            throw new BusinessException(fieldName + " é obrigatório");
        }
        assertNotBlank(dto.logradouro(), fieldName + ".logradouro");
        assertNotBlank(dto.numero(), fieldName + ".numero");
        assertNotBlank(dto.cep(), fieldName + ".cep");
        assertCep(dto.cep(), fieldName + ".cep");
        assertNotBlank(dto.bairro(), fieldName + ".bairro");
        assertNotBlank(dto.cidade(), fieldName + ".cidade");
        assertNotBlank(dto.estado(), fieldName + ".estado");
        assertState(dto.estado());
    }

    private static void assertCep(String value, String fieldName) {
        if (!CEP_PATTERN.matcher(value).matches()) {
            throw new BusinessException(fieldName + " deve conter exatamente 8 dígitos");
        }
    }
    public static <E extends Enum<E>> void assertValidEnum(String value, Class<E> enumClass, String fieldName) {
        try {
            Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException(fieldName + " deve ser um valor válido de " + enumClass.getSimpleName());
        }
    }

    public static void assertNcm(String value) {
        if (!NCM_PATTERN.matcher(value).matches()) {
            throw new BusinessException("codigo deve conter exatamente 8 dígitos");
        }
    }

    public static void assertState(String value) {
        if (!STATE_PATTERN.matcher(value).matches()) {
            throw new BusinessException("estado deve conter exatamente 2 letras");
        }
    }

    public static void assertNonNegative(BigDecimal value, String fieldName) {
        if (value == null) {
            throw new BusinessException(fieldName + " é obrigatória");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(fieldName + " não pode ser negativa");
        }
    }
}
