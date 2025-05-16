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
