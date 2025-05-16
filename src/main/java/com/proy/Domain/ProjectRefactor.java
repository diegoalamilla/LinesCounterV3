package com.proy.Domain;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import com.proy.Infrastructure.FileWriterUtil;

public class ProjectRefactor {

    private static final int MAX_LINE_LENGTH = 80;
    private static final char[] SPLIT_SYMBOLS = { '+', '(', ')', '=', '{', '}', ',', ';' };

    /**
     * Refactoriza in-place el archivo o todos los .java de un directorio.
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

    private void refactorFile(Path filePath) throws IOException {
        List<String> original = Files.readAllLines(filePath);
        List<String> refactored = new ArrayList<>(original.size());
        for (String line : original) {
            refactored.addAll(refactorLine(line));
        }
        FileWriterUtil.writeLinesToFile(filePath, refactored);
    }

    private List<String> refactorLine(String line) {
        if (line.length() <= MAX_LINE_LENGTH) {
            return List.of(line);
        }

        // Precomputamos para cada índice si estamos dentro de una cadena
        boolean[] inString = buildInStringMap(line);

        List<String> parts = new ArrayList<>();
        String indent = extractIndent(line);
        String remainder = line;

        while (remainder.length() > MAX_LINE_LENGTH) {
            int splitPos = findSplitPosition(remainder, inString);
            // cortamos justo *después* del símbolo
            String head = remainder.substring(0, splitPos + 1);
            parts.add(head);

            // preparamos el resto, conservando indentación
            String tail = remainder.substring(splitPos + 1);
            remainder = indent + tail;
            // volvemos a generar el mapa de comillas para la nueva "remainder"
            inString = buildInStringMap(remainder);
        }

        parts.add(remainder);
        return parts;
    }

    /**
     * Construye un array donde inString[i] == true si el carácter en i está
     * *dentro* de un literal de cadena "…".
     */
    private boolean[] buildInStringMap(String s) {
        boolean[] inString = new boolean[s.length()];
        boolean inside = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Toggle al encontrar " no escapada
            if (c == '"' && (i == 0 || s.charAt(i - 1) != '\\')) {
                inside = !inside;
            }
            inString[i] = inside;
        }
        return inString;
    }

    /**
     * Busca el primer símbolo seguro cercano al límite:
     * 1) retrocediendo desde MAX_LINE_LENGTH–1 hacia 0,
     * 2) si no hay ninguno, avanzando desde MAX_LINE_LENGTH hasta el final.
     * Siempre ignorando posiciones donde inString[pos] == true.
     */
    private int findSplitPosition(String s, boolean[] inString) {
        int len = s.length();
        int backStart = Math.min(len, MAX_LINE_LENGTH) - 1;

        // 1) hacia atrás
        for (int i = backStart; i >= 0; i--) {
            if (!inString[i] && isSplitSymbol(s.charAt(i))) {
                return i;
            }
        }
        // 2) hacia adelante
        for (int i = MAX_LINE_LENGTH; i < len; i++) {
            if (!inString[i] && isSplitSymbol(s.charAt(i))) {
                return i;
            }
        }
        // 3) fallback: en la columna 79
        return MAX_LINE_LENGTH - 1;
    }

    private boolean isSplitSymbol(char c) {
        for (char sym : SPLIT_SYMBOLS) {
            if (c == sym) return true;
        }
        return false;
    }

    private String extractIndent(String line) {
        int i = 0;
        while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
            i++;
        }
        return line.substring(0, i);
    }
}
