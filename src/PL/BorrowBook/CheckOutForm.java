package PL.BorrowBook;

import DAL.BookInfo;
import DAL.BorrowerInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CheckOutForm extends Application {

    /**
     * Start scene
     * @param primaryStage stage of the program
     * @throws Exception if can't start
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CheckOut.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CheckOut");
        primaryStage.show();
    }

    /**
     * Start scene
     * @param primaryStage stage of the program
     * @param borrower borrower using program
     * @param select_list selected book list
     */
    public void start(Stage primaryStage, BorrowerInfo borrower, ArrayList<BookInfo> select_list){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOut.fxml"));

        Stage stage = new Stage();
        
        // make parent un-usable when child is executing
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        CheckOutListener listener = loader.<CheckOutListener>getController();
        if (borrower != null){
            listener.setBorrower(borrower);
            listener.setSelect_list(select_list);
            listener.init();
        }
        else {
            System.out.println("Error db.");
            stage.close();
        }

        stage.setTitle("Checkout");
        stage.show();
    }
}
