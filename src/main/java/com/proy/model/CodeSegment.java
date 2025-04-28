package com.proy.model;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.replace(".java", "");
    }

    public int getPhysicalLines() {
        return physicalLines;
    }

    public void setPhysicalLines(int physicalLines) {
        this.physicalLines = physicalLines;
    }

    public void addPhysicalLines(int lines) {
        setPhysicalLines(getPhysicalLines() + lines);
    }

    public void addPhysicalLine() {
        setPhysicalLines(getPhysicalLines() + 1);
    }

    public void incrementNumMethods() {
        this.numMethods++;
    }

    public int getNumMethods() {
        return this.numMethods;
    }

    public boolean isAClass() {
        return this.isAClass;
    }

    public void setIsAClass(boolean isAClass) {
        this.isAClass = isAClass;
    }
}
