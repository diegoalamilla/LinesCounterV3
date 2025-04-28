package com.proy.validator.concreteValidators;

import java.util.List;

import com.proy.exceptions.CodeStandarException;
import com.proy.validator.validatorContext.CodeValidationContext;
import com.proy.validator.validatorContext.StandardValidator;

/**
 * La clase "FunctionStructureValidator" proporciona los métodos para validar
 * una formato de firmas de funciones para poder hacer la suma de lineas físicas
 * solo en caso de ser un estructura de función
 * 
 * @version 1.3
 */

public class FunctionStructureValidator extends StandardValidator {

    public FunctionStructureValidator(CodeValidationContext codeValidationContext) {
        super(codeValidationContext);
    }

    /**
     * Cuenta una línea de código física si la linea o lineas representan una
     * función y está en el formato
     * 
     * @param lines que representa las lineas de un código java
     * @return si es una estructura de función y está en el formato
     * 
     */
    @Override
    public boolean validate(List<String> lines) throws CodeStandarException {
        isIncorrectStructure(lines.get(0));
        if (isAbstractInterfaceFunction(lines.get(0))) {
            return false;
        } else if (isFunction(lines.get(0))) {
            getCodeValidationContext().addPhysicalLine();
            getCodeValidationContext().incrementNumMethods();
            return true;
        } else if (isIncompleteFunction(lines)) {
            if (matchesPattern(lines.get(0).trim(), "^.*?\\{\\s*(//.*)?$")) {
                getCodeValidationContext().addPhysicalLine();
                getCodeValidationContext().incrementNumMethods();
            } else {
                getCodeValidationContext().addPhysicalLine();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Revisa si la linea es una función
     * 
     * @param line representa la linea de código a validar
     * @return si es una función completa
     * @throws CodeStandarException si es una estrucura de función y no está en el
     *                              formato
     */
    private boolean isFunction(String line) throws CodeStandarException {
        String structureFunction = "^[a-zA-Z\\s]+?\\s?[\\w<>\\[\\],]+\\s+\\w+\\s*\\(.*\\)\\s*.*\\{\\s*(//.*)?";
        return matchesPattern(line.trim(), structureFunction);
    }

    /**
     * Verifica si una línea corresponde a una función abstracta o de interfaz.
     * 
     * @param line Línea de código a verificar.
     * @return {@code true} si la línea es una función abstracta o de interfaz.
     * @throws CodeStandarException Si no cumple con el formato esperado.
     */
    public boolean isAbstractInterfaceFunction(String line) throws CodeStandarException {
        String structureAbstractFunction = "^(\\w+\\s+)*(abstract)\\s+\\w+\\s+\\w+\\s*\\(.*$";
        String structureInterfaceFunction = "^(\\w+\\s+)*(abstract\\s+)?\\s*\\w+\\s+\\w+\\s*\\(.*\\)\\S*;\\s*(//.*)?$";
        return matchesPattern(line.trim(), structureAbstractFunction)
                || matchesPattern(line.trim(), structureInterfaceFunction);
    }

    /**
     * Verifica si la línea tiene una estructura incorrecta de función o control.
     * 
     * @param line Línea de código a verificar.
     * @return {@code false} si la estructura es incorrecta, lanza excepción.
     * @throws CodeStandarException Si la estructura es incorrecta.
     */
    private boolean isIncorrectStructure(String line) throws CodeStandarException {
        String structureFunction = "^(\\w+\\s+)+\\w+\\s*\\(.*\\)\\s*.*\\s*\\{.*\\}\\{?\\s*(//.*)?";
        if (matchesPattern(line.trim(), structureFunction)) {
            throw new CodeStandarException("No se cumple el formato de codigo de estructuras de control");
        }
        return false;
    }

    /**
     * Revisa si la primera linea del código es una función incompleta, es decir que
     * ocurrió un salto de línea, para validar la función completa
     * 
     * @param lines representa la lineas de código a validar
     * @return si es una función con salto de línea
     * @throws CodeStandarException si es una estrucura de función y no está en el
     *                              formato
     */
    private boolean isIncompleteFunction(List<String> lines) throws CodeStandarException {
        String structure = "(\\w+\\s+)+\\w+\\s*\\(.*\\s*";
        if (matchesPattern(lines.get(0).trim(), structure) && !lines.get(0).trim().startsWith("return")) {
            return findEndOfLine(lines);
        }
        return false;
    }

}
