package pruebas;

import java.util.List;

import controlador.CatalogoController;
import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Pelicula;
import modelo.Podcast;
import modelo.SerieDeTV;
import modelo.VideoMusical;

/**
 * Pruebas automáticas del sistema (sin librerías externas).
 * Ejecutar como "Java Application": muestra OK/FALLA por cada caso.
 */
public class PruebasSistema {
    private static int aprobadas = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS DEL SISTEMA AUDIOVISUAL ===");

        // 1. Herencia y polimorfismo
        CatalogoController c = new CatalogoController();
        c.cargarDatosDeEjemplo();
        verificar("Catálogo de ejemplo con 5 contenidos", c.getCatalogo().size() == 5);
        boolean todosHeredan = true;
        for (ContenidoAudiovisual x : c.getCatalogo()) {
            todosHeredan &= x.obtenerDetalles().startsWith("Detalles (" + x.getTipo() + ")");
        }
        verificar("Polimorfismo: cada subclase muestra su propio tipo", todosHeredan);

        // 2. Agregación Pelicula-Actor
        Pelicula p = c.registrarPelicula("Prueba", 90, "Drama", "UPS Films");
        Actor a = c.registrarActor("Actor Prueba", "Ecuador", 2020);
        int numActor = c.getActores().size();
        c.asignarActor(p.getId(), numActor);
        verificar("Agregación: actor agregado a la película", p.getActores().contains(a));
        verificar("Agregación: no se duplica el actor", lanzaError(() -> c.asignarActor(p.getId(), numActor)));
        c.eliminarContenido(p.getId());
        verificar("Agregación: el actor sobrevive al eliminar la película", c.getActores().contains(a));

        // 3. Composición SerieDeTV-Temporada
        SerieDeTV s = c.registrarSerie("Serie Prueba", 40, "Comedia");
        c.agregarTemporada(s.getId(), 2024, 8);
        c.agregarTemporada(s.getId(), 2025, 10);
        verificar("Composición: numeración automática de temporadas",
                s.getTemporadas().get(1).getNumero() == 2 && s.getTotalEpisodios() == 18);
        verificar("Composición: lista de temporadas protegida",
                lanzaError(() -> s.getTemporadas().clear()));

        // 4. Asociación Documental/Podcast-Investigador
        Documental d = c.registrarDocumental("Doc Prueba", 50, "Ciencia", "Volcanes");
        Investigador inv = c.registrarInvestigador("Inv Prueba", "Vulcanología", "UPS");
        c.asignarInvestigador(d.getId(), c.getInvestigadores().size());
        verificar("Asociación: investigador vinculado al documental", d.getInvestigadores().contains(inv));
        Podcast pod = c.registrarPodcast("Pod Prueba", 20, "Ciencia", "Andy", "YouTube", 3);
        verificar("Podcast sin invitados es monólogo", pod.esMonologo());

        // 5. Nuevas subclases y reglas
        VideoMusical v = c.registrarVideoMusical("Video", 4, "Pop", "Artista", "Álbum", 2026);
        c.registrarReproducciones(v.getId(), 999_999);
        verificar("VideoMusical no viral con 999.999", !v.esViral());
        c.registrarReproducciones(v.getId(), 1);
        verificar("VideoMusical viral con 1.000.000", v.esViral());

        // 6. Interfaz Calificable y validaciones
        c.calificar(v.getId(), 5);
        c.calificar(v.getId(), 3);
        verificar("Calificación promedio 4.0", v.getCalificacionPromedio() == 4.0);
        verificar("Rechaza 6 estrellas", lanzaError(() -> c.calificar(v.getId(), 6)));
        verificar("Rechaza actor en documental", lanzaError(() -> c.asignarActor(d.getId(), 1)));
        verificar("Rechaza temporada en película", lanzaError(() -> c.agregarTemporada(0, 2020, 5)));
        verificar("Rechaza ID inexistente", lanzaError(() -> c.obtenerPorId(9999)));
        verificar("Rechaza duración 0", lanzaError(() -> c.registrarPelicula("X", 0, "G", "E")));

        // 7. Búsqueda y edición
        c.actualizarDatosBasicos(v.getId(), "Video Editado", 6, "Rock");
        List<ContenidoAudiovisual> r = c.buscarPorTitulo("editado");
        verificar("Edición y búsqueda por título", r.size() == 1 && r.get(0).getDuracionEnMinutos() == 6);

        System.out.println("---------------------------------------");
        System.out.println("Aprobadas: " + aprobadas + " | Fallidas: " + fallidas);
        if (fallidas > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (condicion) {
            aprobadas++;
            System.out.println("[OK]    " + descripcion);
        } else {
            fallidas++;
            System.out.println("[FALLA] " + descripcion);
        }
    }

    private static boolean lanzaError(Runnable accion) {
        try {
            accion.run();
            return false;
        } catch (RuntimeException e) {
            return true;
        }
    }
}
