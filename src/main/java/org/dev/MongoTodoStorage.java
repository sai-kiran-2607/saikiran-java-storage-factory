package org.dev;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoTodoStorage implements TodoStorage {

    private static final String FIELD_ID = "id";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_COMPLETED = "completed";

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoTodoStorage(String uri, String dbName) {
        this.mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase(dbName);
        this.collection = database.getCollection("todos");
    }

    @Override
    public void save(Todo todo) {
        Document doc = new Document(FIELD_ID, todo.getId())
                .append(FIELD_TITLE, todo.getTitle())
                .append(FIELD_DESCRIPTION, todo.getDescription())
                .append(FIELD_COMPLETED, todo.isCompleted());
        collection.insertOne(doc);
    }

    @Override
    public Todo retrieve(String id) {
        Document doc = collection.find(Filters.eq(FIELD_ID, id)).first();
        return doc != null ? new Todo(
                doc.getString(FIELD_ID),
                doc.getString(FIELD_TITLE),
                doc.getString(FIELD_DESCRIPTION),
                doc.getBoolean(FIELD_COMPLETED, false)
        ) : null;
    }

    @Override
    public List<Todo> retrieveAll() {
        List<Todo> todos = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                todos.add(new Todo(
                        doc.getString(FIELD_ID),
                        doc.getString(FIELD_TITLE),
                        doc.getString(FIELD_DESCRIPTION),
                        doc.getBoolean(FIELD_COMPLETED, false)
                ));
            }
        }
        return todos;
    }

    @Override
    public void update(Todo todo) {
        collection.updateOne(Filters.eq(FIELD_ID, todo.getId()), Updates.combine(
                Updates.set(FIELD_TITLE, todo.getTitle()),
                Updates.set(FIELD_DESCRIPTION, todo.getDescription()),
                Updates.set(FIELD_COMPLETED, todo.isCompleted())
        ));
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(Filters.eq(FIELD_ID, id));
    }

    public void close() {
        // Close the MongoClient connection when done
        mongoClient.close();
    }
}
