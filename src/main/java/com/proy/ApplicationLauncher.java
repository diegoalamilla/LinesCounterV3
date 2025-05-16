package com.proy;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

import com.proy.Application.ProgramAnalyzer;
import com.proy.Presentation.UserInputHandler;

/**
 * La clase {@code ApplicationLauncher} actúa como punto de entrada para la aplicación
 * de análisis de versiones de proyectos Java.
 *
 * <p>Solicita al usuario las rutas del proyecto original, el proyecto modificado
 * y el directorio donde se almacenará el reporte de diferencias.</p>
 *
 * <p>Después, delega el análisis de los proyectos a la clase {@link ProgramAnalyzer}.</p>
 */
public class ApplicationLauncher {
    /**
     * Método principal que inicia la ejecución del programa.
     *
     * <p>Este método guía al usuario mediante mensajes en consola para proporcionar las rutas
     * necesarias, e invoca el análisis de los proyectos especificados. Maneja errores
     * relacionados con archivos y excepciones generales, notificando al usuario cuando sea necesario.</p>
     *
     * @param args Argumentos de línea de comandos (no utilizados en esta aplicación).
     */
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