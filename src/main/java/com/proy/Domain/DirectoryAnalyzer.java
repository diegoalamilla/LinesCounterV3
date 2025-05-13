package com.proy.Domain;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * La clase {@code DirectoryAnalyzer} se encarga de contar las líneas de
 * código en archivos Java dentro de un directorio y sus subdirectorios.
 * Recorre recursivamente todos los archivos y subdirectorios, utilizando
 * FileCounter para contar las líneas de cada archivo Java
 * 
 * @version 2.1
 */
public class DirectoryAnalyzer {
    private File directoryFile;

    /**
     * Constructor que inicializa el contador con el directorio especificado.
     * 
     * @param directoryFile el directorio raíz donde se buscarán los archivos Java.
     */
    public DirectoryAnalyzer(File directoryFile) {
        this.directoryFile = directoryFile;
    }

    /**
     * Recorre el directorio y cuenta las líneas de todos los archivos Java (.java)
     * encontrados, incluyendo los de subdirectorios.
     * 
     * @return Directory que contiene los segmentos de código y
     *         subdirectorios con sus respectivos conteos.
     * @throws FileNotFoundException si algún archivo no puede ser encontrado al
     *                               intentar contar sus líneas.
     */
    public Directory countLinesInDirectory() throws FileNotFoundException {
        Directory directory = new Directory(this.directoryFile.getName());
        File[] files = this.getFilesFromTheDirectory();
        
        for (File file : files) {
            if (file.isFile()) {
                directory.getCodeSegments().add(this.processFile(file));
            } else if (file.isDirectory()) {
                Directory subDirectory = this.processDirectory(file);
                directory.getDirectories().add(subDirectory);
            }
        }

        return directory;
    }


    /**
     * Procesa un archivo, contando las líneas de código
     * 
     * @param file el archivo a procesar.
     * @return CodeSegment con las líneas de código contadas.
     * @throws FileNotFoundException si el archivo no puede ser encontrado al
     *                               intentar contar sus líneas.
     */
    private CodeSegment processFile(File file) throws FileNotFoundException {
        SourceFileAnalyzer sourceFileAnalyzer = new SourceFileAnalyzer(file);
        sourceFileAnalyzer.countLinesInFile();
        return sourceFileAnalyzer.getCodeSegment();
    }

    /**
     * Procesa un subdirectorio, contando las líneas de código de los archivos
     * 
     * @param directory el subdirectorio a procesar.
     * @return Directory que contiene los segmentos de código y
     *         subdirectorios con sus respectivos conteos.
     * @throws FileNotFoundException si algún archivo no puede ser encontrado al
     *                               intentar contar sus líneas.
     */
    private Directory processDirectory(File directory) throws FileNotFoundException {
        DirectoryAnalyzer directoryAnalyzer = new DirectoryAnalyzer(directory);
        return directoryAnalyzer.countLinesInDirectory();
    }

    /**
     * Obtiene los archivos del directorio
     * 
     * @return arreglo de archivos del directorio
     */
    private File[] getFilesFromTheDirectory() {
        return this.directoryFile.listFiles() != null ? this.directoryFile.listFiles() : new File[0];
    }

}