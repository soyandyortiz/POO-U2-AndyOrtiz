package vista;

import java.util.List;
import java.util.Scanner;

import controlador.CatalogoController;
import modelo.ContenidoAudiovisual;
import modelo.Temporada;

/**
 * VISTA (patrón MVC).
 * Muestra el menú por consola, lee los datos del usuario y delega toda
 * la lógica en el controlador.
 */
public class VistaConsola {
    private final CatalogoController controlador;
    private final Scanner entrada;

    public VistaConsola(CatalogoController controlador, Scanner entrada) {
        this.controlador = controlador;
        this.entrada = entrada;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ", 0, 15);
            try {
                ejecutar(opcion);
            } catch (IllegalArgumentException e) {
                System.out.println(">> Error: " + e.getMessage());
            }
        } while (opcion != 0);
        System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("=========== SISTEMA DE CONTENIDO AUDIOVISUAL ===========");
        System.out.println(" 1. Listar catálogo            9. Calificar contenido");
        System.out.println(" 2. Ver detalle por ID        10. Registrar reproducciones (video)");
        System.out.println(" 3. Registrar contenido       11. Editar título/duración/género");
        System.out.println(" 4. Registrar actor           12. Buscar por título");
        System.out.println(" 5. Registrar investigador    13. Eliminar contenido");
        System.out.println(" 6. Asignar actor             14. Ver actores e investigadores");
        System.out.println(" 7. Asignar investigador      15. Resumen del catálogo");
        System.out.println(" 8. Agregar temporada a serie  0. Salir");
        System.out.println("=========================================================");
    }

    private void ejecutar(int opcion) {
        switch (opcion) {
            case 1 -> listar(controlador.getCatalogo());
            case 2 -> controlador.obtenerPorId(leerEntero("ID del contenido: ", 0, Integer.MAX_VALUE)).mostrarDetalles();
            case 3 -> registrarContenido();
            case 4 -> {
                controlador.registrarActor(leerTexto("Nombre: "), leerTexto("Nacionalidad: "),
                        leerEntero("Año de debut: ", 1880, 2100));
                System.out.println(">> Actor registrado.");
            }
            case 5 -> {
                controlador.registrarInvestigador(leerTexto("Nombre: "), leerTexto("Especialidad: "),
                        leerTexto("Institución: "));
                System.out.println(">> Investigador registrado.");
            }
            case 6 -> {
                listarNumerado("Actores", controlador.getActores());
                controlador.asignarActor(leerEntero("ID de la película o video musical: ", 0, Integer.MAX_VALUE),
                        leerEntero("Número de actor: ", 1, Integer.MAX_VALUE));
                System.out.println(">> Actor asignado (agregación).");
            }
            case 7 -> {
                listarNumerado("Investigadores", controlador.getInvestigadores());
                controlador.asignarInvestigador(leerEntero("ID del documental o podcast: ", 0, Integer.MAX_VALUE),
                        leerEntero("Número de investigador: ", 1, Integer.MAX_VALUE));
                System.out.println(">> Investigador asignado (asociación).");
            }
            case 8 -> {
                Temporada t = controlador.agregarTemporada(leerEntero("ID de la serie: ", 0, Integer.MAX_VALUE),
                        leerEntero("Año de estreno: ", 1900, 2100), leerEntero("Número de episodios: ", 1, 500));
                System.out.println(">> Creada " + t + " (composición).");
            }
            case 9 -> {
                controlador.calificar(leerEntero("ID del contenido: ", 0, Integer.MAX_VALUE),
                        leerEntero("Estrellas (1-5): ", 1, 5));
                System.out.println(">> Calificación registrada.");
            }
            case 10 -> {
                controlador.registrarReproducciones(leerEntero("ID del video musical: ", 0, Integer.MAX_VALUE),
                        leerEntero("Reproducciones a sumar: ", 0, Integer.MAX_VALUE));
                System.out.println(">> Reproducciones actualizadas.");
            }
            case 11 -> {
                controlador.actualizarDatosBasicos(leerEntero("ID del contenido: ", 0, Integer.MAX_VALUE),
                        leerTexto("Nuevo título: "), leerEntero("Nueva duración (min): ", 1, 100000),
                        leerTexto("Nuevo género: "));
                System.out.println(">> Contenido actualizado.");
            }
            case 12 -> listar(controlador.buscarPorTitulo(leerTexto("Texto a buscar: ")));
            case 13 -> {
                ContenidoAudiovisual c = controlador.eliminarContenido(leerEntero("ID a eliminar: ", 0, Integer.MAX_VALUE));
                System.out.println(">> Eliminado: " + c.getTitulo()
                        + ". Actores e investigadores siguen registrados (agregación/asociación).");
            }
            case 14 -> {
                listarNumerado("Actores", controlador.getActores());
                listarNumerado("Investigadores", controlador.getInvestigadores());
            }
            case 15 -> mostrarResumen();
            default -> { /* 0 = salir */ }
        }
    }

    private void registrarContenido() {
        System.out.println("Tipo: 1) Película 2) Serie de TV 3) Documental 4) Video musical 5) Podcast");
        int tipo = leerEntero("Opción: ", 1, 5);
        String titulo = leerTexto("Título: ");
        int duracion = leerEntero("Duración (min): ", 1, 100000);
        String genero = leerTexto("Género: ");
        ContenidoAudiovisual nuevo = switch (tipo) {
            case 1 -> controlador.registrarPelicula(titulo, duracion, genero, leerTexto("Estudio: "));
            case 2 -> controlador.registrarSerie(titulo, duracion, genero);
            case 3 -> controlador.registrarDocumental(titulo, duracion, genero, leerTexto("Tema: "));
            case 4 -> controlador.registrarVideoMusical(titulo, duracion, genero, leerTexto("Artista: "),
                    leerTexto("Álbum: "), leerEntero("Año de lanzamiento: ", 1900, 2100));
            default -> controlador.registrarPodcast(titulo, duracion, genero, leerTexto("Anfitrión: "),
                    leerTexto("Plataforma: "), leerEntero("Número de episodio: ", 1, 100000));
        };
        System.out.println(">> Registrado: " + nuevo);
    }

    private void mostrarResumen() {
        System.out.println("---- Resumen del catálogo ----");
        for (String tipo : new String[] { "Película", "Serie de TV", "Documental", "Video musical", "Podcast" }) {
            System.out.printf("%-14s: %d%n", tipo, controlador.contarPorTipo(tipo));
        }
        System.out.println("Total de contenidos : " + controlador.getCatalogo().size());
        System.out.println("Duración total (min): " + controlador.getDuracionTotal());
        ContenidoAudiovisual mejor = controlador.getMejorCalificado();
        System.out.println("Mejor calificado    : " + (mejor == null ? "sin calificaciones" : mejor));
    }

    private void listar(List<ContenidoAudiovisual> lista) {
        if (lista.isEmpty()) {
            System.out.println("(sin resultados)");
        }
        for (ContenidoAudiovisual c : lista) {
            System.out.println(c);
        }
    }

    private void listarNumerado(String titulo, List<?> lista) {
        System.out.println("---- " + titulo + " ----");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
    }

    // ---- lectura segura de datos ----
    private int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String linea = entrada.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // se vuelve a pedir el dato
            }
            System.out.println("   Valor inválido. Ingrese un número entre " + min + " y " + max + ".");
        }
    }

    private String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = entrada.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("   Este campo es obligatorio.");
        }
    }
}
