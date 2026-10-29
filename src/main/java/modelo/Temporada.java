package modelo;

/**
 * Representa una temporada de una serie de TV.
 * Relación con SerieDeTV: COMPOSICIÓN. Una temporada no tiene sentido
 * sin su serie: solo SerieDeTV puede crearla (el constructor no es público)
 * y, al eliminar la serie, sus temporadas desaparecen con ella.
 */
public class Temporada {
    private final int numero;
    private int anioEstreno;
    private int numeroEpisodios;

    // Constructor de paquete: únicamente SerieDeTV crea temporadas
    Temporada(int numero, int anioEstreno, int numeroEpisodios) {
        this.numero = numero;
        this.anioEstreno = anioEstreno;
        this.numeroEpisodios = numeroEpisodios;
    }

    public int getNumero() {
        return numero;
    }

    public int getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(int anioEstreno) {
        this.anioEstreno = anioEstreno;
    }

    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }

    public void setNumeroEpisodios(int numeroEpisodios) {
        this.numeroEpisodios = numeroEpisodios;
    }

    @Override
    public String toString() {
        return "Temporada " + numero + " (" + anioEstreno + ") - " + numeroEpisodios + " episodios";
    }
}
