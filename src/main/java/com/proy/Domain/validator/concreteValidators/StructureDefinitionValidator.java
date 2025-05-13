package com.proy.Domain.validator.concreteValidators;

import java.util.List;

import com.proy.Domain.exceptions.CodeStandarException;
import com.proy.Domain.validator.validatorContext.CodeValidationContext;
import com.proy.Domain.validator.validatorContext.StandardValidator;

/**
 * La clase "StructureDefinitionValidator" proporciona los métodos para validar
 * una formato de estructuras de definción para poder hacer la suma de lineas
 * físicas solo
 * en caso de ser un estructura de definción
 * 
 * @version 1.0
 */

public class StructureDefinitionValidator extends StandardValidator {

    public StructureDefinitionValidator(CodeValidationContext codeValidationContext) {
        super(codeValidationContext);
    }

    /**
     * Cuenta una línea de código física si la linea o lineas representan una
     * definición y está en el formato
     * 
     * @param lines que representa las lineas de un código java
     * @return si es una estructura de definción y está en el formato
     * @throws CodeStandarException si es una estrucura de definción y no está en el
     *                              formato
     */

    @Override
    public boolean validate(List<String> lines) throws CodeStandarException {
        if (hasInterfaceOrEnumDefinition(lines) || hasClassDefinition(lines) || hasRecordDefinition(lines)
                || hasNestedDefinition(lines.get(0))) {
            getCodeValidationContext().addPhysicalLine();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Revisa si la linea es una definición de tipo Interface o enum
     * 
     * @param line representa la linea de código a validar
     * @return si es una definición completa
     */
    public boolean hasInterfaceOrEnumDefinition(List<String> lines) throws CodeStandarException {
        String structureKeywords = "^(public|private|protected)(\\s\\w+)*\\s+(interface|enum)\\s+\\w+(\\s+\\w+,?)*\\s*\\{?\\s*(//.*)?$";
        return hasCorrestStructure(lines, structureKeywords);
    }

    /**
     * Revisa si la linea es una definición de tipo class
     * 
     * @param line representa la linea de código a validar
     * 
     * @return si es una definición completa
     */
    public boolean hasClassDefinition(List<String> lines) throws CodeStandarException {
        String structureKeywords = "(public|private|protected)(\\s\\w+)*\\s+class\\s+\\w+(\\s+\\w+,?)*\\s*\\{?\\s*(//.*)?$";
        return hasCorrestStructure(lines, structureKeywords);
    }

    /**
     * Revisa si la linea es una definición de tipo record
     * 
     * @param line representa la linea de código a validar
     * @return si es una definición completa
     */
    public boolean hasRecordDefinition(List<String> lines) throws CodeStandarException {
        String structureKeywords = "(public|private|protected)(\\s\\w+)*\\s+record\\s+[\\w+]\\s*\\([^)]*\\)(\\s+\\w+,?)*\\s*\\{?\\s*(//.*)?$";
        return hasCorrestStructure(lines, structureKeywords);
    }

    /**
     * Revisa si la linea es una estructura de definición anidada
     * 
     * @param line representa la linea de código a validar
     * @return si es una definición completa
     */
    public boolean hasNestedDefinition(String line) throws CodeStandarException {
        if (line.trim().startsWith("class ") || line.trim().startsWith("interface ") || line.trim().startsWith("enum "))
            throw new CodeStandarException("No se pemite estructuras de control anidadas en el código");
        return false;
    }

    /**
     * Revisa si la linea cumpple con la estrcutura de definición, en caso de
     * parecer un salto de línea, revisar su final
     * 
     * @param line    representa la linea de código a validar
     * @param pattern representa la regla para validar
     * @return si es una definición completa
     * @throws CodeStandarException si es una estrucura de definición y no está en
     *                              el formato
     */
    public boolean hasCorrestStructure(List<String> lines, String pattern) throws CodeStandarException {
        String structure = "^.*?\\{\\s*(//.*)?$";
        if (matchesPattern(lines.get(0).trim(), pattern)) {
            if (!matchesPattern(lines.get(0).trim(), structure)) {
                return findEndOfLine(lines);
            }
            return true;
        } else {
            return false;
        }
    }

}
