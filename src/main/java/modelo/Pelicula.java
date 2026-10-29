package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Subclase Pelicula que extiende de ContenidoAudiovisual.
 * Agrega (agregación 0..*) a los actores de su reparto.
 */
public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private final List<Actor> actores = new ArrayList<>();

    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(titulo, duracionEnMinutos, genero);
        this.estudio = estudio;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    /** Agrega un actor ya existente al reparto (sin duplicados). */
    public boolean agregarActor(Actor actor) {
        if (actor == null || actores.contains(actor)) {
            return false;
        }
        return actores.add(actor);
    }

    public boolean quitarActor(Actor actor) {
        return actores.remove(actor);
    }

    public List<Actor> getActores() {
        return Collections.unmodifiableList(actores);
    }

    @Override
    public String getTipo() {
        return "Película";
    }

    @Override
    protected String detallesEspecificos() {
        StringBuilder sb = new StringBuilder("Estudio: " + estudio + "\n");
        sb.append("Reparto (").append(actores.size()).append("):\n");
        for (Actor a : actores) {
            sb.append("  - ").append(a).append("\n");
        }
        return sb.toString();
    }
}
