package modelo;

/**
 * Subclase Documental que extiende de ContenidoAudiovisual.
 */
public class Documental extends ContenidoAudiovisual {
    private String tema;

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

    @Override
    public String getTipo() {
        return "Documental";
    }

    @Override
    protected String detallesEspecificos() {
        return "Tema: " + tema + "\n";
    }
}
