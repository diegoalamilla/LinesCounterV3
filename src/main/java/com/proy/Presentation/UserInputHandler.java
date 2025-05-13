package com.proy.Presentation;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * La clase HandleInput gestiona la obtención de una ruta valida de un
 * directorio o archivo
 * 
 * @version 2.0
 */
public class UserInputHandler {

    /**
     * Solicita al usuario la ruta de la carpeta o archivo java por analizar
     * 
     * @throws FileNotFoundException Si no se encuentra el archivo o directorio
     *                              especificado.
     */
    public static Path requestValidPath(Scanner scanner) throws FileNotFoundException {
        Path path = Paths.get(askForFilepath(scanner));

        if (path.toFile().exists()) {
            return path;
        }

        throw new InvalidPathException(path.toString(), "No se encontró el archivo o directorio especificado.");

    }

    /**
     * Solicita al usuario que ingrese una ruta de archivo o directorio por consola.
     * 
     * @return La ruta ingresada por el usuario.
     */
    private static String askForFilepath(Scanner scanner) {
        System.out.print("Introduce la ruta del archivo o directorio: ");
        return scanner.nextLine();
    }
}
