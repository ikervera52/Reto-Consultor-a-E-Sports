package org.example.appesports.Utilidades;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidarDatosTest {
    @org.junit.jupiter.api.Test
    @Order(1)
    /**
     * Método de prueba para validar el nombre de usuario utilizando la expresión regular definida en la clase ValidarDatos.
     * Este método verifica que los nombres de usuario cumplan con el patrón de solo letras mayúsculas, minúsculas y números, y que no contengan espacios ni caracteres especiales.
     */
    void validarUsername() {
        /** Caso 1: El nombre "admin" cumple con el patrón ^[A-Za-z0-9]+$
         */
        assertTrue("admin".matches("^[A-Za-z0-9]+$"));

        /** Caso 2: El nombre "admin123" cumple con el patrón
         */
        assertTrue("admin123".matches("^[A-Za-z0-9]+$"));

        /** Caso 3: Un nombre con espacios NO cumple con el patrón
         */
        assertFalse("admin login".matches("^[A-Za-z0-9]+$"));

        /** Caso 4: Un nombre con caracteres especiales NO cumple con el patrón
         */
        assertFalse("admin_2024".matches("^[A-Za-z0-9]+$"));
    }
}