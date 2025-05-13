package com.proy.Domain.validator.concreteValidators;

import java.util.List;

import com.proy.Domain.exceptions.CodeStandarException;
import com.proy.Domain.validator.validatorContext.CodeValidationContext;
import com.proy.Domain.validator.validatorContext.StandardValidator;

/**
 * La clase "OrganizationalStructureValidator" proporciona los métodos para validar una formato de estructura organizacional para poder hacer la suma de lineas físicas solo
 * en caso de ser una estructura organizacional
 * @version 1.0
 */

public class OrganizationalStructureValidator extends StandardValidator{

    public OrganizationalStructureValidator(CodeValidationContext codeValidationContext){
        super(codeValidationContext);
    }

    /**
     * Cuenta una línea de código física si la linea o lineas representan una estructura organizacional
     * 
     * @param lines que representa las lineas de un código java
     * @return si es una estructura de estructura organizacional
     * @throws CodeStandarException 
     */

    @Override
    public boolean validate(List<String> lines) throws CodeStandarException {
        if(isOrganizationalStructure(lines.get(0))){
            getCodeValidationContext().addPhysicalLine();
            return true;
        }else{
            return false;
        }
    }

    /**
     * Revisa si la linea es una estructura organizacional
     * 
     * @param line representa la linea de código a validar
     * @return si es una estructura organizacional
     */

    private boolean isOrganizationalStructure(String line) throws CodeStandarException {
        String organizationalKeywords = "^(package|import)\\s+(static)?\\s*[\\w\\.]+\\*?;\\s*(//.*)?$";
        if (matchesPattern(line, organizationalKeywords)){
            return true;
        } else {
            if (line.trim().startsWith("package ")||line.trim().startsWith("import ")){
                throw new CodeStandarException("Los imports y package no cumplen el formato de codigo");
            }
           return false;
        }
    }

}