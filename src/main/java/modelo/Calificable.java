package modelo;

/**
 * Interfaz para todo contenido que el público puede calificar (1 a 5 estrellas).
 * La implementa ContenidoAudiovisual, por lo que todas sus subclases la heredan.
 */
public interface Calificable {
    int MIN_ESTRELLAS = 1;
    int MAX_ESTRELLAS = 5;

    void calificar(int estrellas);

    double getCalificacionPromedio();

    int getNumeroCalificaciones();
}
