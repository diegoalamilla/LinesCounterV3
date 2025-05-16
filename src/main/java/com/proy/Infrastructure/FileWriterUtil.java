package com.proy.Infrastructure;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * La clase {@code FileWriterUtil} proporciona una utilidad para escribir una lista de líneas
 * de texto en un archivo en el sistema de archivos.
 * 
 * Esta clase garantiza que el directorio padre del archivo exista antes de escribir,
 * y gestiona automáticamente el cierre del recurso mediante un bloque try-with-resources.
 */
public class FileWriterUtil {
        /**
        * Escribe una lista de líneas en un archivo de texto especificado.
        * 
        * <p>Si el directorio contenedor del archivo no existe, será creado automáticamente.
        * 
        * @param filePath Ruta del archivo en el que se escribirán las líneas.
        * @param lines Lista de líneas de texto a escribir en el archivo.
        * @throws IOException Si ocurre un error al crear el directorio o escribir en el archivo.
        */
        public static void writeLinesToFile(Path filePath, List<String> lines) throws IOException {
            Files.createDirectories(filePath.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                for (String line : lines) {
                    if (line != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }
    }
