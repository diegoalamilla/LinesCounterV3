package com.proy.Infrastructure;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code ReaderFile} proporciona métodos para leer
 * las líneas de texto de un archivo y devolverlas como una lista de cadenas.
 *
 * @version 1.0
 */
public class FileReaderUtil {

    /**
     * Lee todas las líneas de un archivo y las devuelve como una lista de cadenas.
     *
     * @param file El archivo {@code File} del cual se desea leer el contenido.
     * @return Una lista de cadenas, donde cada elemento representa una línea del archivo.
     */
    public List<String> readFileLines(File file) {
        List<String> lines = getLinesOfCode(file);
        return lines;
    }

    /**
     * Método auxiliar que realiza la lectura línea por línea del archivo
     * y almacena cada línea en una lista.
     *
     * @param file El archivo {@code File} del cual se van a leer las líneas.
     * @return Una lista de cadenas con el contenido del archivo línea por línea.
     */
    private List<String> getLinesOfCode(File file){
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + file.getName());
        }
        return lines;
    }
}