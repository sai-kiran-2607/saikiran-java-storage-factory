package org.dev;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoTodoStorageTest {

    private MongoTodoStorage storage;

    @BeforeAll
    void setUp() {
        // Use your local MongoDB URI and database name
        storage = new MongoTodoStorage("mongodb://localhost:27017", "todos");
        // Clean up the collection before running tests
        storage.retrieveAll().forEach(todo -> storage.delete(todo.getId()));
    }

    @AfterAll
    void tearDown() {
        if (storage != null) {
            storage.close();
        }
    }

    @Test
    void testSaveAndRetrieve() {
        Todo todo = new Todo("1", "Test Todo", "Test Description", false);
        storage.save(todo);

        Todo retrieved = storage.retrieve("1");
        assertNotNull(retrieved);
        assertEquals("1", retrieved.getId());
        assertEquals("Test Todo", retrieved.getTitle());
        assertEquals("Test Description", retrieved.getDescription());
        assertFalse(retrieved.isCompleted());

        storage.delete("1");
    }

    @Test
    void testRetrieveAll() {
        Todo todo1 = new Todo("2", "Todo 1", "Desc 1", false);
        Todo todo2 = new Todo("3", "Todo 2", "Desc 2", true);

        storage.save(todo1);
        storage.save(todo2);

        List<Todo> todos = storage.retrieveAll();
        assertTrue(todos.size() >= 2);

        storage.delete("2");
        storage.delete("3");
    }

    @Test
    void testUpdate() {
        Todo todo = new Todo("4", "Original", "Original Desc", false);
        storage.save(todo);

        todo.setTitle("Updated");
        todo.setDescription("Updated Desc");
        todo.setCompleted(true);
        storage.update(todo);

        Todo updated = storage.retrieve("4");
        assertNotNull(updated);
        assertEquals("Updated", updated.getTitle());
        assertEquals("Updated Desc", updated.getDescription());
        assertTrue(updated.isCompleted());

        storage.delete("4");
    }

    @Test
    void testDelete() {
        Todo todo = new Todo("5", "To Delete", "Desc", false);
        storage.save(todo);

        storage.delete("5");
        Todo deleted = storage.retrieve("5");
        assertNull(deleted);
    }
}
