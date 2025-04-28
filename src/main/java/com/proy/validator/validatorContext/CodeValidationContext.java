package com.proy.validator.validatorContext;

import java.util.List;

import com.proy.exceptions.CodeStandarException;
import com.proy.model.CodeSegment;

/**
 * La clase "CodeValidationContext" almacena una referencia a uno de los objetos de los validadores concretos
 * CodeValidationContext se comunica con el objeto de StandardValidator
 * @version 2.0
 */


public class CodeValidationContext {

    private StandardValidator standardValidator;
    private CodeSegment codeSegment;

    public CodeValidationContext(){
        this.codeSegment = new CodeSegment();
    }

    public CodeValidationContext(StandardValidator standardValidator){
        this.standardValidator = standardValidator;
        this.codeSegment = new CodeSegment();
    }

    public boolean validate(List<String> lines) throws CodeStandarException{
        return standardValidator.validate(lines);
    }

    public StandardValidator getStandardValidator() {
        return standardValidator;
    }

    public void setStandardValidator(StandardValidator standardValidator) {
        this.standardValidator = standardValidator;
    }

    public CodeSegment getCodeSegment() {
        return codeSegment;
    }

    public void setCodeSegment(CodeSegment codeSegment) {
        this.codeSegment = codeSegment;
    }

    public int getPhysicalLines(){
        return this.codeSegment.getPhysicalLines();
    }

    public void addPhysicalLine(){
        this.codeSegment.addPhysicalLine();
    }

    public void addPhysicalLine(int num){
        this.codeSegment.addPhysicalLines(num);
    }

    public void incrementNumMethods() {
        this.codeSegment.incrementNumMethods();
    }

    public int getNumMethods() {
        return this.codeSegment.getNumMethods();
    }
}