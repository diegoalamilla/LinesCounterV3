package com.proy.readers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.proy.Presentation.UserInputHandler;
import org.junit.Test;

public class UserInputHandlerTest {
    @Test
    public void testGetInput_ValidFolderPath() throws FileNotFoundException {
        String simulatedInput = "src\\test\\resources\\validFolderPath";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Path result = UserInputHandler.requestValidPath(scanner);
        
        assertEquals(Paths.get(simulatedInput), result);
    }

    @Test
    public void testGetInput_ValidFilePath() throws FileNotFoundException {
        String simulatedInput = "src\\test\\resources\\validFolderPath\\validJavaFile.java";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Path result = UserInputHandler.requestValidPath(scanner);
        
        assertEquals(Paths.get(simulatedInput), result);
    }

    @Test
    public void testGetInput_invalidPath() {
        String simulatedInput = "src\\test\\resour";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream); 
        assertThrows(InvalidPathException.class, ()-> UserInputHandler.requestValidPath(scanner));
    }
}
