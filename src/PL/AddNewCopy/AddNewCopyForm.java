package PL.AddNewCopy;

import DAL.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AddNewCopyForm extends Application {
	
    /**
     * Start scene
     * @param primaryStage stage of the program
     * @throws Exception if can't start
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddNewCopy.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add New Book Copy");
//        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * start scene
     * @param stage stage of the program
     * @param user user who using program
     * @param bookID the ID of the new book copy
     * @param totalCopy the number of total copy of the book
     */
    public void start(Stage stage, User user, String bookID, String totalCopy) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewCopy.fxml"));
        try {
            BorderPane root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        AddNewCopyListener listener = loader.<AddNewCopyListener>getController();
        //listener.setBookID(bookID);
        //listener.setSequence(Integer.parseInt(totalCopy) + 1);
        if (user != null){
            listener.setBookID(bookID);
            listener.setSequence(Integer.parseInt(totalCopy) + 1);
            listener.setUser(user);
            listener.setWelcomeLabel(user.getName());
        }
        else {
            System.out.println("Error db.");
            stage.close();
        }

        stage.setTitle("Add New Book Copy");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
