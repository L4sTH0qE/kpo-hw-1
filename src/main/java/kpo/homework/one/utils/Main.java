package kpo.homework.one.utils;

import kpo.homework.one.model.*;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.module.kotlin.*;
import com.fasterxml.jackson.core.type.TypeReference;

// Класс Main.
public class Main {
    // Пути к json-файлам с начальными данными.
    private static final String MOVIES_PATH = "data/defaultMovies.json";
    private static final String SESSIONS_PATH = "data/defaultSessions.json";
    private static final String TICKETS_PATH = "data/defaultTickets.json";

    // Объект, используемый для сериализации.
    static ObjectMapper objectMapper = new ObjectMapper();

    // Для получения ввода пользователя используется объект типа Scanner из состава JDK(JRE) API
    // Для инициализации scanner'а используется объект стандартного потока ввода - System.in

    public static void main(String[] args) {
        // Файлы по указанным путям.
        File moviesFile = new File(MOVIES_PATH);
        File ticketsFile = new File(TICKETS_PATH);
        File sessionsFile = new File(SESSIONS_PATH);

        // Чтение уже добавленной информации о фильмах, сеансах и билетах из приложенных файлов.
        try {
            List<Movie> movies = objectMapper.readValue(moviesFile, new TypeReference<List<Movie>>(){});
            for (Movie movie : movies) {
                Console.movieDao.create(movie);

            }
            List<Session> sessions = objectMapper.readValue(sessionsFile, new TypeReference<List<Session>>(){});
            for (Session session : sessions) {
                Console.sessionDao.create(session);
            }
            List<Ticket> tickets = objectMapper.readValue(ticketsFile, new TypeReference<List<Ticket>>(){});
            for (Ticket ticket : tickets) {
                Console.ticketDao.create(ticket);
            }
        }
        // При ошибке во время чтения файлов.
        catch (Exception ex) {
            System.out.println(ex.getMessage() + "Error: Ошибка во время чтения начальных файлов!");
        }

        // Запуск основного меню.
        try {
            Console.startMenuLoop();
        }
        // При ошибке во время работы программы.
        catch (Exception ex) {
            System.out.println("Error: Возникла непредвиденная ошибка во время работы программы!. Завершение работы...");
            System.exit(0);
        }
    }
}
