package com.proy.Domain.exceptions;
/**
 * Excepción personalizada que se lanza cuando un código fuente no cumple
 * con los estándares o convenciones esperadas definidos en el sistema.
 *
 *
 * 
 * @version 3.0
 */
public class CodeStandarException extends Exception {

    /**
     * Crea una nueva instancia de {@code CodeStandarException} con un mensaje descriptivo.
     *
     * @param message el mensaje que describe la causa específica de la excepción
     */
    public CodeStandarException(String message) {
        super(message);
    }
}

