package org.dev;

import java.sql.SQLException;

public class MySQLTodoStorage extends AbstractTodoStorage {
    public MySQLTodoStorage(String url, String user, String password) throws SQLException {
        super(url, user, password);
    }
}
