package com.proy.Infraestructure;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.proy.Infrastructure.FileWriterUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase FileWriterUtil.
 * Se valida el correcto funcionamiento de la escritura en archivos,
 * la creación de directorios padres y el manejo de errores.
 */
public class FileWriterUtilTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Prueba que verifica que se escriben correctamente varias líneas en un archivo.
     * Verifica que:
     * - El archivo se haya creado.
     * - El contenido del archivo coincida con las líneas proporcionadas.
     */
    @Test
    public void testWriteLinesToFile() throws IOException {
        List<String> lines = Arrays.asList("Línea 1", "Línea 2", "Línea 3");

        Path testFilePath = tempFolder.newFolder("testDir").toPath().resolve("testFile.txt");
        FileWriterUtil.writeLinesToFile(testFilePath, lines);
        assertTrue("El archivo debería existir", Files.exists(testFilePath));

        List<String> readLines = Files.readAllLines(testFilePath);
        assertEquals("El número de líneas no coincide", lines.size(), readLines.size());
        assertArrayEquals("El contenido de las líneas no coincide", lines.toArray(), readLines.toArray());
    }

    /**
     * Prueba que verifica que se lanza una excepción al intentar escribir en una ruta inválida.
     * En este caso, se intenta escribir en un directorio en lugar de un archivo.
     * Se espera una IOException.
     */
    @Test(expected = IOException.class)
    public void testWriteLinesToFileWithInvalidPath() throws IOException {
        Path directoryPath = tempFolder.newFolder("existingDir").toPath();
        List<String> lines = Arrays.asList("Esta escritura debería fallar");
        FileWriterUtil.writeLinesToFile(directoryPath, lines);
    }

    /**
     * Prueba que verifica que el método crea correctamente los directorios padres
     * si estos no existen al momento de escribir el archivo.
     */
    @Test
    public void testWriteLinesToFileCreatesParentDirectories() throws IOException {
        Path deepPath = tempFolder.getRoot().toPath()
                .resolve("dir1/dir2/dir3/testFile.txt");

        List<String> lines = Arrays.asList("Contenido de prueba");

        FileWriterUtil.writeLinesToFile(deepPath, lines);

        assertTrue("Los directorios padres deberían existir", Files.exists(deepPath.getParent()));
        assertTrue("El archivo debería existir", Files.exists(deepPath));
    }
}
