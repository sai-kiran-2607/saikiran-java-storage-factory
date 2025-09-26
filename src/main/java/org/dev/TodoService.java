package org.dev;
import java.sql.SQLException;
import java.util.List;

public class TodoService {
    private TodoStorage storage;

    public TodoService(TodoStorage storage) {
        this.storage = storage;
    }

    public void createTodo(Todo todo) throws SQLException {
        storage.save(todo);
    }

    public Todo getTodoById(String id) throws SQLException {
        return storage.retrieve(id);
    }

    public List<Todo> getAllTodos() throws SQLException {
        return storage.retrieveAll();
    }

    public void updateTodo(Todo todo) throws SQLException {
        storage.update(todo);
    }

    public void deleteTodo(String id) throws SQLException {
        storage.delete(id);
    }
}
