package org.dev;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        logger.info("Select Database (mysql/postgresql): ");
        String dbType = sc.nextLine();

        TodoStorage storage = StorageFactory.getStorage(dbType);
        TodoService service = new TodoService(storage);
        boolean running = true;
        while (running) {
            logger.info("\n1. Create TODO\n2. Retrieve TODO By ID\n3. Update TODO\n4. Delete TODO\n5. Retrieve All TODOS\n6. Exit");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    logger.info("ID: ");
                    String id = sc.nextLine();
                    logger.info("Title: ");
                    String title = sc.nextLine();
                    logger.info("Description: ");
                    String desc = sc.nextLine();
                    service.createTodo(new Todo(id, title, desc, false));
                    logger.info("TODO Created!");
                }
                case 2 -> {
                    logger.info("ID: ");
                    String rid = sc.nextLine();
                    Todo t = service.getTodoById(rid);
                    if (t != null) {
                        logger.info("{}", t);
                    } else {
                        logger.info("Todo not found");
                    }

                }
                case 3 -> {
                    logger.info("ID to Update: ");
                    String uid = sc.nextLine();
                    Todo ut = service.getTodoById(uid);
                    if (ut != null) {
                        logger.info("New Title: ");
                        ut.setTitle(sc.nextLine());
                        logger.info("New Description: ");
                        ut.setDescription(sc.nextLine());
                        logger.info("Completed (true/false): ");
                        ut.setCompleted(Boolean.parseBoolean(sc.nextLine()));
                        service.updateTodo(ut);
                        logger.info("TODO Updated!");
                    } else {
                        logger.info("TODO not found");
                    }
                }
                case 4 -> {
                    logger.info("ID to Delete: ");
                    String did = sc.nextLine();
                    service.deleteTodo(did);
                    logger.info("TODO Deleted!");
                }
                case 5 -> {
                    List<Todo> todos = service.getAllTodos();
                    if (todos.isEmpty()) {
                        logger.info("No TODOs found");
                    } else {
                        todos.forEach(todo -> logger.info(todo.toString()));
                    }
                }
                case 6 -> {

                    running=false;
                    logger.info("Exiting...");

                }
                default -> logger.warn("Invalid Operation");
            }
        }
    }
}
