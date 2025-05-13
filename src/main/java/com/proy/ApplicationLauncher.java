package com.proy;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

import com.proy.Application.ProgramAnalyzer;
import com.proy.Presentation.UserInputHandler;

public class ApplicationLauncher {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Bienvenido al programa de control de verrsiones...");

            System.out.println("Archivo Previo:");
            Path originalProjectPath = UserInputHandler.requestValidPath(scanner);

            System.out.println("Archivo Modificado:");
            Path modifiedProjectPath = UserInputHandler.requestValidPath(scanner);

            System.out.println("Directorio donde se guardará el reporte de cambios:");
            Path reportFilePath = UserInputHandler.requestValidPath(scanner);

            ProgramAnalyzer analyzer = new ProgramAnalyzer();
            analyzer.analyzePrograms(originalProjectPath, modifiedProjectPath, reportFilePath);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado.");
        }
        System.out.println("\nPresiona Enter para salir...");
        try {
            System.in.read();
        } catch (Exception ignored) {}

    }
}