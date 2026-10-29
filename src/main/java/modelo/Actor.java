package modelo;

/**
 * Representa a un actor o actriz.
 * Relación con Pelicula: AGREGACIÓN. El actor existe por sí mismo
 * (se registra aparte) y puede formar parte del reparto de varias películas;
 * si una película se elimina, el actor sigue existiendo.
 */
public class Actor {
    private String nombre;
    private String nacionalidad;
    private int anioDebut;

    public Actor(String nombre, String nacionalidad, int anioDebut) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.anioDebut = anioDebut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getAnioDebut() {
        return anioDebut;
    }

    public void setAnioDebut(int anioDebut) {
        this.anioDebut = anioDebut;
    }

    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ", debut " + anioDebut + ")";
    }
}
