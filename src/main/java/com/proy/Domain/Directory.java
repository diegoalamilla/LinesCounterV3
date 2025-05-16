package com.proy.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase "Directory" es usada para representar los directorios que contienen archivos en el programa. Proporciona los getters y setters necesarios para acceder a los datos del conteo a través de los CodeSegment asignados al directorio
 * @version 2.1
 */
public class Directory {
    private List<CodeSegment> codeSegments;
    private List<Directory> directories;
    private String name;

    public Directory(String name) {
        this.name = name;
        this.codeSegments = new ArrayList<>();
        this.directories = new ArrayList<>();
    }

    /**
     * Agrega un segmento de código al directorio si tiene un título válido.
     *
     * @param codeSegment el segmento de código a agregar
     */
    public void addCodeSegment(CodeSegment codeSegment) {
        if (codeSegment.getTitle() != null){
            codeSegments.add(codeSegment);
        }
    }

    /**
     * Establece el nombre del directorio.
     *
     * @param name el nuevo nombre del directorio
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el nombre del directorio.
     *
     * @return el nombre del directorio
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene la lista de segmentos de código asociados al directorio.
     *
     * @return lista de objetos {@code CodeSegment}
     */
    public List<CodeSegment> getCodeSegments() {
        return codeSegments;
    }

    /**
     * Establece la lista de segmentos de código del directorio.
     *
     * @param codeSegments la nueva lista de segmentos de código
     */
    public void setCodeSegments(List<CodeSegment> codeSegments) {
        this.codeSegments = codeSegments;
    }

    /**
     * Obtiene la lista de subdirectorios contenidos dentro del directorio.
     *
     * @return lista de objetos {@code Directory}
     */
    public List<Directory> getDirectories() {
        return directories;
    }

    /**
     * Establece la lista de subdirectorios del directorio.
     *
     * @param directories la nueva lista de subdirectorios
     */
    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    /**
     * Calcula el total de líneas físicas de código contenidas en los
     * segmentos de código del directorio.
     *
     * @return total de líneas físicas de código
     */
    public int getTotalPhysicalLines() {
        int physicalLines = 0;
        for (CodeSegment codeSegment : codeSegments) {
            physicalLines += codeSegment.getPhysicalLines();
        }
        return physicalLines;
    }    
}