package modelo;

/**
 * Superclase abstracta de todo el contenido audiovisual del sistema.
 * Define los atributos comunes y el comportamiento polimórfico
 * {@link #mostrarDetalles()} que cada subclase especializa.
 *
 * @author Andy Ortiz
 */
public abstract class ContenidoAudiovisual implements Calificable {
    private static int contar = 0;
    private String titulo;
    private int duracionEnMinutos;
    private String genero;
    private final int id;
    private int sumaEstrellas;
    private int numeroCalificaciones;

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

    // ---- Implementación de la interfaz Calificable ----
    @Override
    public void calificar(int estrellas) {
        if (estrellas < MIN_ESTRELLAS || estrellas > MAX_ESTRELLAS) {
            throw new IllegalArgumentException(
                    "La calificación debe estar entre " + MIN_ESTRELLAS + " y " + MAX_ESTRELLAS);
        }
        sumaEstrellas += estrellas;
        numeroCalificaciones++;
    }

    @Override
    public double getCalificacionPromedio() {
        return numeroCalificaciones == 0 ? 0 : (double) sumaEstrellas / numeroCalificaciones;
    }

    @Override
    public int getNumeroCalificaciones() {
        return numeroCalificaciones;
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
                + String.format("Calificación: %.1f/5 (%d votos)%n",
                        getCalificacionPromedio(), numeroCalificaciones)
                + detallesEspecificos();
    }

    /** Método polimórfico heredado del código original. */
    public void mostrarDetalles() {
        System.out.println(obtenerDetalles());
    }
}
