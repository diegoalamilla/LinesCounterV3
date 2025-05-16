package com.proy.Domain;

/**
 * La clase "CodeSegment" representa un segmento de código que tienen líneas
 * lógicas y físicas y guarda la cantidad de líneas físicas y lógicas contadas
 * en ese segmento
 * 
 * @version 2.0
 */

public class CodeSegment {
    private int physicalLines;
    private String title;
    private int numMethods;
    private boolean isAClass;

    public CodeSegment() {
        this.physicalLines = 0;
        this.numMethods = 0;
        this.isAClass = false;
    }

    public CodeSegment(int physicalLines, int numMethods, boolean isAClass) {
        this.physicalLines = physicalLines;
        this.numMethods = numMethods;
        this.isAClass = isAClass;
    }

    /**
     * Obtiene el título del segmento.
     *
     * @return el título del segmento sin la extensión {@code .java}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título del segmento, eliminando la extensión {@code .java} si está presente.
     *
     * @param title el nombre del archivo
     */
    public void setTitle(String title) {
        this.title = title.replace(".java", "");
    }

    /**
     * Obtiene el número actual de líneas físicas.
     *
     * @return número de líneas físicas
     */
    public int getPhysicalLines() {
        return physicalLines;
    }

    /**
     * Establece el número de líneas físicas.
     *
     * @param physicalLines número de líneas físicas
     */
    public void setPhysicalLines(int physicalLines) {
        this.physicalLines = physicalLines;
    }

    /**
     * Agrega un número específico de líneas físicas al total actual.
     *
     * @param lines número de líneas a agregar
     */
    public void addPhysicalLines(int lines) {
        setPhysicalLines(getPhysicalLines() + lines);
    }

    /**
     * Incrementa en uno el número de líneas físicas.
     */
    public void addPhysicalLine() {
        setPhysicalLines(getPhysicalLines() + 1);
    }

    
    /**
     * Incrementa en uno el número de métodos dentro del segmento.
     */
    public void incrementNumMethods() {
        this.numMethods++;
    }

    /**
     * Obtiene el número de métodos encontrados en el segmento.
     *
     * @return número de métodos
     */
    public int getNumMethods() {
        return this.numMethods;
    }

    /**
     * Indica si el segmento representa una clase.
     *
     * @return {@code true} si es una clase, {@code false} en caso contrario
     */
    public boolean isAClass() {
        return this.isAClass;
    }

    /**
     * Establece si el segmento representa una clase.
     *
     * @param isAClass {@code true} si representa una clase, {@code false} en caso contrario
     */
    public void setIsAClass(boolean isAClass) {
        this.isAClass = isAClass;
    }
}
