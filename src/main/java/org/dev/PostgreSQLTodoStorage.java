package org.dev;

import java.sql.SQLException;

public class PostgreSQLTodoStorage extends AbstractTodoStorage {
    public PostgreSQLTodoStorage(String url, String user, String password) throws SQLException {
        super(url, user, password);
    }
}