package org.dev;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLTodoStorage implements TodoStorage {

    Connection conn;
    public MySQLTodoStorage(String url, String user, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void save(Todo todo) throws SQLException {
        String sql = "INSERT INTO todos (id, title, description, completed) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, todo.getId());
        stmt.setString(2, todo.getTitle());
        stmt.setString(3, todo.getDescription());
        stmt.setBoolean(4, todo.isCompleted());
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public Todo retrieve(String id) throws SQLException {
        String sql = "SELECT * FROM todos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        Todo todo = null;
        if (rs.next()) {
            todo = new Todo(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBoolean("completed")
            );
        }
        rs.close();
        stmt.close();
        return todo;
    }

    @Override
    public List<Todo> retrieveAll() throws SQLException {
        String sql = "SELECT * FROM todos";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Todo> todos = new ArrayList<>();
        while (rs.next()) {
            todos.add(new Todo(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBoolean("completed")
            ));
        }
        rs.close();
        stmt.close();
        return todos;
    }

    @Override
    public void update(Todo todo) throws SQLException {
        String sql = "UPDATE todos SET title = ?, description = ?, completed = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, todo.getTitle());
        stmt.setString(2, todo.getDescription());
        stmt.setBoolean(3, todo.isCompleted());
        stmt.setString(4, todo.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM todos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.executeUpdate();
        stmt.close();
    }
}
