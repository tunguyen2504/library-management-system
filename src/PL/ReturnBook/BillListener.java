package PL.ReturnBook;

import BLL.ReturnBookController;
import DAL.BorrowerInfo;
import DAL.BorrowingInfo;
import PL.AlertMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BillListener {
    private ArrayList<BorrowingInfo> infos;
    private BorrowerInfo borrower = new BorrowerInfo();
    private ReturnBookController controller = new ReturnBookController();
    private double addFee;

    @FXML
    private Label messageLabel;

    @FXML
    private HBox bottomHBox;

    public void setAddFee(double addFee) {
        this.addFee = addFee;
    }

    public void setInfos(ArrayList<BorrowingInfo> infos) {
        this.infos = infos;
    }

    /**
     * handle Cancel button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();

        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    /**
     * handle Confirm button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleConfirmButton(ActionEvent actionEvent) {
        ReturnBookController controller = new ReturnBookController();
        AlertMessage alert = new AlertMessage();
        alert.setHeader(null);

        for (BorrowingInfo one : infos) {
            boolean status = controller.update(one);
            if (!status) {
                alert.setMessage("Internal error");
                alert.execute(Alert.AlertType.ERROR);
            }
        }
        alert.setMessage("Successful update information");
        alert.execute(Alert.AlertType.INFORMATION);
    }

    /**
     * init scene with table
     */
    public void init (){
        if (bottomHBox != null) {
            bottomHBox.setAlignment(Pos.BASELINE_LEFT);
            bottomHBox.getChildren().clear();
        }

        if (infos != null) {
            bottomHBox.getChildren().clear();
            bottomHBox.getChildren().add(addTable());
        } else {
            Text resultText = new Text("No result");
            resultText.setId("resultText");

            if (bottomHBox != null) {
                bottomHBox.setAlignment(Pos.BASELINE_CENTER);
                bottomHBox.getChildren().clear();
                bottomHBox.getChildren().addAll(resultText);
            }
        }
    }

    /**
     * add table with bill information
     * @return GridPane object table-like
     */
    private GridPane addTable() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        gridPane.setVgap(7);
        gridPane.setHgap(7);

        controller.setBorrower(infos.get(0));
        borrower = controller.getBorrower();

        double booksPrice = 0;

        if (borrower.getIsHUST()){
            booksPrice = 0;
        } else {
            for (BorrowingInfo one : infos)
                booksPrice += controller.getBook(one).getPrice();
        }

        Label temp;

        temp = new Label("Borrower name");
        gridPane.add(temp, 0, 0);

        temp = new Label(borrower.getName());
        gridPane.add(temp, 1, 0);

        temp = new Label("Card number");
        gridPane.add(temp, 0, 1);

        temp = new Label(Integer.toString(borrower.getCardID()));
        gridPane.add(temp, 1, 1);

        temp = new Label("Price");
        gridPane.add(temp, 0, 2);

        temp = new Label(Double.toString(booksPrice*5/100) + "VND");
        gridPane.add(temp, 1, 2);

        temp = new Label("Additional fee");
        gridPane.add(temp, 0, 3);

        temp = new Label(Double.toString(addFee) + "VND");
        gridPane.add(temp, 1, 3);

        temp = new Label("Total");
        gridPane.add(temp, 0, 4);

        double total = addFee + booksPrice*5/100;

        temp = new Label(Double.toString(total) + "VND");
        gridPane.add(temp, 1, 4);

        return gridPane;
    }
}
