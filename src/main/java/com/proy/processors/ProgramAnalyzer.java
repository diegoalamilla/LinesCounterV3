package com.proy.processors;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.proy.files.FileCounter;
import com.proy.model.Directory;
import com.proy.readers.DirectoryFileCounter;
import com.proy.readers.PrintResults;

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
     * @param path Ruta del archivo o directorio a procesar.
     */
    public void analyzeProgram(Path path) throws FileNotFoundException {
        if (Files.isDirectory(path)) {
            processDirectory(path);
        } else if (Files.isRegularFile(path)) {
            processFile(path);
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
        DirectoryFileCounter directoryFileCounter = new DirectoryFileCounter(new File(directoryFile.toString()));
        Directory directory = directoryFileCounter.countLinesInDirectory();
        PrintResults.printResultsByDirectory(directory);
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
        FileCounter fileCounter = new FileCounter(new File(file.toString()));
        fileCounter.countLinesInFile();
        PrintResults.printResultsByFile(fileCounter.getCodeSegment());
    }
}
