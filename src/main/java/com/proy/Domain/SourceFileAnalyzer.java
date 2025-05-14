package com.proy.Domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.proy.Domain.exceptions.CodeStandarException;
import com.proy.Infrastructure.FileReaderUtil;
import com.proy.Domain.validator.validatorContext.CodeValidationContext;
import com.proy.Domain.validator.validatorControlers.StructureCountValidator;

/**
 * La clase "FileCounter" proporciona los métodos que se necesitan para empezar
 * el conteo de lineas y validación de un archivo
 * 
 * @version 1.2
 */

public class SourceFileAnalyzer {

    private File file;
    private CodeSegment codeSegment;
    private List<String> fileContent;

    public SourceFileAnalyzer(File file) {
        this.file = file;
        this.fileContent = new FileReaderUtil().readFileLines(file);
        this.codeSegment = new CodeSegment();
    }

    /**
     * Llama al CodeValidationContext para el conteo de líneas físicas y lógicas
     * siempre y cuando sea válido el formato
     * Instanciando el primer validador de FileStructureValidator que solo permite
     * estructuras de organización y de estructuras de definición
     */
    public void countLinesInFile() {

        if (!SourceFileAnalyzer.isJavaFile(file)) {
            System.out.println(this.file.getName() + " no es un archivo con extensión válida");
            return;
        }

        int initialLines = fileContent.size();
        List<String> fileContent = new ArrayList<>(this.fileContent); 

        try {
            validateAndSetCodeSegment(fileContent);
        } catch (CodeStandarException e) {
            int errorLine = initialLines - fileContent.size() + 1;
            System.err.println(file.getName()+ ":" + e.getMessage() + "en la línea: " + errorLine);
        } catch (Exception e) {
        }
    }

    /**
     * Genera el contexto para la validación de las líneas del archivo y lo valida 
     * 
     * @param lines las líneas del archivo
     * @throws CodeStandarException si el archivo no cumple con el estándar
     */
    private void validateAndSetCodeSegment(List<String> lines) throws CodeStandarException {
        CodeValidationContext codeValidationContext = new CodeValidationContext();
        codeValidationContext.setStandardValidator(new StructureCountValidator(codeValidationContext));
        codeValidationContext.validate(lines);

        this.codeSegment = codeValidationContext.getCodeSegment();
        this.codeSegment.setTitle(file.getName());
        this.codeSegment.setIsAClass(containsAClass());
    }

    /**
     * Verifica si un archivo es un archivo Java (.java)
     * 
     * @param file el archivo a verificar
     * @return {@code true} si el archivo es un archivo Java, {@code false} en caso
     *         contrario.
     */
    public static boolean isJavaFile(File file) {
        return file.isFile() && file.getName().endsWith(".java");
    }

    /**
     * Verifica si el archivo contiene una clase
     * @return {@code true} si el archivo contiene una clase, {@code false} en caso
     *         contrario.
     */
    public boolean containsAClass() {
        String classRegex = "\\s*(public\\s+)?((abstract|final)\\s+)?class\\s+.*";

        for (String lineOfFile : fileContent) {
            if (lineOfFile.matches(classRegex)){
                return true;
            }
        }

        return false;
    }

    /**
     * Devuelve el segmento de código del archivo
     * 
     * @return el segmento de código del archivo
     */
    public CodeSegment getCodeSegment() {
        return this.codeSegment;
    }
}
