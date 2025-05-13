package com.proy.validator.concreteValidators;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.proy.Domain.validator.concreteValidators.FunctionStructureValidator;
import com.proy.Domain.exceptions.CodeStandarException;
import com.proy.Domain.validator.validatorContext.CodeValidationContext;
import org.junit.Before;
import org.junit.Test;

public class FunctionStructureValidatorTest {

    private FunctionStructureValidator functionStructureValidator;
    private CodeValidationContext codeValidationContext;

    @Before
    public void setUp() {
        codeValidationContext = new CodeValidationContext();
        functionStructureValidator = new FunctionStructureValidator(codeValidationContext);
    }

    @Test
    public void testValidateFunction() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("public void myFunction() {");
        
        boolean result = functionStructureValidator.validate(lines);

        assertTrue(result);
    }

    @Test
    public void testValidateStaticFunction() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("public static void myStaticFunction() {");
        boolean result = functionStructureValidator.validate(lines);

        assertTrue(result);
    }

    @Test
    public void testValidateIncompleteFunction() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("public void myFunction(");
        lines.add("int x) {");
        boolean result = functionStructureValidator.validate(lines);

        assertTrue(result);
    }

    @Test
    public void testInvalidFunction() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("if (x > 0) {");
        boolean result = functionStructureValidator.validate(lines);

        assertFalse(result);
    }


}
