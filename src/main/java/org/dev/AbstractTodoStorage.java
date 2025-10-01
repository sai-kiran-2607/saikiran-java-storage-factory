package org.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public abstract class AbstractTodoStorage implements TodoStorage {

    protected Connection conn;
    protected static final Logger logger = LoggerFactory.getLogger(AbstractTodoStorage.class);

    protected AbstractTodoStorage(String url, String user, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void save(Todo todo) throws SQLException {
        String sql = "INSERT INTO todos (id, title, description, completed) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, todo.getId());
            stmt.setString(2, todo.getTitle());
            stmt.setString(3, todo.getDescription());
            stmt.setBoolean(4, todo.isCompleted());
            stmt.executeUpdate();
        }
    }

    @Override
    public Todo retrieve(String id) throws SQLException {
        String sql = "SELECT id, title, description, completed FROM todos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Todo(
                            rs.getString("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getBoolean("completed")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Todo> retrieveAll() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT id, title, description, completed FROM todos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                todos.add(new Todo(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("completed")
                ));
            }
        }
        return todos;
    }

    @Override
    public void update(Todo todo) throws SQLException {
        String sql = "UPDATE todos SET title = ?, description = ?, completed = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.isCompleted());
            stmt.setString(4, todo.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM todos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
                logger.error("Failed to close database connection", ignored);
            }
        }
    }
}

