package com.example.movietheater;

import com.example.movietheater.controller.SceneController;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.Context;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class MovieTheaterApplication extends Application {
    private static MovieTheaterApplication instance;  // Declare instance
    private static SceneController sceneController;
    private static InMemoryDatabase inMemoryDatabase;
    private static final String DATA_FILE = "database.ser";

    public MovieTheaterApplication() {
        this.inMemoryDatabase = loadDatabase();  // Load the database on startup
    }

    // Method to serialize the database state to a file
    private void saveDatabase() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(inMemoryDatabase);
            System.out.println("Database state saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to deserialize the database state from a file
    private InMemoryDatabase loadDatabase() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (InMemoryDatabase) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Serialized file not found. Starting with a fresh database.");
            return new InMemoryDatabase();  // Return a fresh database if file doesn't exist
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new InMemoryDatabase();  // Handle any other exceptions by returning a fresh database
        }
    }

    // Ensure to save the database when the application closes
    @Override
    public void stop() {
        System.out.println("Application closing. Saving database state...");
        saveDatabase();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        inMemoryDatabase = new InMemoryDatabase();
        sceneController = new SceneController(primaryStage);

        inMemoryDatabase = loadDatabase();
        instance = this;  // Initialize the instance reference

        // Pass the InMemoryDatabase to the Login scene via the Context
        sceneController.changeScene("Login", new Context(null, null, inMemoryDatabase));
    }

    public static InMemoryDatabase getInMemoryDatabase() {
        return inMemoryDatabase;
    }

    public static SceneController getSceneController() {
        return sceneController;
    }

    // Get the current instance of MovieTheaterApplication
    public static MovieTheaterApplication getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch();
    }
}
