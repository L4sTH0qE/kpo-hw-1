package kpo.homework.one.utils;

import de.vandermeer.asciitable.AsciiTable;
import kpo.homework.one.dao.MovieDaoImpl;
import kpo.homework.one.dao.SessionDaoImpl;
import kpo.homework.one.dao.TicketDaoImpl;
import kpo.homework.one.model.Movie;
import kpo.homework.one.model.Session;
import kpo.homework.one.model.Ticket;
import kpo.homework.one.model.TicketStatus;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// Основной класс со статическими методами для работы приложения.
public class Console {

    // Dao-объекты для используемых в программе сущностей.
    static MovieDaoImpl movieDao = new MovieDaoImpl();
    static SessionDaoImpl sessionDao = new SessionDaoImpl();
    static TicketDaoImpl ticketDao = new TicketDaoImpl();

    // Для получения ввода пользователя используется объект типа Scanner из состава JDK(JRE) API
    // Для инициализации scanner'а используется объект стандартного потока ввода - System.in
    static Scanner scanner = new Scanner(System.in);

    // Метод для отображения основного меню программы.
    public static void startMenuLoop() throws Exception {
        try {
            // Выводим меню и ждем выбора пользователя, пока не будет выбрана опция "q" (завершение работы)
            while (true) {
                System.out.println();
                System.out.println("Выберите одну из доступных команд:");
                System.out.println("1 - Изменить информацию о фильмах в прокате");
                System.out.println("2 - Изменить информацию о сеансах");
                System.out.println("3 - Продать билет");
                System.out.println("4 - Оформить возврат билета");
                System.out.println("5 - Отметить занятое место");
                System.out.println("q - Завершение работы");
                System.out.println();
                System.out.print("Ваш ввод> ");

                String choice = scanner.nextLine();
                System.out.println();

                switch (choice) {
                    case "1":
                        editFilms();
                        break;
                    case "2":
                        editSessions();
                        break;
                    case "3":
                        sellTicket();
                        break;
                    case "4":
                        refundTicket();
                        break;
                    case "5":
                        occupyTicket();
                        break;
                    case "q":
                        System.out.println("Завершение работы...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Указана неверная команда.");
                }
            }
            // При ошибке во время работы программы.
        } catch (Exception ex) {
            System.out.println("Error: Возникла непредвиденная ошибка во время работы программы!. Завершение работы...");
            System.exit(0);
        }
    }

