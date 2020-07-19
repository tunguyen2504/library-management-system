package PL.ReturnBook;

import DAL.BorrowingInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BillForm extends Application {
    /**
     * Start scene
     * @param primaryStage stage of the program
     * @throws Exception if can't start
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

    }


    /**
     * Start scene
     * @param primaryStage stage of the program
     * @param infos list of borrowing info to print
     * @param addFee double additional fee
     */
    public void start(Stage primaryStage, ArrayList<BorrowingInfo> infos, double addFee) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Bill.fxml"));

        Stage stage = new Stage();

        // make parent un-usable when child is executing
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Can't create Bill window: " + e);
        }


        BillListener listener = loader.<BillListener>getController();

        if (infos != null) {
            if (!infos.isEmpty()) {
                listener.setAddFee(addFee);
                listener.setInfos(infos);
                listener.init();
            }
        }

        stage.setTitle("Bill");
        stage.show();
    }
}
