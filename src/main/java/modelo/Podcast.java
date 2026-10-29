package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NUEVA SUBCLASE (Etapa 4): episodio de video-podcast, hereda de ContenidoAudiovisual.
 * Reutiliza la clase Investigador mediante ASOCIACIÓN (0..*): los invitados
 * expertos participan en el episodio pero son objetos independientes.
 */
public class Podcast extends ContenidoAudiovisual {
    private String anfitrion;
    private String plataforma;
    private int numeroEpisodio;
    private final List<Investigador> invitados = new ArrayList<>();

    public Podcast(String titulo, int duracionEnMinutos, String genero,
                   String anfitrion, String plataforma, int numeroEpisodio) {
        super(titulo, duracionEnMinutos, genero);
        this.anfitrion = anfitrion;
        this.plataforma = plataforma;
        this.numeroEpisodio = numeroEpisodio;
    }

    public String getAnfitrion() {
        return anfitrion;
    }

    public void setAnfitrion(String anfitrion) {
        this.anfitrion = anfitrion;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public boolean agregarInvitado(Investigador investigador) {
        if (investigador == null || invitados.contains(investigador)) {
            return false;
        }
        return invitados.add(investigador);
    }

    public boolean quitarInvitado(Investigador investigador) {
        return invitados.remove(investigador);
    }

    public List<Investigador> getInvitados() {
        return Collections.unmodifiableList(invitados);
    }

    /** Un episodio sin invitados es un monólogo del anfitrión. */
    public boolean esMonologo() {
        return invitados.isEmpty();
    }

    @Override
    public String getTipo() {
        return "Podcast";
    }

    @Override
    protected String detallesEspecificos() {
        StringBuilder sb = new StringBuilder();
        sb.append("Episodio #").append(numeroEpisodio).append(" | Anfitrión: ").append(anfitrion)
          .append(" | Plataforma: ").append(plataforma).append("\n");
        sb.append(esMonologo() ? "Formato: monólogo\n" : "Invitados (" + invitados.size() + "):\n");
        for (Investigador i : invitados) {
            sb.append("  - ").append(i).append("\n");
        }
        return sb.toString();
    }
}
