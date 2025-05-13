package com.proy.validator.concreteValidators;

import com.proy.Domain.validator.concreteValidators.PhysicalFormatValidator;
import com.proy.Domain.exceptions.CodeStandarException;
import com.proy.Domain.validator.validatorContext.CodeValidationContext;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas unitarias para la clase PhysicalFormatValidator.
 */
public class PhysicalFormatValidatorTest {

    private PhysicalFormatValidator validator;
    private CodeValidationContext context;

    @Before
    public void setUp() {
        context = new CodeValidationContext();
        validator = new PhysicalFormatValidator(context);
    }

    /**
     * Verifica que una línea válida sea aceptada.
     */
    @Test
    public void testValidPhysicalLine() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("int x;");
        lines.add("private int a();");
        assertTrue(validator.validate(lines));
    }

    /**
     * Verifica que una línea con declaración válida sea aceptada.
     */
    @Test
    public void testValidDeclaration() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("String nombre;");
        lines.add("int y = 3.14159265");
        assertTrue(validator.validate(lines));
    }

    /**
     * Verifica que una línea con enum válido sea aceptada.
     */
    @Test
    public void testValidEnumLiteral() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("LUNES, MARTES, MIERCOLES, JUEVES, VIERNES;");
        assertTrue(validator.validate(lines));
    }

    /**
     * Verifica que una línea con estructura de control inválida genere excepción.
     */
    @Test(expected = CodeStandarException.class)
    public void testInvalidControlStructure() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("else{");
        lines.add("else {");

        validator.validate(lines);
    }

    /**
     * Verifica que una línea con anotación válida sea aceptada.
     */
    @Test
    public void testValidAnnotation() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("@Override");
        lines.add("@Bean");
        lines.add("@gmail.com");
        assertTrue(validator.validate(lines));
    }

    /**
     * Verifica que una línea con múltiples declaraciones separadas por comas genere excepción.
     */
    @Test(expected = CodeStandarException.class)
    public void testInvalidMultipleDeclarations() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("int a, b, c;");
        lines.add("String nombre, apellido, email;");
        
        validator.validate(lines);
    }
}
