package com.proy.Domain.validator.concreteValidators;

import java.util.List;

import com.proy.Domain.exceptions.CodeStandarException;
import com.proy.Domain.validator.validatorContext.CodeValidationContext;
import com.proy.Domain.validator.validatorContext.StandardValidator;

/**
 * La clase "PhysicalFormatValidator" proporciona los métodos para validar una
 * formato de líneas físicas para poder hacer la suma de físicas solo
 * en caso de ser una línea física que necesite validarse
 * 
 * @version 1.1
 */

public class PhysicalFormatValidator extends StandardValidator {

    public PhysicalFormatValidator(CodeValidationContext codeValidationContext) {
        super(codeValidationContext);
    }

    /**
     * Cuenta una línea de código física si cumple con el formato
     * 
     * @param lines que representa las lineas de un código java
     * @return si una línea está en el formato
     * @throws CodeStandarException si una línea no está en el formato
     */

    @Override
    public boolean validate(List<String> lines) throws CodeStandarException {
        if (isCorrectFormat(lines.get(0))) {
            getCodeValidationContext().addPhysicalLine();
            return true;
        }
        return false;
    }

    /**
     * Revisa primero si es una línea no válida en el formato
     * 
     * @param line representa la linea de código a validar
     * @return si es una linea sin faltas en el formato físico
     * @throws CodeStandarException si una línea no está en el formato
     */

    private boolean isCorrectFormat(String line) throws CodeStandarException {
        MultipleStatements(line);
        if (line.trim().startsWith("{") || line.trim().startsWith("else") || line.trim().startsWith("finally")
                || line.trim().startsWith(";")) {
            throw new CodeStandarException("No se cumple el formato de codigo");
        } else if (line.trim().startsWith("}")) {
            return validateMiddleOfFlowControl(line);
        } else if (line.trim().startsWith("@")) {
            return hasCorrectAnnotation(line);
        }
        return true;
    }

    /**
     * Verifica si una línea contiene múltiples declaraciones de variables en una
     * sola línea.
     * Si se encuentra más de una declaración, lanza una excepción.
     * 
     * @param line La línea de código a evaluar.
     * @return {@code false} si no se encuentran declaraciones múltiples.
     * @throws CodeStandarException Si se encuentran múltiples declaraciones.
     */
    private boolean MultipleStatements(String line) throws CodeStandarException {
        String multipleStatements = "\\b[a-zA-Z_]\\w*\\s+[a-zA-Z_]\\w*\\s*(?:,\\s*[a-zA-Z_]\\w*\\s*)+;\\s*(//.*)?";
        if (matchesPattern(line.trim(), multipleStatements)) {
            throw new CodeStandarException("No se permite declaraciones multiples");
        }
        return false;
    }

    /**
     * Revisa el formato de las lineas físicas de las estructuras de control
     * 
     * @param line representa la linea de código a validar
     * @return si es una linea sin faltas en el formato físico
     * @throws CodeStandarException si una línea no está en el formato
     */
    private boolean validateMiddleOfFlowControl(String line) throws CodeStandarException {
        if (!line.trim().equals("}")) {
            String structure = "^\\s*\\}\\s*(else\\s+if\\s*\\(.*\\)|else|finally|catch\\s*\\(\\s*\\w+(\\s+\\w+)?\\s*\\))\\s*\\{\\s*(//.*)?$";
            String doWhileStructure = "^\\s*\\}\\s*while\\s*\\(.*\\)\\s*;\\s*(//.*)?$";
            String endLine = "^}\\s*(//.*)?$";
            if (matchesPattern(line, structure) || matchesPattern(line, endLine) || matchesPattern(line, doWhileStructure)) {
                return true;
            } else {
                throw new CodeStandarException("No se cumple el formato de codigo de estrcuturas de flujo de control");
            }
        }
        return true;
    }

    /**
     * Revisa que las anotaciones y el formato de las palabras clave try y static
     * 
     * @param line representa la linea de código a validar
     * @return si es una linea sin faltas en el formato físico
     * @throws CodeStandarException si una línea no está en el formato
     */

    private boolean hasCorrectAnnotation(String line) throws CodeStandarException {
        String anonotation = "^@\\w+\\s*(\\(.*\\))?(\\s*//.*)?$";
        if (matchesPattern(line.trim(), anonotation)) {
            return true;
        } else {
            throw new CodeStandarException("Las anotaciones no cumplen el formato de codigo");
        }
    }
}
