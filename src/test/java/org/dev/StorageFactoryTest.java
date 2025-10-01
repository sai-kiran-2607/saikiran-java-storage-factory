package org.dev;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StorageFactoryTest {

    @Test
    void testMySQLStorageCreation() {
        try {
            TodoStorage storage = StorageFactory.getStorage("mysql");
            assertNotNull(storage);
            assertTrue(storage instanceof MySQLTodoStorage);
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    void testPostgreSQLStorageCreation() {
        try {
            TodoStorage storage = StorageFactory.getStorage("postgresql");
            assertNotNull(storage);
            assertTrue(storage instanceof PostgreSQLTodoStorage);
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    void testUnknownStorageType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            StorageFactory.getStorage("unknown");
        });
        assertEquals("Unknown storage type", exception.getMessage());
    }
}

