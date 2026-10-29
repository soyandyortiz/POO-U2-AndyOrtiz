package modelo;

/**
 * Superclase abstracta de todo el contenido audiovisual del sistema.
 * Define los atributos comunes y el comportamiento polimórfico
 * {@link #mostrarDetalles()} que cada subclase especializa.
 *
 * @author Andy Ortiz
 */
public abstract class ContenidoAudiovisual {
    private static int contar = 0;
    private String titulo;
    private int duracionEnMinutos;
    private String genero;
    private final int id;

    public ContenidoAudiovisual(String titulo, int duracionEnMinutos, String genero) {
        this.id = contar++;
        this.titulo = titulo;
        this.duracionEnMinutos = duracionEnMinutos;
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    // 'id' no tiene setter: se asigna una sola vez en el constructor
    public int getId() {
        return id;
    }

    /** Nombre del tipo de contenido (Película, Serie de TV, ...). */
    public abstract String getTipo();

    /** Detalles propios de cada subclase. */
    protected abstract String detallesEspecificos();

    /** Detalles completos: datos comunes + datos de la subclase. */
    public String obtenerDetalles() {
        return "Detalles (" + getTipo() + "):\n"
                + "ID: " + id + "\n"
                + "Título: " + titulo + "\n"
                + "Duración en minutos: " + duracionEnMinutos + "\n"
                + "Género: " + genero + "\n"
                + detallesEspecificos();
    }

    /** Método polimórfico heredado del código original. */
    public void mostrarDetalles() {
        System.out.println(obtenerDetalles());
    }
}
