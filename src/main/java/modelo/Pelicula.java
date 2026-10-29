package modelo;

/**
 * Subclase Pelicula que extiende de ContenidoAudiovisual.
 */
public class Pelicula extends ContenidoAudiovisual {
    private String estudio;

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

    @Override
    public String getTipo() {
        return "Película";
    }

    @Override
    protected String detallesEspecificos() {
        return "Estudio: " + estudio + "\n";
    }
}
