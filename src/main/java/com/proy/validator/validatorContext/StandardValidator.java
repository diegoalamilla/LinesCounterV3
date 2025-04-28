package com.proy.validator.validatorContext;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.proy.exceptions.CodeStandarException;
import com.proy.model.CodeSegment;

/**
 * La clase "StandardValidator" declara el metodo específico para los
 * validadores concretos y métoods comunes.
 * 
 * @version 1.0
 */

public abstract class StandardValidator {

    private CodeValidationContext codeValidationContext;

    public StandardValidator(CodeValidationContext codeValidationContext) {
        this.codeValidationContext = codeValidationContext;
    }

    protected CodeSegment codeSegment;

    public abstract boolean validate(List<String> lines) throws CodeStandarException;

    public boolean isCommentLine(String line) {
        return line.trim().startsWith("//")
                || line.trim().startsWith("*")
                || line.trim().startsWith("/*");
    }

    public boolean matchesPattern(String line, String structure) {
        Pattern pattern = Pattern.compile(structure);
        Matcher matcher = pattern.matcher(line.trim());
        return matcher.matches();
    }

    public CodeSegment getCodeSegment() {
        return codeSegment;
    }

    public void setCodeSegment(CodeSegment codeSegment) {
        this.codeSegment = codeSegment;
    }

    public CodeValidationContext getCodeValidationContext() {
        return codeValidationContext;
    }

    public void setCodeValidationContext(CodeValidationContext codeValidationContext) {
        this.codeValidationContext = codeValidationContext;
    }

    /**
     * Revisa las lineas de código hasta encontrar el final de linea de la
     * estructrura es decir {
     * 
     * @param lines representa la lineas de código a validar
     * @return si es una estructura de control con salto de línea con formato
     *         correcto
     * @throws CodeStandarException si es una estrucura de control y no está en el
     *                              formato
     */

    public boolean findEndOfLine(List<String> lines) throws CodeStandarException {
        lines.remove(0);
        String endLine = "^.*?\\{\\s*(//.*)?$";
        String incorrectEndLine = "^.*?\\;\\s*(//.*)?$";
        while (lines.size() > 0) {
            if (isCommentLine(lines.get(0).trim())) {
                lines.remove(0);
                continue;
            } else if (matchesPattern(lines.get(0).trim(), incorrectEndLine)) {
                throw new CodeStandarException("No se cumple el formato de codigo de estructuras de control");
            } else if (matchesPattern(lines.get(0).trim(), endLine)) {
                if (lines.get(0).trim().startsWith("{")) {
                    throw new CodeStandarException("No se cumple el formato de codigo de estructuras de control");
                }
                this.codeValidationContext.addPhysicalLine();
                return true;
            }
            if (lines.size() > 0) {
                lines.remove(0);
            }
            this.codeValidationContext.addPhysicalLine();
        }

        if (lines.size() <= 0) {
            throw new CodeStandarException("No se cumple el formato de codigo de estructuras de control");
        }
        return false;
    }
}
