package com.proy.Domain.validator.concreteValidators;
import com.proy.Domain.exceptions.CodeStandarException;
import com.proy.Domain.validator.validatorContext.CodeValidationContext;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para StructureDefinitionValidator
 */
public class StructureDefinitionValidatorTest {
    
    private StructureDefinitionValidator validator;
    private CodeValidationContext context;

    @Before
    public void setUp() {
        context = new CodeValidationContext();
        validator = new StructureDefinitionValidator(context);
    }

    /**
     * Verifica que una definición válida de clase sea reconocida correctamente.
     */
    @Test
    public void testValidClassDefinition() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("public class Test {");
        assertTrue(validator.hasClassDefinition(lines));
    }

    /**
     * Verifica que una definición válida de interfaz sea reconocida correctamente.
     */
    @Test
    public void testValidInterfaceDefinition() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("public interface Test {");
        assertTrue(validator.hasInterfaceOrEnumDefinition(lines));
    }

    /**
     * Verifica que una definición válida de enum sea reconocida correctamente.
     */
    @Test
    public void testValidEnumDefinition() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("public enum Test {");
        assertTrue(validator.hasInterfaceOrEnumDefinition(lines));
    }

    /**
     * Verifica que una definición válida de record sea reconocida correctamente.
     */
    @Test
    public void testValidRecordDefinition() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("public record a(int x){");
        lines.add("public  record   MYRecord   (int  x  ) {");
        lines.add("record   MYRecord   (int  x  ) {");
        assertTrue(validator.hasRecordDefinition(lines));
    }

    /**
     * Verifica que una estructura anidada arroje una excepción.
     */
    @Test(expected = CodeStandarException.class)
    public void testNestedDefinitionThrowsException() throws CodeStandarException {
        validator.hasNestedDefinition("class Nested {");
    }

    /**
     * Verifica que una estructura incorrecta no sea validada.
     */
    @Test
    public void testInvalidDefinition() throws CodeStandarException {
        List<String> lines = new ArrayList<>();
        lines.add("class {");
        assertFalse(validator.hasClassDefinition(lines));
    }
}
