package kpo.homework.one.dao;

import kpo.homework.one.model.Ticket;
import kpo.homework.one.model.TicketStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Класс TicketDaoImpl, реализующий интерфейс Dao.
public class TicketDaoImpl implements Dao<Ticket> {

    // Коллекция билетов.
    private static final ArrayList<Ticket> tickets = new ArrayList<>();

    // Путь к json-файлу с данными.
    private static final String TICKETS_PATH = "data/tickets.json";

    // Метод для получения всех объектов из коллекции.
    @Override
    public List<Ticket> getAll() { return tickets; }

    // Метод для получения билетов на данный сеанс (по id) из коллекции.
    public List<Ticket> getTickets(int sessionId) {
        ArrayList<Ticket> myTickets = new ArrayList<Ticket>();
        for (Ticket ticket : tickets) {
            if (ticket.getSessionId() == sessionId) {
                myTickets.add(ticket);
            }
        }
        return myTickets;
    }

    // Метод для записи коллекции в файл.
    @Override
    public void saveData() {
        try {
            File file = new File(TICKETS_PATH);
            objectMapper.writeValue(file, tickets);
        }
        catch (Exception ex) {
            System.out.println("Error: не удалось сохранить данные в файл!");
        }
    }

    // Метод для добавления объекта в коллекцию.
    @Override
    public void create(Ticket ticket) {
        for (Ticket ticket1 : getTickets(ticket.getSessionId())) {
            if (ticket1.getRow() == ticket.getRow() && ticket1.getNumber() == ticket.getNumber()) {
                return;
            }
        }
        tickets.add(ticket);
        saveData();
    }

    // Метод для удаления объекта из коллекции.
    @Override
    public void delete(Ticket ticket) {
        tickets.remove(ticket);
        saveData();
    }

    // Метод для получения билета из коллекции по id сеанса, номеру ряда и номеру места.
    public Ticket read(int sessionId, int row, int number) {
        for (Ticket ticket : getTickets(sessionId)) {
            if (ticket.getRow() == row && ticket.getNumber() == number) {
                return ticket;
            }
        }
        return null;
    }

    // Метод для генерации билетов на сеанс с данным id.
    public void generateTickets(int sessionId) {
        for (int i = 1; i < 11; ++i) {
            for (int j = 1; j < 11; ++j) {
                tickets.add(new Ticket(sessionId, i, j, TicketStatus.Free));
            }
        }
        saveData();;
    }
}
