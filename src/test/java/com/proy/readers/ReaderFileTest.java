package com.proy.readers;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ReaderFileTest {
    
    private ReaderFile readerFile;
    private File testFile;
    private File emptyFile;
    private File nonExistentFile;

    /**
     * Simular un archivo de prueba con contenido
     * Simular un archivo de prueba vacio
     * Simular un archivo de prueba inexistente
     */
    @Before
    public void setUp() throws IOException {
        readerFile = new ReaderFile();
        
        
        testFile = new File("testFile.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Primera línea\n");
            writer.write("Segunda línea\n");
            writer.write("Tercera línea\n");
        }
        
        // Crear un archivo vacío
        emptyFile = new File("emptyFile.txt");
        emptyFile.createNewFile();
        
        // Archivo inexistente
        nonExistentFile = new File("nonExistentFile.txt");
    }

    /**
     * Prueba la lectura de un archivo con contenido.
     */
    @Test
    public void testReadFileLines_WithContent() {
        List<String> lines = readerFile.readFileLines(testFile);
        assertNotNull(lines);
        assertEquals(3, lines.size());
        assertEquals("Primera línea", lines.get(0));
        assertEquals("Segunda línea", lines.get(1));
        assertEquals("Tercera línea", lines.get(2));
    }

    /**
     * Prueba la lectura de un archivo vacío.
     */
    @Test
    public void testReadFileLines_EmptyFile() {
        List<String> lines = readerFile.readFileLines(emptyFile);
        assertNotNull(lines);
        assertTrue(lines.isEmpty());
    }

    /**
     * Prueba la lectura de un archivo que no existe.
     */
    @Test
    public void testReadFileLines_NonExistentFile() {
        List<String> lines = readerFile.readFileLines(nonExistentFile);
        assertNotNull(lines);
        assertTrue(lines.isEmpty());
    }
}
