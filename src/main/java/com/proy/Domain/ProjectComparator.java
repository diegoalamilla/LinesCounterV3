package com.proy.Domain;

import com.proy.Infrastructure.FileWriterUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProjectComparator {

    private final Path reportDirectory;
    private final Path originalRootPath;
    private final Path modifiedRootPath;
    private Path originalFilePath;
    private Path modifiedFilePath;
    private  Path originalReportBase;
    private  Path modifiedReportBase;
    private  List<String> globalSummary = new ArrayList<>();
    private int globalErasedLinesCount = 0;
    private int globalAddedLinesCount = 0;
    private int globalModifiedLinesCount = 0;

    public ProjectComparator(
            Path originalFilePath,
            Path modifiedFilePath,
            Path reportDirectory,
            Path originalRootPath,
            Path modifiedRootPath,
            Path originalReportBase,
            Path modifiedReportBase
    ) {
        this.originalFilePath   = originalFilePath;
        this.modifiedFilePath   = modifiedFilePath;
        this.reportDirectory = reportDirectory;
        this.originalRootPath   = originalRootPath;
        this.modifiedRootPath   = modifiedRootPath;
        this.originalReportBase = originalReportBase;
        this.modifiedReportBase = modifiedReportBase;
    }

    /**
     * Compara archivos o directorios dependiendo de si la ruta de entrada es un archivo o carpeta.
     *
     * @throws IOException si ocurre un error al leer archivos.
     */
    public void compareProjects() throws IOException {
        if (originalFilePath.toFile().isFile()) {
            compareFiles();
        } else if (originalFilePath.toFile().isDirectory()) {
            compareDirectories();
        }
    }

    /**
     * Compara dos archivos línea por línea. Determina si las líneas son iguales,
     * borradas, añadidas o ligeramente modificadas, y genera reportes para ambas versiones.
     *
     * @throws IOException si ocurre un error al leer o escribir archivos.
     */
    private void compareFiles() throws IOException {
        List<String> originalLines = Files.readAllLines(this.originalFilePath);
        List<String> modifiedLines = Files.readAllLines(this.modifiedFilePath);
        List<String> reportLinesOriginal = new ArrayList<>();
        List<String> reportLinesModified = new ArrayList<>();
        boolean itDoesntExistInModifiedFile = false;
        int erasedLinesCounter = 0;
        int newLinesCounter = 0;
        int modifiedLinesCounter = 0;

        int originalIndex = 0;
        int modifiedIndex = 0;
        int originalLinesCount = originalLines.size();
        int modifiedLinesCount = modifiedLines.size();

        Path relativePath = originalRootPath.relativize(originalFilePath);
        String relativePathStr = relativePath.toString().replace("\\", "/");

        this.globalSummary.add(relativePathStr);

         while (originalIndex < originalLinesCount || modifiedIndex < modifiedLinesCount) {
            String originalLine = originalIndex < originalLinesCount ? originalLines.get(originalIndex) : null;
            String modifiedLine = modifiedIndex < modifiedLinesCount ? modifiedLines.get(modifiedIndex) : null;

            if (originalLine != null && modifiedLine != null && originalLine.equals(modifiedLine)) {
                reportLinesOriginal.add(originalLine);
                reportLinesModified.add(modifiedLine);
                originalIndex++;
                modifiedIndex++;
            } else if (originalLine != null 
            && (modifiedLine == null || modifiedLine.trim().isEmpty())) {
                String msg = "The line " + (originalIndex+1) + " was erased";
                this.globalSummary.add(msg);
                reportLinesOriginal.add(originalLine+" //erased");
                erasedLinesCounter++;
                originalIndex++;
                modifiedIndex++;
            } else if (modifiedLine != null
           && (originalLine == null || originalLine.trim().isEmpty())) {
                String msg = "The line " + (modifiedIndex+1) + " was added";
                this.globalSummary.add(msg);
                reportLinesModified.add(modifiedLine + " //added");
                newLinesCounter++;
                modifiedIndex++;
                originalIndex++;
            }else if (!modifiedLines.subList(modifiedIndex, modifiedLinesCount).contains(originalLine)){
                double similarityScore = calculateSimilarity(originalLine, modifiedLine);
                if (similarityScore > 0.7) {
                    String msg = "The line " + (modifiedIndex+1) + " was modified slightly, before it used to have: "+originalLine;
                    this.globalSummary.add(msg);
                    reportLinesModified.add(modifiedLine + " //modified");
                    reportLinesOriginal.add(originalLine);
                    modifiedLinesCounter++;
                    modifiedIndex++;
                    originalIndex++;
                } else {
                    String msg = "The line " + (originalIndex+1) + " was erased";
                    this.globalSummary.add(msg);
                    reportLinesOriginal.add(originalLine+" //erased");
                    erasedLinesCounter++;
                    originalIndex++;
                }
            }else if(!originalLines.subList(originalIndex, originalLinesCount).contains(modifiedLine)){
                String msg = "The line " + (modifiedIndex+1) + " was added";
                this.globalSummary.add(msg);
                reportLinesModified.add(modifiedLine + " //added");
                newLinesCounter++;
                modifiedIndex++;
                itDoesntExistInModifiedFile=true;
            }
            else {
                if (itDoesntExistInModifiedFile) {
                    String msg = "The line " + (originalIndex+1) + " was erased";
                    this.globalSummary.add(msg);
                    reportLinesOriginal.add(originalLine+" //erased");
                    erasedLinesCounter++;
                    originalIndex++;
                    itDoesntExistInModifiedFile=false;
                } else {
                    String msg = "The line " + (modifiedIndex+1) + " was added";
                    this.globalSummary.add(msg);
                    reportLinesModified.add(modifiedLine + " //added");
                    newLinesCounter++;   
                    originalIndex++;
                }
                
            }
        }

        Path originalReportPath = originalReportBase.resolve(relativePath);
        Path modifiedReportPath = modifiedReportBase.resolve(relativePath);

        this.globalSummary.add("\n The line modifications of: "+relativePathStr+
         " are the following: \n erased lines: "+erasedLinesCounter+
         "\n new lines "+newLinesCounter+" \n slightly modified  lines: "+
         modifiedLinesCounter+"\n");

        this.globalErasedLinesCount += erasedLinesCounter;
        this.globalAddedLinesCount += newLinesCounter;
        this.globalModifiedLinesCount += modifiedLinesCounter;

        FileWriterUtil.writeLinesToFile(originalReportPath, reportLinesOriginal);

        FileWriterUtil.writeLinesToFile(modifiedReportPath, reportLinesModified);
    }


    /**
     * Calcula el grado de similitud entre dos líneas utilizando la distancia de Levenshtein.
     * El resultado es un valor entre 0 y 1, donde 1 representa una igualdad exacta.
     *
     * @param originalLine Línea del archivo original.
     * @param modifiedLine Línea del archivo modificado.
     * @return porcentaje de similitud (de 0.0 a 1.0).
     */
    private double calculateSimilarity(String originalLine, String modifiedLine) {
        if (originalLine == null || modifiedLine == null
            || originalLine.isEmpty() || modifiedLine.isEmpty()) {
            return 0.0;
        }
        String trimmedSource = originalLine.trim();
        String trimmedTarget = modifiedLine.trim();

        int editDistance = computeLevenshteinDistance(trimmedSource, trimmedTarget);
        int longestLength = Math.max(trimmedSource.length(), trimmedTarget.length());
        if (longestLength == 0) {
            return 1.0;
        }
        return 1.0 - ((double) editDistance / longestLength);
    }

    /**
     * Calcula la distancia de Levenshtein entre dos cadenas, que representa el número mínimo
     * de operaciones (inserciones, eliminaciones o sustituciones) para convertir una en otra.
     *
     * @param originalLine Línea original.
     * @param modifiedLine Línea modificada.
     * @return distancia de Levenshtein.
     */
    private int computeLevenshteinDistance(String originalLine, String modifiedLine) {
        int originalLineLength = originalLine.length();
        int modifiedLineLength = modifiedLine.length();
        int[][] distanceMatrix = new int[originalLineLength + 1][modifiedLineLength + 1];

        for (int row = 0; row <= originalLineLength; row++) {
            distanceMatrix[row][0] = row;
        }
        for (int col = 0; col <= modifiedLineLength; col++) {
            distanceMatrix[0][col] = col;
        }
        for (int row = 1; row <= originalLineLength; row++) {
            for (int col = 1; col <= modifiedLineLength; col++) {
                boolean charsMatch = originalLine.charAt(row - 1) == modifiedLine.charAt(col - 1);
                int substitutionCost = charsMatch ? 0 : 1;

                int costDelete     = distanceMatrix[row - 1][col] + 1;
                int costInsert     = distanceMatrix[row][col - 1] + 1;
                int costSubstitute = distanceMatrix[row - 1][col - 1] + substitutionCost;

                distanceMatrix[row][col] = Math.min(
                    Math.min(costDelete, costInsert),
                    costSubstitute
                );
            }
        }

        return distanceMatrix[originalLineLength][modifiedLineLength];
    }

    /**
     * Compara dos directorios recursivamente, entrando en subdirectorios
     * y comparando archivos con extensión ".java".
     *
     * @throws IOException si ocurre un error al acceder al sistema de archivos.
     */
    private void compareDirectories() throws IOException {
        File[] directoryEntries = listDirectoryEntries(this.originalFilePath.toFile());

        for (File entry : directoryEntries) {
            Path entryOriginalPath = entry.toPath();
            Path entryModifiedPath  = Path.of(modifiedFilePath.toString(), entry.getName());
            Path entryOriginalReportBase = this.originalReportBase;
            Path entryModifiedReportBase = this.modifiedReportBase;
            ProjectComparator comparator = new ProjectComparator(
                entryOriginalPath,
                entryModifiedPath,
                reportDirectory,
                originalRootPath,
                modifiedRootPath,
                entryOriginalReportBase,
                entryModifiedReportBase
            );
            comparator.globalSummary = this.globalSummary;
            comparator.globalErasedLinesCount = this.globalErasedLinesCount;
            comparator.globalAddedLinesCount = this.globalAddedLinesCount;
            comparator.globalModifiedLinesCount = this.globalModifiedLinesCount;

            if (entry.isFile() && entry.getName().endsWith(".java")) {
                comparator.compareProjects();
                this.globalErasedLinesCount   = comparator.globalErasedLinesCount;
                this.globalAddedLinesCount    = comparator.globalAddedLinesCount;
                this.globalModifiedLinesCount = comparator.globalModifiedLinesCount;
            } else if (entry.isDirectory()) {
                comparator.compareProjects();
                this.globalErasedLinesCount   = comparator.globalErasedLinesCount;
                this.globalAddedLinesCount    = comparator.globalAddedLinesCount;
                this.globalModifiedLinesCount = comparator.globalModifiedLinesCount;
            }
        }
    }

    /**
     * Escribe el resumen global de las diferencias entre ambos proyectos en un archivo
     * llamado {@code modificationsSummary.txt}.
     *
     * @param reportGenerationPath Ruta donde se escribirá el resumen.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public void writeGlobalSummary(Path reportGenerationPath) throws IOException {
        String fileName = "modificationsSummary.txt";
        Path reportSummaryPath = reportGenerationPath.resolve(fileName);
        this.globalSummary.add("\n The global lines modifications are: \n erased lines: "
        + this.globalErasedLinesCount+ "\n new lines: " + this.globalAddedLinesCount
        + "\n slightly modified lines :" + this.globalModifiedLinesCount+"\n");
        FileWriterUtil.writeLinesToFile(reportSummaryPath, this.globalSummary);
        
    }

    /**
     * Lista todos los archivos y subdirectorios en un directorio dado.
     *
     * @param directory El directorio a listar.
     * @return un arreglo de {@code File} que contiene los elementos del directorio.
     */
    private File[] listDirectoryEntries(File directory) {
        return directory.listFiles() != null ? directory.listFiles() : new File[0];
    }
}
