package org.dev;
import java.sql.SQLException;
import java.util.List;

public interface TodoStorage {
    void save(Todo todo) throws SQLException;
    Todo retrieve(String id) throws SQLException;
    List<Todo> retrieveAll() throws SQLException;
    void update(Todo todo) throws SQLException;
    void delete(String id) throws SQLException;
}

