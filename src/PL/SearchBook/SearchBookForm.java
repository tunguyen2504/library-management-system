package PL.SearchBook;

import DAL.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SearchBookForm extends Application {
	
    /**
     * Start scene
     * @param primaryStage stage of the program
     * @throws Exception if can't start
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SearchBook.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search Book");
//        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * start scene
     * @param stage stage of the program
     * @param user user who using program
     */
    public void start(Stage stage, User user) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchBook.fxml"));
        try {
            BorderPane root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        SearchBookListener listener = loader.<SearchBookListener>getController();
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

    public static void main(String[] args) {
        launch(args);
    }
}