    // Метод для изменения информации о фильмах.
    private static void editFilms() throws IOException {
        try {
            while (true) {
                System.out.println();
                System.out.println("Выберите одну из доступных команд:");
                System.out.println("1 - Добавить фильм");
                System.out.println("2 - Удалить фильм");
                System.out.println("q - Отменить действие");
                System.out.println();
                System.out.print("Ваш ввод> ");

                String choice = scanner.nextLine();
                System.out.println();

                switch (choice) {
                    case "1":
                        addFilm();
                        return;
                    case "2":
                        removeFilm();
                        return;
                    case "q":
                        System.out.println("Возврат в главное меню...");
                        return;
                    default:
                        System.out.println("Указана неверная команда.");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: Возникла непредвиденная ошибка во время работы программы!. Завершение работы...");
            System.exit(0);
        }
    }

    // Метод для получения id нового фильма.
    private static int getMovieId() throws IOException {
        int id = -1;
        System.out.println();
        System.out.println("Введите id фильма, который вы хотите добавить:");
        boolean flag = true;
        while (flag) {
            try {
                System.out.print("Ваш ввод> ");
                String choice = scanner.nextLine();
                id = Integer.parseInt(choice);
                System.out.println();
                if (movieDao.read(id) != null) {
                    System.out.println("Error: Есть фильм с таким id!");
                } else {
                    flag = false;
                }
            } catch (NumberFormatException ex1) {
                System.out.println("Error: Введите целое число!");
            } catch (Exception ex2) {
                System.out.println("Error: Непредвиденная ошибка!");
            }
        }
        return id;
    }

    // Метод для получения названия нового фильма.
    private static String getMovieTitle() throws IOException {
        System.out.println();
        System.out.println("Введите название фильма, который вы хотите добавить:");
        System.out.print("Ваш ввод> ");
        return scanner.nextLine();
    }

    // Метод для получения продолжительности нового фильма
    private static int getMovieRunTime() throws IOException {
        int runTime = -1;
        System.out.println();
        System.out.println("Введите продолжительность фильма (в минутах), который вы хотите добавить:");
        boolean flag = true;
        while (flag) {
            try {
                System.out.print("Ваш ввод> ");
                String choice = scanner.nextLine();
                runTime = Integer.parseInt(choice);
                System.out.println();
                if (runTime <= 0) {
                    System.out.println("Error: Продолжительность - положительное целое число!");
                } else {
                    flag = false;
                }
            } catch (NumberFormatException ex1) {
                System.out.println("Error: Введите целое число!");
            } catch (Exception ex2) {
                System.out.println("Error: Непредвиденная ошибка!");
            }
        }
        return runTime;
    }

    // Метод для добавления нового фильма.
    private static void addFilm() throws IOException {
        // Вывод списка фильмов.
        System.out.println("Список фильмов в прокате:");
        for (Movie movie : movieDao.getAll()) {
            System.out.println("* " + movie);
        }

        int id = getMovieId(); // id фильма.

        String title = getMovieTitle(); // Название фильма.

        int runTime = getMovieRunTime(); // Продолжительность фильма.

        // Добавление фильма.
        Movie movie = new Movie(id, title, runTime);
        movieDao.create(movie);
        System.out.println("Добавление фильма выполнено.");
        System.out.println("Возврат в главное меню...");
    }

    // Метод для удаления фильма из списка.
    private static void removeFilm() throws IOException {
        // Если нет фильмов.
        if (movieDao.getAll().isEmpty()) {
            System.out.println("Нет фильмов в прокате. Возврат в главное меню...");
        } else {
            // Вывод списка фильмов.
            System.out.println("Список фильмов в прокате:");
            for (Movie movie : movieDao.getAll()) {
                System.out.println("* " + movie);
            }
            System.out.println();
            System.out.println("Введите id фильма, который вы хотите удалить:");
            boolean flag = true;
            while (flag) {
                try {
                    System.out.print("Ваш ввод> ");
                    String choice = scanner.nextLine();
                    int id = Integer.parseInt(choice);
                    System.out.println();
                    if (movieDao.read(id) == null) {
                        System.out.println("Error: Нет фильма с таким id!");
                    } else {
                        // Удаление фильма с указанным id.
                        flag = false;
                        List<Session> sessions = sessionDao.getSessions(id);
                        for (Session session : sessions) {
                            List<Ticket> tickets = ticketDao.getTickets(session.getId());
                            for (Ticket ticket : tickets) {
                                ticketDao.delete(ticket);
                            }
                            sessionDao.delete(session);
                        }
                        movieDao.delete(movieDao.read(id));
                        System.out.println("Удаление фильма (и всей связанной информации) выполнено.");
                    }
                } catch (NumberFormatException ex1) {
                    System.out.println("Error: Введите целое число!");
                } catch(Exception ex2) {
                    System.out.println("Error: Непредвиденная ошибка!");
                }
            }
            System.out.println("Возврат в главное меню...");
        }
    }

    // Метод для изменения информации о сеансах.
    private static void editSessions() throws IOException {
        try {
            while (true) {
                System.out.println();
                System.out.println("Выберите одну из доступных команд:");
                System.out.println("1 - Добавить сеанс");
                System.out.println("2 - Удалить сеанс");
                System.out.println("q - Отменить действие");
                System.out.println();
                System.out.print("Ваш ввод> ");

                String choice = scanner.nextLine();
                System.out.println();

                switch (choice) {
                    case "1":
                        addSession();
                        return;
                    case "2":
                        removeSession();
                        return;
                    case "q":
                        System.out.println("Возврат в главное меню...");
                        return;
                    default:
                        System.out.println("Указана неверная команда.");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: Возникла непредвиденная ошибка во время работы программы!. Завершение работы...");
            System.exit(0);
        }
    }

    // Метод для получения id фильма, сеанс на который мы хотим добавить.
    private static int getMovieSessionId() throws IOException {
        System.out.println();
        System.out.println("Введите id фильма, сеанс на который вы хотите добавить:");
        int filmId = -1;
        while (true) {
            try {
                System.out.print("Ваш ввод> ");
                String choice = scanner.nextLine();
                filmId = Integer.parseInt(choice);
                System.out.println();
                if (movieDao.read(filmId) == null) {
                    System.out.println("Error: Нет фильма с таким id!");
                } else {
                    return filmId;
                }
            } catch (NumberFormatException ex1) {
                System.out.println("Error: Введите целое число!");
            } catch (Exception ex2) {
                System.out.println("Error: Непредвиденная ошибка!");
            }
        }
    }

    // Метод для получения id нового сеанса.
    private static int getSessionId() throws IOException {
        System.out.println();
        System.out.println("Введите id сеанса, который вы хотите добавить:");
        int id = -1;
        while (true) {
            try {
                System.out.print("Ваш ввод> ");
                String choice = scanner.nextLine();
                id = Integer.parseInt(choice);
                System.out.println();
                if (sessionDao.read(id) != null) {
                    System.out.println("Error: Есть сеанс с таким id!");
                } else {
                    return id;
                }
            } catch (NumberFormatException ex1) {
                System.out.println("Error: Введите целое число!");
            } catch (Exception ex2) {
                System.out.println("Error: Непредвиденная ошибка!");
            }
        }
    }

    // Метод для получения времени начала нового сеанса.
    private static String getSessionStartTime(int filmId) throws  IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm");
        Date startTime;
        String startTimeStr = "";
        while (true) {
            try {
                boolean flag = false;
                System.out.println();
                System.out.println("Введите время начала сеанса, в формате \"yyyy-dd-MM HH:mm\":");
                System.out.print("Ваш ввод> ");
                String choice = scanner.nextLine();
                startTime = formatter.parse(choice);
                startTimeStr = choice;
                System.out.println();
                // Проверяем введеное время, чтобы оно не пересекалось с другими сеансами.
                for (Session session : sessionDao.getAll()) {
                    if ((formatter.parse(session.getStartTime()).getTime() <= startTime.getTime() &&
                            startTime.getTime() <= formatter.parse(session.getStartTime()).getTime() + movieDao.read(session.getFilmId()).getRuntime() * 60000L) ||
                            (startTime.getTime() <= formatter.parse(session.getStartTime()).getTime() &&
                                    formatter.parse(session.getStartTime()).getTime() <= startTime.getTime() + movieDao.read(filmId).getRuntime() * 60000L)) {
                        System.out.println("Error: Есть пересечение по времени с другим сеансом!");
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    continue;
                } else {
                    return startTimeStr;
                }
            } catch (ParseException ex1) {
                System.out.println("Error: Некорректные формат введенной даты!");
            } catch (Exception ex2) {
                System.out.println("Error: Непредвиденная ошибка!");
            }
        }
    }

    // Метод для добавления нового сеанса.
    private static void addSession() throws IOException {
        if (movieDao.getAll().isEmpty()) {
            System.out.println("Нет фильмов в прокате. Возврат в главное меню...");
            return;
        }
        System.out.println();
        System.out.println("Список фильмов в прокате:");
        for (Movie movie : movieDao.getAll()) {
            System.out.println("* " + movie);
        }

        int filmId = getMovieSessionId(); // id фильма, сеанс на который мы добавляем.

        System.out.println();
        System.out.println("Список доступных сеансов в прокате:");
        for (Session session : sessionDao.getAll()) {
            System.out.println("* " + session + "; Title='" + movieDao.read(session.getFilmId()).getTitle() + "'");
        }

        int id = getSessionId(); // id нового сеанса.

        String startTimeStr = getSessionStartTime(filmId); // время начала нового сеанса.

        // Добавление нового сеанса.
        Session session = new Session(id, filmId, startTimeStr);
        sessionDao.create(session);
        ticketDao.generateTickets(id);
        System.out.println("Добавление сеанса выполнено.");
        System.out.println("Возврат в главное меню...");
    }

    // Метод для удаления существующего сеанса.
    private static void removeSession() throws IOException {
        if (sessionDao.getAll().isEmpty()) {
            System.out.println("Нет доступных сеансов в прокате. Возврат в главное меню...");
        } else {
            System.out.println("Список доступных сеансов:");
            for (Session session : sessionDao.getAll()) {
                System.out.println("* " + session + "; Title='" + movieDao.read(session.getFilmId()).getTitle() + "'");
            }
            System.out.println();
            System.out.println("Введите id сеанса, который вы хотите удалить:");
            while (true) {
                try {
                    System.out.print("Ваш ввод> ");
                    String choice = scanner.nextLine();
                    int id = Integer.parseInt(choice);
                    System.out.println();
                    if (sessionDao.read(id) == null) {
                        System.out.println("Error: Нет сеанса с таким id!");
                    } else {
                        List<Ticket> tickets = ticketDao.getTickets(id);
                        for (Ticket ticket : tickets) {
                            ticketDao.delete(ticket);
                        }
                        sessionDao.delete(sessionDao.read(id));
                        System.out.println("Удаление сеанса (и всей связанной информации) выполнено.");
                        System.out.println("Возврат в главное меню...");
                        return;
                    }
                } catch (NumberFormatException ex1) {
                    System.out.println("Error: Введите целое число!");
                } catch(Exception ex2) {
                    System.out.println("Error: Непредвиденная ошибка!");
                }
            }
        }
    }

    // Метод для вывода статусов мест на данный сеанс в user-friendly стиле.
    private static void printTickets(int sessionId) throws IOException {
        System.out.println("F - свободно, S - продано, O - занято.");
        System.out.println("Данный сеанс:");
        System.out.println();

        // Выводим информацию о билетах на даннный сеанс в формате таблицы ascii (почему бы не воспользоваться хорошей идеей?)
        AsciiTable table = new AsciiTable();
        table.addRule();

        // Заполняем таблицу статусами билетов на данный сеанс.
        String[] firstRow = new String[11];
        firstRow[0] = "ряд\\место";
        for (int i = 1; i < 11; ++i) {
            firstRow[i] = Integer.toString(i);
        }
        table.addRow(firstRow);
        table.addRule();

        for (int i = 1; i < 11; ++i) {
            String[] row = new String[11];
            row[0] = Integer.toString(i);
            for (int j = 1; j < 11; ++j) {
                Ticket ticket = ticketDao.read(sessionId, i, j);
                if (ticket == null) {
                    row[j] = "-";
                } else {
                    row[j] = ticket.getStatus() == TicketStatus.Free ? "F" : ticket.getStatus() == TicketStatus.Sold ? "S" : "O";
                }
            }
            table.addRow(row);
            table.addRule();
        }
        // Выводим таблицу.
        System.out.println(table.render());
        System.out.println();
    }

    // Метод для получения id сеанса, билет на который мы хотим изменить.
    private static int getTicketSession() throws IOException {
        int id = -1;
        System.out.println();
        System.out.println("Список доступных сеансов:");
        for (Session session : sessionDao.getAll()) {
            System.out.println("* " + session + "; Title='" + movieDao.read(session.getFilmId()).getTitle() + "'");
        }
        System.out.println();
        System.out.println("Введите id сеанса:");
        while (true) {
            try {
                System.out.print("Ваш ввод> ");
                String choice = scanner.nextLine();
                id = Integer.parseInt(choice);
                System.out.println();
                if (sessionDao.read(id) == null) {
                    System.out.println("Error: Нет сеанса с таким id!");
                } else {
                    return id;
                }
            } catch (NumberFormatException ex1) {
                System.out.println("Error: Введите целое число!");
            } catch(Exception ex2) {
                System.out.println("Error: Непредвиденная ошибка!");
            }
        }
    }

    // Метод для корректного изменения статуса билета на данный сеанс.
    private static void changeTicketStatus(int sessionId, TicketStatus status) throws IOException {
        TicketStatus statusCheck = status == TicketStatus.Free ? TicketStatus.Sold : status == TicketStatus.Sold ? TicketStatus.Free : TicketStatus.Sold;
        int row = -1;
        int number = -1;
        String choice;

        // Запрашиваем ряд и номер билета, статус которого мы требуется изменить.
        while (true) {
            while (true) {
                try {
                    System.out.println();
                    System.out.println("Введите номер ряда:");
                    System.out.print("Ваш ввод> ");
                    choice = scanner.nextLine();
                    row = Integer.parseInt(choice);
                    System.out.println();
                    if (row < 1 || row > 10) {
                        System.out.println("Error: Нет ряда с таким номером!");
                    } else {
                        break;
                    }
                } catch (NumberFormatException ex1) {
                    System.out.println("Error: Введите целое число!");
                } catch(Exception ex2) {
                    System.out.println("Error: Непредвиденная ошибка!");
                }
                System.out.println("Введите q, если хотите отменить действие (что-то другое если хотите повторить попытку):");
                System.out.print("Ваш ввод> ");
                choice = scanner.nextLine();
                if (Objects.equals(choice, "q")) {
                    return;
                }
            }
            while (true) {
                try {
                    System.out.println();
                    System.out.println("Введите номер места:");
                    System.out.print("Ваш ввод> ");
                    choice = scanner.nextLine();
                    number = Integer.parseInt(choice);
                    System.out.println();
                    if (number < 1 || number > 10) {
                        System.out.println("Error: Нет места с таким номером!");
                    } else {
                        break;
                    }
                } catch (NumberFormatException ex1) {
                    System.out.println("Error: Введите целое число!");
                } catch(Exception ex2) {
                    System.out.println("Error: Непредвиденная ошибка!");
                }
                System.out.println("Введите q, если хотите отменить действие (что-то другое если хотите повторить попытку):");
                System.out.print("Ваш ввод> ");
                choice = scanner.nextLine();
                if (Objects.equals(choice, "q")) {
                    return;
                }
            }
            // Проверка того, что изменение статуса данного билета допустимо.
            if (ticketDao.read(sessionId, row, number).getStatus() == statusCheck) {
                ticketDao.read(sessionId, row, number).setStatus(status);
                ticketDao.saveData();
                System.out.println("Статус успешно изменен!");
                return;
            } else {
                System.out.println("Нельзя задать данный статус для этого места!");
                System.out.println("Введите q, если хотите отменить действие (что-то другое если хотите повторить попытку):");
                System.out.print("Ваш ввод> ");
                choice = scanner.nextLine();
                if (Objects.equals(choice, "q")) {
                    return;
                }
            }
        }
    }

    // Метод для продажи билета.
    private static void sellTicket() throws IOException {
        if (sessionDao.getAll().isEmpty()) {
            System.out.println("Нет доступных сеансов в прокате. Возврат в главное меню...");
        } else {
            int id = getTicketSession();
            printTickets(id); // Выводим все билеты на данный сеанс.
            changeTicketStatus(id, TicketStatus.Sold); // Меняем статус билета на Sold.
            System.out.println("Возврат в главное меню...");
        }
    }

    // Метод для возврата билета.
    private static void refundTicket() throws IOException {
        if (sessionDao.getAll().isEmpty()) {
            System.out.println("Нет доступных сеансов в прокате. Возврат в главное меню...");
        } else {
            int id = getTicketSession();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm");
            try {
                // Проверяем, начался ли данный сеанс.
                Date sessionTime = formatter.parse(sessionDao.read(id).getStartTime());
                if (sessionTime.getTime() < new java.util.Date().getTime()) {
                    System.out.println("Нельзя оформить возврат, так как этот сеанс уже начался!");
                    System.out.println("Возврат в главное меню...");
                    return;
                }
                printTickets(id); // Выводим все билеты на данный сеанс.
                changeTicketStatus(id, TicketStatus.Free); // Меняем статус билета на Free.
                System.out.println("Возврат в главное меню...");
            }
            catch (Exception ex) {
                System.out.println("Error: Непредвиденная ошибка!");
            }
        }
    }

    // Метод для отметки места на сеансе занятым.
    private static void occupyTicket() throws IOException {
        if (sessionDao.getAll().isEmpty()) {
            System.out.println("Нет доступных сеансов в прокате. Возврат в главное меню...");
        } else {
            int id = getTicketSession();
            printTickets(id); // Выводим все билеты на данный сеанс.
            changeTicketStatus(id, TicketStatus.Occupied); // Меняем статус билета на Occupied.
            System.out.println("Возврат в главное меню...");
        }
    }
}
