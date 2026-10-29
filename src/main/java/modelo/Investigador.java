package modelo;

/**
 * Representa a un investigador o experto que asesora contenido.
 * Relación con Documental: ASOCIACIÓN. El documental conoce a los
 * investigadores que lo asesoran, pero no hay relación todo-parte:
 * ambos objetos son independientes.
 */
public class Investigador {
    private String nombre;
    private String especialidad;
    private String institucion;

    public Investigador(String nombre, String especialidad, String institucion) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.institucion = institucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    @Override
    public String toString() {
        return nombre + " - " + especialidad + " (" + institucion + ")";
    }
}
