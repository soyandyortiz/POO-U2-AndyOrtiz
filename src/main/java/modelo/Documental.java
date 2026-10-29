package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Subclase Documental que extiende de ContenidoAudiovisual.
 * Se asocia (asociación 0..*) con los investigadores que lo asesoran.
 */
public class Documental extends ContenidoAudiovisual {
    private String tema;
    private final List<Investigador> investigadores = new ArrayList<>();

    public Documental(String titulo, int duracionEnMinutos, String genero, String tema) {
        super(titulo, duracionEnMinutos, genero);
        this.tema = tema;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public boolean agregarInvestigador(Investigador investigador) {
        if (investigador == null || investigadores.contains(investigador)) {
            return false;
        }
        return investigadores.add(investigador);
    }

    public boolean quitarInvestigador(Investigador investigador) {
        return investigadores.remove(investigador);
    }

    public List<Investigador> getInvestigadores() {
        return Collections.unmodifiableList(investigadores);
    }

    @Override
    public String getTipo() {
        return "Documental";
    }

    @Override
    protected String detallesEspecificos() {
        StringBuilder sb = new StringBuilder("Tema: " + tema + "\n");
        sb.append("Investigadores asesores (").append(investigadores.size()).append("):\n");
        for (Investigador i : investigadores) {
            sb.append("  - ").append(i).append("\n");
        }
        return sb.toString();
    }
}
