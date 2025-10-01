package org.dev;

import org.junit.jupiter.api.*;
import java.io.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }


    private void simulateMainInput(String input) throws SQLException {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main.main(new String[]{});
    }

    @Test
    void testMainFlow() throws SQLException {
        String simulatedInput = String.join(System.lineSeparator(),
                "mysql",
                "1", "1", "Test Title", "Test Desc",
                "2", "1",
                "3", "1", "Updated Title", "Updated Desc", "true",
                "4", "1",
                "5",
                "6"
        );

        simulateMainInput(simulatedInput);

        String output = outContent.toString();
        assertTrue(output.contains("TODO Created!"));
        assertTrue(output.contains("TODO Updated!"));
        assertTrue(output.contains("TODO Deleted!"));
        assertTrue(output.contains("Exiting..."));
    }

    @Test
    void testInvalidChoice() throws SQLException {
        String simulatedInput = String.join(System.lineSeparator(),
                "mysql",
                "99",
                "6"
        );

        simulateMainInput(simulatedInput);

        String output = outContent.toString();
        assertTrue(output.contains("Invalid Operation"));
        assertTrue(output.contains("Exiting..."));
    }

    @Test
    void testRetrieveNonExistentTodo() throws SQLException {
        String simulatedInput = String.join(System.lineSeparator(),
                "mysql",
                "2", "nonexistent-id",
                "6"
        );

        simulateMainInput(simulatedInput);

        String output = outContent.toString();
        assertTrue(output.contains("Todo not found"));
        assertTrue(output.contains("Exiting..."));
    }
}
