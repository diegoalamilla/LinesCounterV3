package com.proy.Application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.proy.Domain.DirectoryAnalyzer;
import com.proy.Domain.ProjectComparator;
import com.proy.Domain.ProjectRefactor;
import com.proy.Domain.SourceFileAnalyzer;
import com.proy.Domain.Directory;
import com.proy.Presentation.CountingResultsPrinter;

/**
 * La clase ProgramProcessor procesa un archivo o directorio dado,
 * contando las líneas de código de los archivos contenidos y mostrando los
 * resultados.
 * 
 * @version 1.0
 */

public class ProgramAnalyzer {

    /**
     * Procesa una ruta dada, ya sea archivo o directorio.
     * Si es un directorio, cuenta las líneas de todos los archivos dentro.
     * Si es un archivo, cuenta las líneas físicas y lógicas.
     * 
    // * @param path Ruta del archivo o directorio a procesar.
     */
    public void analyzePrograms(Path originalProjectPath, Path modifiedProjectPath, Path reportGenerationPath) throws FileNotFoundException {

        if (Files.isDirectory(originalProjectPath)) {
            processDirectory(originalProjectPath);
        } else if (Files.isRegularFile(originalProjectPath)) {
            processFile(originalProjectPath);
        }

        if (Files.isDirectory(modifiedProjectPath)) {
            processDirectory(modifiedProjectPath);
        } else if (Files.isRegularFile(modifiedProjectPath)) {
            processFile(modifiedProjectPath);
        }

        //nueva instancia
        
        ProjectComparator pc = new ProjectComparator(
            originalProjectPath, 
            modifiedProjectPath, 
            reportGenerationPath, 
            originalProjectPath,
            modifiedProjectPath, 
            reportGenerationPath.resolve(originalProjectPath.getFileName().toString()), 
            reportGenerationPath.resolve(modifiedProjectPath.getFileName().toString()));
        try {
            pc.compareProjects();
            pc.writeGlobalSummary(reportGenerationPath);
        } catch (Exception e) {
            System.out.println("Error al comparar los archivos: " + e.getMessage());
        }

        ProjectRefactor ref = new ProjectRefactor();
        try {
            System.out.println(reportGenerationPath.resolve(modifiedProjectPath.getFileName().toString()));
            ref.refactorPath(reportGenerationPath.resolve(originalProjectPath.getFileName().toString()));
            ref.refactorPath(reportGenerationPath.resolve(modifiedProjectPath.getFileName().toString()));
        } catch (IOException e) {
            System.err.println("Error al refactorizar: " + e.getMessage());
        }
    }

    /**
     * Procesa un directorio dado, contando las líneas de código de los archivos
     * contenidos y mostrando los resultados.
     *
     * @param directoryFile El directorio a procesar.
     * @throws FileNotFoundException Si ocurre un error al acceder a los archivos
     *                               dentro del directorio.
     */
    private void processDirectory(Path directoryFile) throws FileNotFoundException {
        DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(new File(directoryFile.toString()));
        Directory directory = directoryAnalyzer.countLinesInDirectory();
        CountingResultsPrinter.printResultsByDirectory(directory);
    }

    /**
     * Procesa un archivo individual, contando las líneas físicas y lógicas del
     * código
     * y mostrando los resultados.
     *
     * @param file El archivo a procesar.
     * @throws FileNotFoundException Si ocurre un error al acceder al archivo.
     */
    private void processFile(Path file) throws FileNotFoundException {
        SourceFileAnalyzer sourceFileAnalyzer = new SourceFileAnalyzer(new File(file.toString()));
        sourceFileAnalyzer.countLinesInFile();
        CountingResultsPrinter.printResultsByFile(sourceFileAnalyzer.getCodeSegment());
    }
}
