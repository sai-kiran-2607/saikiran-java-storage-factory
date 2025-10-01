package org.dev;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AbstractTodoStorageTest {

    AbstractTodoStorage storage;
    Connection conn;

    // JDBC URLs for MySQL and PostgreSQL
    String[] jdbcUrls = {
            "jdbc:mysql://localhost:3306/todo?useSSL=false&serverTimezone=UTC",
            "jdbc:postgresql://localhost:5432/todo?ssl=false"
    };
    String mysqlUser = "root";
    String mysqlPass = "root";
    String postgresUser = "postgres";
    String postgresPass = "root";

    @BeforeAll
    void loadDrivers() throws ClassNotFoundException {
        // Load JDBC drivers
        Class.forName("com.mysql.cj.jdbc.Driver");
        Class.forName("org.postgresql.Driver");
    }

    @BeforeEach
    void setUp(TestInfo testInfo) throws SQLException {
        // Pick database based on test name (alternatively, loop over both)
        String db = testInfo.getDisplayName().contains("MySQL") ? jdbcUrls[0] : jdbcUrls[1];
        String user = db.contains("mysql") ? mysqlUser : postgresUser;
        String pass = db.contains("mysql") ? mysqlPass : postgresPass;

        conn = DriverManager.getConnection(db, user, pass);

        // Create a concrete subclass for AbstractTodoStorage
        storage = new AbstractTodoStorage(db, user, pass) {

        };

        // Clean table before each test
        conn.createStatement().executeUpdate("DELETE FROM todos");
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    @DisplayName("Test CRUD on MySQL")
    void testCRUDMySQL() throws SQLException {
        testCRUD( storage);
    }

    @Test
    @DisplayName("Test CRUD on PostgreSQL")
    void testCRUDPostgres() throws SQLException {
        testCRUD( storage);
    }

    // Common CRUD tests
    void testCRUD(AbstractTodoStorage storage) throws SQLException {
        // Save
        Todo todo = new Todo("1", "Title", "Desc", false);
        storage.save(todo);

        // Retrieve
        Todo retrieved = storage.retrieve("1");
        assertNotNull(retrieved);
        assertEquals("Title", retrieved.getTitle());

        // Update
        todo.setTitle("Updated");
        todo.setCompleted(true);
        storage.update(todo);

        Todo updated = storage.retrieve("1");
        assertEquals("Updated", updated.getTitle());
        assertTrue(updated.isCompleted());

        // Retrieve all
        List<Todo> todos = storage.retrieveAll();
        assertEquals(1, todos.size());

        // Delete
        storage.delete("1");
        Todo deleted = storage.retrieve("1");
        assertNull(deleted);
    }
}
