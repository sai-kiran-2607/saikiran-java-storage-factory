package org.dev;


import java.sql.SQLException;

public class StorageFactory {
    public static TodoStorage getStorage(String type) throws SQLException {
        switch (type.toLowerCase()) {
            case "mysql":
                return new MySQLTodoStorage("jdbc:mysql://localhost:3306/todo", "root", "root");
            case "postgresql":
                return new PostgreSQLTodoStorage("jdbc:postgresql://localhost:5432/todo_db", "postgres", "password");
                default:
                throw new IllegalArgumentException("Unknown storage type");
        }
    }
}
