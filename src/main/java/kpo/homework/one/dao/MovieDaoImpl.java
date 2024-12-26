package kpo.homework.one.dao;

import kpo.homework.one.model.Movie;

import javax.swing.*;
import java.io.File;
import java.util.*;

// Класс MovieDaoImpl, реализующий интерфейс Dao.
public class MovieDaoImpl implements Dao<Movie> {

    // Коллекция фильмов.
    private static final Map<Integer, Movie> movies = new HashMap<>();

    // Путь к json-файлу с данными.
    private static final String MOVIES_PATH = "data/movies.json";

    // Метод для получения всех объектов из коллекции.
    @Override
    public List<Movie> getAll() {
        return new ArrayList<>(movies.values());
    }


    // Метод для записи коллекции в файл.
    @Override
    public void saveData() {
        try {
            File file = new File(MOVIES_PATH);
            objectMapper.writeValue(file, new ArrayList<>(movies.values()));
        }
        catch (Exception ex) {
            System.out.println("Error: не удалось сохранить данные в файл!");
        }
    }

    // Метод для добавления объекта в коллекцию.
    @Override
    public void create(Movie movie) {
        movies.put(movie.getId(), movie);
        saveData();
    }

    // Метод для удаления объекта из коллекции.
    @Override
    public void delete(Movie movie) {
        movies.remove(movie.getId());
        saveData();
    }

    // Метод для получения объекта из коллекции по id.
    public Movie read(int id) {
        return movies.get(id);
    }
}
