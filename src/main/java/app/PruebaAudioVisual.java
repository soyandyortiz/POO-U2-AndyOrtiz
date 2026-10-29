package app;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Pelicula;
import modelo.Podcast;
import modelo.SerieDeTV;
import modelo.VideoMusical;

public class PruebaAudioVisual {
    public static void main(String[] args) {
        System.out.println("Hello from Eclipse!");

        // Objetos independientes (agregación / asociación)
        Actor sam = new Actor("Sam Worthington", "Australia", 2000);
        Actor zoe = new Actor("Zoe Saldaña", "Estados Unidos", 2000);
        Actor bailarin = new Actor("Juan Pérez", "Ecuador", 2015);
        Investigador sagan = new Investigador("Carl Sagan", "Astronomía", "Universidad de Cornell");
        Investigador torres = new Investigador("María Torres", "Astrofísica", "Escuela Politécnica Nacional");

        Pelicula avatar = new Pelicula("Avatar", 125, "Accion", "20th Century Studios");
        avatar.agregarActor(sam);
        avatar.agregarActor(zoe);

        // Composición: la serie crea sus propias temporadas
        SerieDeTV got = new SerieDeTV("Game of Thrones", 60, "Fantasy");
        got.agregarTemporada(2011, 10);
        got.agregarTemporada(2012, 10);

        Documental cosmos = new Documental("Cosmos", 45, "Science", "Astronomy");
        cosmos.agregarInvestigador(sagan);

        // Nuevas subclases (Etapa 4)
        VideoMusical video = new VideoMusical("Vivir mi vida", 5, "Salsa", "Marc Anthony", "3.0", 2013);
        video.agregarParticipante(bailarin);
        video.registrarReproducciones(1_500_000);

        Podcast podcast = new Podcast("El universo en 30 minutos", 30, "Ciencia",
                "Andy Ortiz", "Spotify", 1);
        podcast.agregarInvitado(torres);

        ContenidoAudiovisual[] contenidos = { avatar, got, cosmos, video, podcast };

        // Calificar usando la interfaz Calificable
        avatar.calificar(5);
        avatar.calificar(4);
        podcast.calificar(5);

        // Mostrar los detalles de cada contenido audiovisual (polimorfismo)
        for (ContenidoAudiovisual contenido : contenidos) {
            contenido.mostrarDetalles();
        }
    }
}
