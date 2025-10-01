package org.dev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class TodoServiceTest {

    private TodoStorage storage;
    private TodoService service;

    @BeforeEach
    void setUp() {
        storage = Mockito.mock(TodoStorage.class);
        service = new TodoService(storage);
    }

    @Test
    void testCreateTodo() throws SQLException {
        Todo todo = new Todo("1", "Learn Mockito", false);

        service.createTodo(todo);

        verify(storage, times(1)).save(todo);
    }

    @Test
    void testGetTodoById() throws SQLException {
        Todo todo = new Todo("1", "Learn JUnit", false);
        when(storage.retrieve("1")).thenReturn(todo);

        Todo result = service.getTodoById("1");

        assertNotNull(result);
        assertEquals("Learn JUnit", result.getTitle());
        verify(storage, times(1)).retrieve("1");
    }

    @Test
    void testGetAllTodos() throws SQLException {
        List<Todo> todos = Arrays.asList(
                new Todo("1", "Task 1", false),
                new Todo("2", "Task 2", true)
        );
        when(storage.retrieveAll()).thenReturn(todos);

        List<Todo> result = service.getAllTodos();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        verify(storage, times(1)).retrieveAll();
    }

    @Test
    void testUpdateTodo() throws SQLException {
        Todo todo = new Todo("1", "Updated Task", true);

        service.updateTodo(todo);

        verify(storage, times(1)).update(todo);
    }

    @Test
    void testDeleteTodo() throws SQLException {
        service.deleteTodo("1");

        verify(storage, times(1)).delete("1");
    }
}
