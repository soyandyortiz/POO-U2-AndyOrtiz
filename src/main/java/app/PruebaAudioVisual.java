package app;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Pelicula;
import modelo.SerieDeTV;

public class PruebaAudioVisual {
    public static void main(String[] args) {
        System.out.println("Hello from Eclipse!");

        // Objetos independientes (agregación / asociación)
        Actor sam = new Actor("Sam Worthington", "Australia", 2000);
        Actor zoe = new Actor("Zoe Saldaña", "Estados Unidos", 2000);
        Investigador sagan = new Investigador("Carl Sagan", "Astronomía", "Universidad de Cornell");

        Pelicula avatar = new Pelicula("Avatar", 125, "Accion", "20th Century Studios");
        avatar.agregarActor(sam);
        avatar.agregarActor(zoe);

        // Composición: la serie crea sus propias temporadas
        SerieDeTV got = new SerieDeTV("Game of Thrones", 60, "Fantasy");
        got.agregarTemporada(2011, 10);
        got.agregarTemporada(2012, 10);

        Documental cosmos = new Documental("Cosmos", 45, "Science", "Astronomy");
        cosmos.agregarInvestigador(sagan);

        ContenidoAudiovisual[] contenidos = { avatar, got, cosmos };

        // Mostrar los detalles de cada contenido audiovisual (polimorfismo)
        for (ContenidoAudiovisual contenido : contenidos) {
            contenido.mostrarDetalles();
        }
    }
}
