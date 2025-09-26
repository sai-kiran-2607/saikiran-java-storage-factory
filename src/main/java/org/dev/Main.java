package org.dev;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Select Database (mysql/postgresql): ");
        String dbType = sc.nextLine();

        TodoStorage storage = StorageFactory.getStorage(dbType);
        TodoService service = new TodoService(storage);

        while (true) {
            System.out.println("\n1. Create TODO\n2. Retrieve TODO By ID\n3. Update TODO\n4. Delete TODO\n5. Retrieve All TODOS\n6. Exit");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("ID:");
                    String id=sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    service.createTodo(new Todo(id, title, desc, false));
                    System.out.println("TODO Created!");
                    break;
                case 2:
                    System.out.print("ID: ");
                    String rid = sc.nextLine();
                    Todo t = service.getTodoById(rid);
                    System.out.println(t != null ? t : "TODO not found");
                    break;
                case 3:
                    System.out.print("ID to Update: ");
                    String uid = sc.nextLine();
                    Todo ut = service.getTodoById(uid);
                    if (ut != null) {
                        System.out.print("New Title: ");
                        ut.setTitle(sc.nextLine());
                        System.out.print("New Description: ");
                        ut.setDescription(sc.nextLine());
                        System.out.print("Completed (true/false): ");
                        ut.setCompleted(Boolean.parseBoolean(sc.nextLine()));
                        service.updateTodo(ut);
                        System.out.println("TODO Updated!");
                    } else System.out.println("TODO not found");
                    break;
                case 4:
                    System.out.print("ID to Delete: ");
                    String did = sc.nextLine();
                    service.deleteTodo(did);
                    System.out.println("TODO Deleted!");
                    break;
                case 5:
                    List<Todo> todos = service.getAllTodos();
                    for (Todo todo : todos) {
                        System.out.println(todo);
                    }
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Operation");
            }
        }
    }
}
