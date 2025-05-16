package com.proy.Domain.validator.validatorControlers;

import java.util.List;

import com.proy.Domain.validator.concreteValidators.FunctionStructureValidator;
import com.proy.Domain.validator.concreteValidators.OrganizationalStructureValidator;
import com.proy.Domain.validator.concreteValidators.PhysicalFormatValidator;
import com.proy.Domain.validator.concreteValidators.StructureDefinitionValidator;
import com.proy.Domain.exceptions.CodeStandarException;
import com.proy.Domain.validator.validatorContext.CodeValidationContext;
import com.proy.Domain.validator.validatorContext.StandardValidator;

/**
 * La clase "StructureCountValidator" proporciona los métodos para validar el
 * contenido de una estrucutra de definición para el conteo de líneas
 * 
 * @version 2.0
 */

public class StructureCountValidator extends StandardValidator {

    public StructureCountValidator(CodeValidationContext codeValidationContext) {
        super(codeValidationContext);
    }

    /**
     * Valida que se cumplan las reglas de formato para el conteo de líneas físicas
     * y lógicas de todos los validadores concretos.
     * Cuenta una línea física al codeSegment del contexto en el caso que no
     * pertenezca a una línea lógica y que no rompa regla de formato de las líneas
     * físicas
     * 
     * @param lines que representa las lineas de un código java
     * @return si logró validar todas las líneas del contenido de una estructura de
     *         definición
     * @throws CodeStandarException si no está en el formato
     */
    @Override
    public boolean validate(List<String> lines) throws CodeStandarException {
        return isCorrectFileStructure(lines);
    }

    /**
     * Elimina la línea procesada de la lista de líneas por procesar, es un
     * comportamiento similar * de una pila, donde cada línea procesada, se elimina.
     * 
     * @param lines Las líneas del archivo a validar.
     * @return {@code true} si la estructura es correcta.
     * @throws CodeStandarException Si el archivo no cumple con la estructura
     *                              esperada.
     */
    private boolean isCorrectFileStructure(List<String> lines) throws CodeStandarException {
        while (lines.size() > 0) {
            String currentLine = lines.get(0);
            if (isCommentLine(currentLine) || currentLine.isBlank() || isOrganizationalStructure(lines)) {
                lines.remove(0);
            } else if (isStructureDefinition(lines)) {
                lines.remove(0);
                isCorrectContent(lines);
            } else {
                throw new CodeStandarException("El archivo no cumple con una estructura válida.");
            }
        }
        return true;
    }

    /**
     * Checa que el contenido de las líneas sea correcto, para eso checa si es una
     * definición de
     * estructura o si es comentario o línea en blanco.
     * 
     * @param lines Las líneas del archivo a validar.
     * @return {@code true} si la estructura es correcta.
     * @throws CodeStandarException Si el archivo no cumple con la estructura
     *                              esperada.
     */
    private boolean isCorrectContent(List<String> lines) throws CodeStandarException {
        while (!lines.isEmpty()) {
            String currentLine = lines.get(0);

            if (isStructureDefinition(lines)) {
                throw new CodeStandarException("No se permite más de una estructura en un archivo");
            } else if (isCommentLine(currentLine) || currentLine.isBlank() || isStructuresCorrectFormat(lines)) {
                lines.remove(0);
            } else {
                getCodeValidationContext().addPhysicalLine();
                lines.remove(0);
            }
        }
        return true;
    }

    /**
     * Verifica si las lineas pertecenen a una estrucura y cumple las reglas de
     * acuerdo al formato
     * 
     * @param lines que representa las lineas de un código java
     * @return si pertenece a una estrucura y cumple las reglas de acuerdo al
     *         formato
     * @throws CodeStandarException si no está en el formato
     */
    private boolean isStructuresCorrectFormat(List<String> lines) throws CodeStandarException{ 
        if (isFunctionStructure(lines)){
            return true;
        } else if(isPhysicalCorrectFormat(lines)){
            return true;
        }
        return false;
    }

    /**
     * Verifica si las lineas pertecenen a una estrucura y cumple las reglas de
     * acuerdo al formato cambiando a validadores concretos
     * 
     * @param lines que representa las lineas de un código java
     * @return si pertenece a una estrucura y cumple las reglas de acuerdo al
     *         formato
     * @throws CodeStandarException si no está en el formato
     */
    private boolean isPhysicalCorrectFormat(List<String> lines) throws CodeStandarException {
        getCodeValidationContext().setStandardValidator(new PhysicalFormatValidator(getCodeValidationContext()));
        return getCodeValidationContext().validate(lines);
    }

    private boolean isStructureDefinition(List<String> lines) throws CodeStandarException {
        getCodeValidationContext().setStandardValidator(new StructureDefinitionValidator(getCodeValidationContext()));
        return getCodeValidationContext().validate(lines);
    }

    private boolean isFunctionStructure(List<String> lines) throws CodeStandarException {
        getCodeValidationContext().setStandardValidator(new FunctionStructureValidator(getCodeValidationContext()));
        return getCodeValidationContext().validate(lines);
    }

    private boolean isOrganizationalStructure(List<String> lines) throws CodeStandarException {
        getCodeValidationContext()
                .setStandardValidator(new OrganizationalStructureValidator(getCodeValidationContext()));
        return getCodeValidationContext().validate(lines);
    }

    public void setCodeValidationContext(CodeValidationContext codeValidationContext) {
        setCodeValidationContext(codeValidationContext);
    }
}
