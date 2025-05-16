package com.proy.Domain;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import com.proy.Infrastructure.FileWriterUtil;

/**
 * La clase {@code ProjectRefactor} permite realizar una refactorización automática de archivos fuente Java,
 * dividiendo líneas de código que superan una longitud máxima, sin alterar el contenido de cadenas literales.
 * 
 * El objetivo principal es mejorar la legibilidad del código, dividiendo líneas extensas en múltiples líneas
 * respetando símbolos sintácticos comunes.
 * 
 * Las líneas se dividen solo fuera de literales de cadenas, respetando la indentación original.
 */
public class ProjectRefactor {

    private static final int MAX_LINE_LENGTH = 80;
    private static final char[] SPLIT_SYMBOLS = { '+', '(', ')', '=', '{', '}', ',', ';' };

    /**
     * Refactoriza un archivo o todos los archivos .java dentro de un directorio especificado.
     *
     * @param path Ruta del archivo o directorio a refactorizar.
     * @throws IOException Si ocurre un error al leer o escribir archivos.
     */
    public void refactorPath(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (var stream = Files.walk(path)) {
                stream.filter(Files::isRegularFile)
                      .filter(p -> p.toString().endsWith(".java"))
                      .forEach(p -> {
                          try {
                              refactorFile(p);
                          } catch (IOException e) {
                              throw new UncheckedIOException(e);
                          }
                      });
            }
        } else if (Files.isRegularFile(path) && path.toString().endsWith(".java")) {
            refactorFile(path);
        }
    }

    /**
     * Refactoriza un archivo Java dividiendo líneas largas.
     *
     * @param filePath Ruta del archivo a procesar.
     * @throws IOException Si ocurre un error al leer o escribir el archivo.
     */
    private void refactorFile(Path filePath) throws IOException {
        List<String> original = Files.readAllLines(filePath);
        List<String> refactored = new ArrayList<>(original.size());
        for (String line : original) {
            refactored.addAll(refactorLine(line));
        }
        FileWriterUtil.writeLinesToFile(filePath, refactored);
    }

    /**
     * Divide una línea en múltiples líneas si excede la longitud máxima permitida.
     *
     * @param line Línea original de código.
     * @return Lista de líneas resultantes después de la refactorización.
     */
    private List<String> refactorLine(String line) {
        if (line.length() <= MAX_LINE_LENGTH) {
            return List.of(line);
        }

        boolean[] inString = buildInStringMap(line);
        List<String> parts = new ArrayList<>();
        String indent = extractIndent(line);
        String remainder = line;

        while (remainder.length() > MAX_LINE_LENGTH) {
            int splitPos = findSplitPosition(remainder, inString);
            String head = remainder.substring(0, splitPos + 1);
            parts.add(head);
            String tail = remainder.substring(splitPos + 1);
            remainder = indent + tail;
            inString = buildInStringMap(remainder);
        }

        parts.add(remainder);
        return parts;
    }

    /**
     * Crea un mapa booleano indicando si cada carácter está dentro de un literal de cadena.
     *
     * @param s Línea a analizar.
     * @return Arreglo de booleanos donde {@code true} indica que el carácter está dentro de una cadena.
     */
    private boolean[] buildInStringMap(String s) {
        boolean[] inString = new boolean[s.length()];
        boolean inside = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"' && (i == 0 || s.charAt(i - 1) != '\\')) {
                inside = !inside;
            }
            inString[i] = inside;
        }
        return inString;
    }

    /**
     * Encuentra una posición adecuada para dividir una línea, fuera de literales de cadena,
     * y preferentemente dentro del límite máximo de longitud.
     *
     * @param s Línea a analizar.
     * @param inString Mapa que indica los caracteres dentro de cadenas.
     * @return Índice donde se puede dividir la línea.
     */
    private int findSplitPosition(String s, boolean[] inString) {
        int len = s.length();
        int backStart = Math.min(len, MAX_LINE_LENGTH) - 1;
        for (int i = backStart; i >= 0; i--) {
            if (!inString[i] && isSplitSymbol(s.charAt(i))) {
                return i;
            }
        }
        for (int i = MAX_LINE_LENGTH; i < len; i++) {
            if (!inString[i] && isSplitSymbol(s.charAt(i))) {
                return i;
            }
        }
        return MAX_LINE_LENGTH - 1;
    }

    /**
     * Verifica si un carácter es un símbolo válido para dividir la línea.
     *
     * @param c Carácter a verificar.
     * @return {@code true} si el carácter es un símbolo de división válido.
     */
    private boolean isSplitSymbol(char c) {
        for (char sym : SPLIT_SYMBOLS) {
            if (c == sym) return true;
        }
        return false;
    }

    /**
     * Extrae la indentación (espacios o tabulaciones al inicio) de una línea.
     *
     * @param line Línea de la cual extraer la indentación.
     * @return Cadena con los espacios iniciales de la línea.
     */
    private String extractIndent(String line) {
        int i = 0;
        while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
            i++;
        }
        return line.substring(0, i);
    }
}
