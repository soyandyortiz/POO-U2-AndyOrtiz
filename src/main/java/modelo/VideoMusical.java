package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NUEVA SUBCLASE (Etapa 4): video musical, hereda de ContenidoAudiovisual.
 * Reutiliza la clase Actor mediante AGREGACIÓN (0..*): los actores o
 * bailarines que aparecen en el video existen independientemente de él.
 */
public class VideoMusical extends ContenidoAudiovisual {
    public static final long UMBRAL_VIRAL = 1_000_000;

    private String artista;
    private String album;
    private int anioLanzamiento;
    private long reproducciones;
    private final List<Actor> participantes = new ArrayList<>();

    public VideoMusical(String titulo, int duracionEnMinutos, String genero,
                        String artista, String album, int anioLanzamiento) {
        super(titulo, duracionEnMinutos, genero);
        this.artista = artista;
        this.album = album;
        this.anioLanzamiento = anioLanzamiento;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    public long getReproducciones() {
        return reproducciones;
    }

    /** Suma reproducciones al contador (no admite valores negativos). */
    public void registrarReproducciones(long cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        reproducciones += cantidad;
    }

    public boolean esViral() {
        return reproducciones >= UMBRAL_VIRAL;
    }

    public boolean agregarParticipante(Actor actor) {
        if (actor == null || participantes.contains(actor)) {
            return false;
        }
        return participantes.add(actor);
    }

    public boolean quitarParticipante(Actor actor) {
        return participantes.remove(actor);
    }

    public List<Actor> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }

    @Override
    public String getTipo() {
        return "Video musical";
    }

    @Override
    protected String detallesEspecificos() {
        StringBuilder sb = new StringBuilder();
        sb.append("Artista: ").append(artista).append(" | Álbum: ").append(album)
          .append(" (").append(anioLanzamiento).append(")\n");
        sb.append("Reproducciones: ").append(reproducciones)
          .append(esViral() ? " [VIRAL]" : "").append("\n");
        sb.append("Participantes (").append(participantes.size()).append("):\n");
        for (Actor a : participantes) {
            sb.append("  - ").append(a).append("\n");
        }
        return sb.toString();
    }
}
