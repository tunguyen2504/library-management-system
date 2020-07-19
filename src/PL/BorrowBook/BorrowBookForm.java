package PL.BorrowBook;

import DAL.BorrowerInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BorrowBookForm extends Application {

    /**
     * Start scene
     * @param primaryStage stage of the program
     * @throws Exception if can't start
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("BorrowBook.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Borrow Book");
//        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * start scene
     * @param primaryStage stage of the program
     * @param borrower borrower who using program
     */
    public void start(Stage primaryStage, BorrowerInfo borrower) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowBook.fxml"));
        try {
            BorderPane root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        BorrowBookListener listener = loader.<BorrowBookListener>getController();
        if (borrower != null){
            listener.setBorrower(borrower);
            listener.setWelcomeLabel(borrower.getName());
        }
        else {
            System.out.println("Error db.");
            primaryStage.close();
        }

        primaryStage.setTitle("Borrow Book");
        primaryStage.show();

        listener.checkExpire();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
