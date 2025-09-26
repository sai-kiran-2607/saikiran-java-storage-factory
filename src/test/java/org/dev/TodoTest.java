package org.dev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void testNoArgConstructorAndSetters() {
        Todo todo = new Todo();
        todo.setId("1");
        todo.setTitle("Test Title");
        todo.setDescription("Test Description");
        todo.setCompleted(true);

        assertEquals("1", todo.getId());
        assertEquals("Test Title", todo.getTitle());
        assertEquals("Test Description", todo.getDescription());
        assertTrue(todo.isCompleted());
    }

    @Test
    void testAllArgsConstructor() {
        Todo todo = new Todo("2", "Title 2", "Description 2", false);

        assertEquals("2", todo.getId());
        assertEquals("Title 2", todo.getTitle());
        assertEquals("Description 2", todo.getDescription());
        assertFalse(todo.isCompleted());
    }

    @Test
    void testToString() {
        Todo todo = new Todo("3", "Title 3", "Description 3", true);
        String expected = "Todo [id=3, title=Title 3, description=Description 3, completed=true]";
        assertEquals(expected, todo.toString());
    }
}

