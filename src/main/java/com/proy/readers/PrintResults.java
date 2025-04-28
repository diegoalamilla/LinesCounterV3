package com.proy.readers;

import com.proy.model.CodeSegment;
import com.proy.model.Directory;

import java.util.List;

/**
 * La clase se encarga de imprimir en consola los resultados del conteo de
 * líneas físicas y lógicas de código
 * de archivos dentro de un directorio y sus subdirectorios o de un archivo
 * individual.
 *
 * @version 2.2
 */
public class PrintResults {

    /**
     * Imprime en consola los resultados de un directorio y sus subdirectorios.
     * 
     * @param directory Objeto Directory que contiene los resultados
     *                  del conteo de líneas a imprimir.
     */
    public static void printResultsByDirectory(Directory directory) {
        if (!directory.getCodeSegments().isEmpty() || !directory.getDirectories().isEmpty()) {
            showHeaderData();
            showDirectoryResults(directory);
        }
    }

    /**
     * Imprime en consola los resultados de un archivo individual.
     *
     * @param codeSegment Objeto CodeSegment que contiene los resultados
     *                    del conteo de líneas a imprimir.
     */
    public static void printResultsByFile(CodeSegment codeSegment) {
        if (codeSegment.getTitle() != null) {
            showHeaderData();
            showDataByFile(codeSegment.getTitle(), codeSegment);
        }
    }

    /**
     * Muestra en consola los resultados de un directorio y sus subdirectorios.
     *
     * @param directory Objeto Directory que contiene los resultados
     *                  del conteo de líneas a imprimir.
     */
    private static void showDirectoryResults(Directory directory) {
        List<CodeSegment> codeSegments = directory.getCodeSegments();

        if (!codeSegments.isEmpty()) {
            boolean isFirst = true;

            for (CodeSegment segment : codeSegments) {
                showDataByFile(isFirst ? directory.getName() : " ", segment);
                isFirst = false;
            }

            System.out.printf("%-35s %-40s %-20s %-20s %-20s%n", "Total", "", "", "",
                    directory.getTotalPhysicalLines());
        }

        directory.getDirectories().forEach(PrintResults::showDirectoryResults);
    }

    /**
     * Muestra en consola el encabezado de la tabla de resultados.
     */
    private static void showHeaderData() {
        System.out.println("-".repeat(140));
        System.out.printf("%-35s %-40s %-20s %-20s %-20s%n", "Programa", "Archivo", "Tipo", "Métodos",
                "Líneas físicas");
        System.out.println("-".repeat(140));
    }

    /**
     * Muestra en consola los resultados de un archivo individual.
     *
     * @param programName Nombre del programa que contiene el archivo.
     * @param codeSegment Objeto CodeSegment que contiene los resultados.
     */
    private static void showDataByFile(String programName, CodeSegment codeSegment) {
        String typeOfFile = codeSegment.isAClass() ? "Clase" : "Otro";
        System.out.printf("%-35s %-40s %-20s %-20s %-20s%n", programName,
                codeSegment.getTitle(), typeOfFile, codeSegment.getNumMethods(),
                codeSegment.getPhysicalLines());
    }
}
