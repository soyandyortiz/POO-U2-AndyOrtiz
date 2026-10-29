package controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Pelicula;
import modelo.Podcast;
import modelo.SerieDeTV;
import modelo.Temporada;
import modelo.VideoMusical;

/**
 * CONTROLADOR (patrón MVC).
 * Contiene la lógica del sistema: crea, busca, modifica y elimina objetos
 * del modelo. No imprime nada; la vista se encarga de mostrar resultados.
 * Los errores de negocio se comunican con IllegalArgumentException.
 */
public class CatalogoController {
    private final List<ContenidoAudiovisual> catalogo = new ArrayList<>();
    private final List<Actor> actores = new ArrayList<>();
    private final List<Investigador> investigadores = new ArrayList<>();

    // ------------------------------------------------------------ registro
    public Pelicula registrarPelicula(String titulo, int duracion, String genero, String estudio) {
        validarBasicos(titulo, duracion);
        return agregar(new Pelicula(titulo, duracion, genero, estudio));
    }

    public SerieDeTV registrarSerie(String titulo, int duracion, String genero) {
        validarBasicos(titulo, duracion);
        return agregar(new SerieDeTV(titulo, duracion, genero));
    }

    public Documental registrarDocumental(String titulo, int duracion, String genero, String tema) {
        validarBasicos(titulo, duracion);
        return agregar(new Documental(titulo, duracion, genero, tema));
    }

    public VideoMusical registrarVideoMusical(String titulo, int duracion, String genero,
                                              String artista, String album, int anio) {
        validarBasicos(titulo, duracion);
        return agregar(new VideoMusical(titulo, duracion, genero, artista, album, anio));
    }

    public Podcast registrarPodcast(String titulo, int duracion, String genero,
                                    String anfitrion, String plataforma, int episodio) {
        validarBasicos(titulo, duracion);
        return agregar(new Podcast(titulo, duracion, genero, anfitrion, plataforma, episodio));
    }

    public Actor registrarActor(String nombre, String nacionalidad, int anioDebut) {
        validarTexto(nombre, "nombre");
        Actor a = new Actor(nombre, nacionalidad, anioDebut);
        actores.add(a);
        return a;
    }

    public Investigador registrarInvestigador(String nombre, String especialidad, String institucion) {
        validarTexto(nombre, "nombre");
        Investigador i = new Investigador(nombre, especialidad, institucion);
        investigadores.add(i);
        return i;
    }

    // ------------------------------------------------------------ relaciones
    /** Agregación: vincula un actor registrado a una película o video musical. */
    public void asignarActor(int idContenido, int numeroActor) {
        ContenidoAudiovisual c = obtenerPorId(idContenido);
        Actor actor = obtenerDeLista(actores, numeroActor, "actor");
        boolean agregado;
        if (c instanceof Pelicula p) {
            agregado = p.agregarActor(actor);
        } else if (c instanceof VideoMusical v) {
            agregado = v.agregarParticipante(actor);
        } else {
            throw new IllegalArgumentException("Solo se pueden asignar actores a películas o videos musicales");
        }
        if (!agregado) {
            throw new IllegalArgumentException("Ese actor ya está asignado a este contenido");
        }
    }

    /** Asociación: vincula un investigador a un documental o podcast. */
    public void asignarInvestigador(int idContenido, int numeroInvestigador) {
        ContenidoAudiovisual c = obtenerPorId(idContenido);
        Investigador inv = obtenerDeLista(investigadores, numeroInvestigador, "investigador");
        boolean agregado;
        if (c instanceof Documental d) {
            agregado = d.agregarInvestigador(inv);
        } else if (c instanceof Podcast p) {
            agregado = p.agregarInvitado(inv);
        } else {
            throw new IllegalArgumentException("Solo se pueden asignar investigadores a documentales o podcasts");
        }
        if (!agregado) {
            throw new IllegalArgumentException("Ese investigador ya está asignado a este contenido");
        }
    }

    /** Composición: la temporada se crea dentro de la serie. */
    public Temporada agregarTemporada(int idSerie, int anioEstreno, int episodios) {
        if (!(obtenerPorId(idSerie) instanceof SerieDeTV serie)) {
            throw new IllegalArgumentException("El contenido " + idSerie + " no es una serie de TV");
        }
        if (episodios <= 0) {
            throw new IllegalArgumentException("Una temporada debe tener al menos 1 episodio");
        }
        return serie.agregarTemporada(anioEstreno, episodios);
    }

    // ------------------------------------------------------------ manipulación
    public void calificar(int idContenido, int estrellas) {
        obtenerPorId(idContenido).calificar(estrellas);
    }

    public void registrarReproducciones(int idVideo, long cantidad) {
        if (!(obtenerPorId(idVideo) instanceof VideoMusical video)) {
            throw new IllegalArgumentException("El contenido " + idVideo + " no es un video musical");
        }
        video.registrarReproducciones(cantidad);
    }

