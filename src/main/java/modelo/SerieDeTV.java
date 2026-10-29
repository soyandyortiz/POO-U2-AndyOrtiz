package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Subclase SerieDeTV que extiende de ContenidoAudiovisual.
 * Está compuesta (composición 0..*) por sus temporadas: la serie las crea
 * y controla su ciclo de vida.
 */
public class SerieDeTV extends ContenidoAudiovisual {
    private final List<Temporada> temporadas = new ArrayList<>();

    public SerieDeTV(String titulo, int duracionEnMinutos, String genero) {
        super(titulo, duracionEnMinutos, genero);
    }

    /** Crea una nueva temporada dentro de la serie (numeración automática). */
    public Temporada agregarTemporada(int anioEstreno, int numeroEpisodios) {
        Temporada t = new Temporada(temporadas.size() + 1, anioEstreno, numeroEpisodios);
        temporadas.add(t);
        return t;
    }

    /** Elimina la última temporada creada. */
    public boolean eliminarUltimaTemporada() {
        if (temporadas.isEmpty()) {
            return false;
        }
        temporadas.remove(temporadas.size() - 1);
        return true;
    }

    public List<Temporada> getTemporadas() {
        return Collections.unmodifiableList(temporadas);
    }

    public int getNumeroTemporadas() {
        return temporadas.size();
    }

    public int getTotalEpisodios() {
        int total = 0;
        for (Temporada t : temporadas) {
            total += t.getNumeroEpisodios();
        }
        return total;
    }

    @Override
    public String getTipo() {
        return "Serie de TV";
    }

    @Override
    protected String detallesEspecificos() {
        StringBuilder sb = new StringBuilder();
        sb.append("Temporadas: ").append(getNumeroTemporadas())
          .append(" | Episodios totales: ").append(getTotalEpisodios()).append("\n");
        for (Temporada t : temporadas) {
            sb.append("  - ").append(t).append("\n");
        }
        return sb.toString();
    }
}
