/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PL.UpdateBookInformation;

import DAL.User;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author tunguyen
 */
public class BookDetailUpdateForm extends Application {

    private BookDetailUpdateListener listener;

    public BookDetailUpdateListener getListener() {
        return listener;
    }

    /**
     * Start render new window
     * @param primaryStage
     * @throws Exception if cannot render
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetailUpdate.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Update Book Information");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Start render new window with Welcome message to User
     * @param stage
     * @param user an user that logged in
     */
    public void start(Stage stage, User user) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetailUpdate.fxml"));
        try {
            BorderPane root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Book Information");
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        
        listener = loader.<BookDetailUpdateListener>getController();
        if (user != null) {
            listener.setUser(user);
            listener.setWelcomeLabel(user.getName());
        } else {
            System.out.println("Error db.");
            stage.close();
        }

        stage.setTitle("Update Book Information");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
