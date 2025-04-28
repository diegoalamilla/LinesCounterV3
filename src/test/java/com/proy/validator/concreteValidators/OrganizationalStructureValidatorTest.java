package com.proy.validator.concreteValidators;

import com.proy.exceptions.CodeStandarException;
import com.proy.validator.validatorContext.CodeValidationContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase OrganizationalStructureValidator
 */
public class OrganizationalStructureValidatorTest {

    private OrganizationalStructureValidator validator;
    private CodeValidationContext context;

    /**
     * Configuración inicial antes de cada prueba
     */
    @Before
    public void setUp() {
        context = new CodeValidationContext();
        validator = new OrganizationalStructureValidator(context);
    }

    /**
     * Verifica que se valide correctamente una estructura organizacional
     */
    @Test
    public void testValidateWithOrganizationalStructure() throws CodeStandarException {
        List<String> lines = Collections.singletonList("package com.example;");
        boolean result = validator.validate(lines);
        assertTrue(result);
    }

    /**
     * Verifica que una línea sin estructura organizacional no sea validada
     */
    @Test
    public void testValidateWithoutOrganizationalStructure() throws CodeStandarException {
        List<String> lines = Collections.singletonList("public class Test {}");
        boolean result = validator.validate(lines);
        assertFalse(result);
    }

    /**
     * Verifica que una lista vacía genere una excepción
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testValidateEmptyListThrowsException() throws CodeStandarException {
        validator.validate(Collections.emptyList());
    }

    /**
     * Verifica que la detección de estructuras organizacionales funcione correctamente
     */
    @Test
    public void testIsOrganizationalStructure() throws CodeStandarException {
        assertTrue(validator.validate(Collections.singletonList("import java.util.List;")));
        assertFalse(validator.validate(Collections.singletonList("class Example {}")));
    }
}
