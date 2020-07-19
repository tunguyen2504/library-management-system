package PL.ReturnBook;

import DAL.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReturnBookForm extends Application {

    /**
     * Start scene
     * @param primaryStage stage of the program
     * @throws Exception if can't start
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnBook.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Return Book");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void start(Stage stage, User user) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnBook.fxml"));
        try {
            BorderPane root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        ReturnBookListener listener = loader.<ReturnBookListener>getController();
        if (user != null){
            listener.setUser(user);
            listener.setWelcomeLabel(user.getName());
        }
        else {
            System.out.println("Error db.");
            stage.close();
        }

        stage.setTitle("Search Book");
        stage.show();
    }
    public static void main (String[] args){
        launch(args);
    }
}
