package kpo.homework.one.dao;

import kpo.homework.one.model.Session;

import java.io.File;
import java.util.*;

// Класс SessionDaoImpl, реализующий интерфейс Dao.
public class SessionDaoImpl implements Dao<Session> {

    // Коллекция сеансов.
    private static final Map<Integer, Session> sessions = new HashMap<>();

    // Путь к json-файлу с данными.
    private static final String SESSIONS_PATH = "data/sessions.json";

    // Метод для получения всех объектов из коллекции.
    @Override
    public List<Session> getAll() { return new ArrayList<>(sessions.values()); }

    // Метод для получения сеансов данного фильма (по id) из коллекции.
    public List<Session> getSessions(int filmId) {
        ArrayList<Session> mySessions = new ArrayList<Session>();
        for (Session session : sessions.values()) {
            if (session.getFilmId() == filmId) {
                mySessions.add(session);
            }
        }
        return mySessions;
    }

    // Метод для записи коллекции в файл.
    @Override
    public void saveData() {
        try {
            File file = new File(SESSIONS_PATH);
            objectMapper.writeValue(file, new ArrayList<>(sessions.values()));
        }
        catch (Exception ex) {
            System.out.println("Error: не удалось сохранить данные в файл!");
        }
    }

    // Метод для добавления объекта в коллекцию.
    @Override
    public void create(Session session) {
        sessions.put(session.getId(), session);
        saveData();
    }

    // Метод для удаления объекта из коллекции.
    @Override
    public void delete(Session session) {
        sessions.remove(session.getId());
        saveData();
    }

    // Метод для получения объекта из коллекции по id.
    public Session read(int id) {
        return sessions.get(id);
    }
}
