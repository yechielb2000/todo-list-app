package com.example.todolist;

import com.example.todolist.secrets.Secrets;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoInit {

    private MongoDatabase db;

    public MongoInit() {
        MongoClientURI uri = new MongoClientURI("mongodb://"+ Secrets.user +":"+ Secrets.password + Secrets.rest);
        MongoClient mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(uri.getDatabase());
    }

    public void createNewUser(String name, String email, String password){

        MongoCollection<BasicDBObject> collection = db.getCollection("users", BasicDBObject.class);
        BasicDBObject document = new BasicDBObject();
        document.put("name", name);
        document.put("email", email);
        document.put("password", password);
        collection.insertOne(document);
    }

    public void createNewTask(String title, String text, long createAt, long deadlineDate){

        MongoCollection<BasicDBObject> collection = db.getCollection("tasks", BasicDBObject.class);
        BasicDBObject document = new BasicDBObject();
        document.put("title", title);
        document.put("text", text);
        document.put("createdAt", createAt);
        document.put("deadlineDate", deadlineDate);
        collection.insertOne(document);
    }

}
