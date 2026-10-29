package app;

import java.util.Scanner;

import controlador.CatalogoController;
import vista.VistaConsola;

/**
 * Punto de entrada del sistema. Ensambla el patrón MVC:
 * Modelo (paquete modelo) - Vista (VistaConsola) - Controlador (CatalogoController).
 */
public class PruebaAudioVisual {
    public static void main(String[] args) {
        System.out.println("Hello from Eclipse!");
        CatalogoController controlador = new CatalogoController();
        controlador.cargarDatosDeEjemplo();

        try (Scanner entrada = new Scanner(System.in)) {
            new VistaConsola(controlador, entrada).iniciar();
        }
    }
}
