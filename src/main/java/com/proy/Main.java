package com.proy;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.proy.processors.ProgramAnalyzer;
import com.proy.readers.HandleInput;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            new ProgramAnalyzer().analyzeProgram(HandleInput.getInput(scanner));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado.");
        }

        System.out.println("\nPresiona Enter para salir...");
        scanner.nextLine();
        scanner.close();

    }
}