    public void actualizarDatosBasicos(int id, String titulo, int duracion, String genero) {
        validarBasicos(titulo, duracion);
        ContenidoAudiovisual c = obtenerPorId(id);
        c.setTitulo(titulo);
        c.setDuracionEnMinutos(duracion);
        c.setGenero(genero);
    }

    /**
     * Elimina un contenido. Por composición, las temporadas de una serie se
     * eliminan con ella; por agregación/asociación, actores e investigadores
     * siguen registrados.
     */
    public ContenidoAudiovisual eliminarContenido(int id) {
        ContenidoAudiovisual c = obtenerPorId(id);
        catalogo.remove(c);
        return c;
    }

    // ------------------------------------------------------------ consultas
    public ContenidoAudiovisual obtenerPorId(int id) {
        for (ContenidoAudiovisual c : catalogo) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new IllegalArgumentException("No existe contenido con ID " + id);
    }

    public List<ContenidoAudiovisual> buscarPorTitulo(String texto) {
        List<ContenidoAudiovisual> resultado = new ArrayList<>();
        String t = texto == null ? "" : texto.toLowerCase();
        for (ContenidoAudiovisual c : catalogo) {
            if (c.getTitulo().toLowerCase().contains(t)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<ContenidoAudiovisual> getCatalogo() {
        return Collections.unmodifiableList(catalogo);
    }

    public List<Actor> getActores() {
        return Collections.unmodifiableList(actores);
    }

    public List<Investigador> getInvestigadores() {
        return Collections.unmodifiableList(investigadores);
    }

    public int getDuracionTotal() {
        int total = 0;
        for (ContenidoAudiovisual c : catalogo) {
            total += c.getDuracionEnMinutos();
        }
        return total;
    }

    /** Devuelve el contenido mejor calificado o null si nadie ha calificado. */
    public ContenidoAudiovisual getMejorCalificado() {
        ContenidoAudiovisual mejor = null;
        for (ContenidoAudiovisual c : catalogo) {
            if (c.getNumeroCalificaciones() > 0
                    && (mejor == null || c.getCalificacionPromedio() > mejor.getCalificacionPromedio())) {
                mejor = c;
            }
        }
        return mejor;
    }

    public int contarPorTipo(String tipo) {
        int n = 0;
        for (ContenidoAudiovisual c : catalogo) {
            if (c.getTipo().equalsIgnoreCase(tipo)) {
                n++;
            }
        }
        return n;
    }

    // ------------------------------------------------------------ datos de ejemplo
    public void cargarDatosDeEjemplo() {
        Actor sam = registrarActor("Sam Worthington", "Australia", 2000);
        Actor zoe = registrarActor("Zoe Saldaña", "Estados Unidos", 2000);
        Actor juan = registrarActor("Juan Pérez", "Ecuador", 2015);
        Investigador sagan = registrarInvestigador("Carl Sagan", "Astronomía", "Universidad de Cornell");
        Investigador torres = registrarInvestigador("María Torres", "Astrofísica", "Escuela Politécnica Nacional");

        Pelicula avatar = registrarPelicula("Avatar", 125, "Acción", "20th Century Studios");
        avatar.agregarActor(sam);
        avatar.agregarActor(zoe);
        avatar.calificar(5);
        avatar.calificar(4);

        SerieDeTV got = registrarSerie("Game of Thrones", 60, "Fantasía");
        got.agregarTemporada(2011, 10);
        got.agregarTemporada(2012, 10);
        got.calificar(5);

        Documental cosmos = registrarDocumental("Cosmos", 45, "Ciencia", "Astronomía");
        cosmos.agregarInvestigador(sagan);

        VideoMusical video = registrarVideoMusical("Vivir mi vida", 5, "Salsa", "Marc Anthony", "3.0", 2013);
        video.agregarParticipante(juan);
        video.registrarReproducciones(1_500_000);

        Podcast podcast = registrarPodcast("El universo en 30 minutos", 30, "Ciencia", "Andy Ortiz", "Spotify", 1);
        podcast.agregarInvitado(torres);
        podcast.calificar(4);
    }

    // ------------------------------------------------------------ utilidades
    private <T extends ContenidoAudiovisual> T agregar(T contenido) {
        catalogo.add(contenido);
        return contenido;
    }

    private static <T> T obtenerDeLista(List<T> lista, int numero, String nombre) {
        if (numero < 1 || numero > lista.size()) {
            throw new IllegalArgumentException("No existe el " + nombre + " número " + numero);
        }
        return lista.get(numero - 1);
    }

    private static void validarBasicos(String titulo, int duracion) {
        validarTexto(titulo, "título");
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 minutos");
        }
    }

    private static void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El " + campo + " no puede estar vacío");
        }
    }
}
