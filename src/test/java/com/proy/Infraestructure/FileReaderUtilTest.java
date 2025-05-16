package com.proy.Infraestructure;

import com.proy.Infrastructure.FileReaderUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class FileReaderUtilTest {
    
    private FileReaderUtil fileReaderUtil;
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
        fileReaderUtil = new FileReaderUtil();
        
        
        testFile = new File("testFile.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Primera línea\n");
            writer.write("Segunda línea\n");
            writer.write("Tercera línea\n");
        }
        
        emptyFile = new File("emptyFile.txt");
        emptyFile.createNewFile();
        
        nonExistentFile = new File("nonExistentFile.txt");
    }

    /**
     * Prueba la lectura de un archivo con contenido.
     */
    @Test
    public void testReadFileLines_WithContent() {
        List<String> lines = fileReaderUtil.readFileLines(testFile);
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
        List<String> lines = fileReaderUtil.readFileLines(emptyFile);
        assertNotNull(lines);
        assertTrue(lines.isEmpty());
    }

    /**
     * Prueba la lectura de un archivo que no existe.
     */
    @Test
    public void testReadFileLines_NonExistentFile() {
        List<String> lines = fileReaderUtil.readFileLines(nonExistentFile);
        assertNotNull(lines);
        assertTrue(lines.isEmpty());
    }
}
