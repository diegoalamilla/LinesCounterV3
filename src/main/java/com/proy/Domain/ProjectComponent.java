package com.proy.Domain;

/**
 * Interfaz para representar componentes de un proyecto que pueden ser analizados en términos
 * de líneas físicas y lógicas de código.
 */
public interface ProjectComponent {

    int getPhysicalLineCount();
    int getLogicalLineCount();
    void printResults();

}
