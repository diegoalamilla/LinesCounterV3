package com.proy.Domain;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProjectComparator {



    public void compareFiles(Path originalFile, Path modifiedFile, Path reportRoot) throws IOException {
        List<String> originalLines = Files.readAllLines(originalFile);
        List<String> modifiedLines = Files.readAllLines(modifiedFile);

        int origIdx = 0, modIdx = 0;
        int origSize = originalLines.size();
        int modSize = modifiedLines.size();

        Path reportFile = reportRoot.resolve(originalFile.getFileName());
        Files.createDirectories(reportFile.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(reportFile)) {
            while (origIdx < origSize || modIdx < modSize) {
                String origLine = origIdx < origSize ? originalLines.get(origIdx) : null;
                String modLine = modIdx < modSize ? modifiedLines.get(modIdx) : null;

                if (origLine != null && modLine != null && origLine.equals(modLine)) {
                    writer.write(origLine);
                    writer.newLine();
                    origIdx++;
                    modIdx++;
                } else if (origLine != null && (modLine == null || !modifiedLines.subList(modIdx, modSize).contains(origLine))) {
                    writer.write("//erased");
                    writer.newLine();
                    origIdx++;
                } else if (modLine != null && (origLine == null || !originalLines.subList(origIdx, origSize).contains(modLine))) {
                    writer.write(modLine + " //added");
                    writer.newLine();
                    modIdx++;
                } else {
                    // Si ambas líneas son diferentes, se consideran ambas: una borrada y una añadida
                    writer.write("//erased");
                    writer.newLine();
                    writer.write(modLine + " //added");
                    writer.newLine();
                    origIdx++;
                    modIdx++;
                }
            }
        }
    }




}

