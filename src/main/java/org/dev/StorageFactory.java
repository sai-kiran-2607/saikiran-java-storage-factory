package org.dev;

import java.sql.SQLException;

public class StorageFactory {

    private StorageFactory() {

    }

    public static TodoStorage getStorage(String type) throws SQLException {
        switch (type.toLowerCase()) {
            case "mysql":
                return new MySQLTodoStorage(
                        "jdbc:mysql://localhost:3306/todo?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "root"
                );
            case "postgresql":
                return new PostgreSQLTodoStorage(
                        "jdbc:postgresql://localhost:5432/todo",
                        "postgres",
                        "root"
                );
            case "mongodb":
                return new MongoTodoStorage("mongodb://localhost:27017", "todos");
            default:
                throw new IllegalArgumentException("Unknown storage type");
        }
    }
}
