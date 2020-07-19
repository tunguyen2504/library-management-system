package PL.BorrowBook;

import BLL.BorrowBookController;
import DAL.BookInfo;
import DAL.BorrowerInfo;
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
import javafx.stage.Stage;

import java.util.ArrayList;

public class CheckOutListener {
    private BorrowerInfo borrower;
    private ArrayList<BookInfo> select_list = new ArrayList<>();
    @FXML
    private HBox bookHBox;
    @FXML
    private Label messageLabel;

    public void setBorrower(BorrowerInfo borrower) {
        this.borrower = borrower;
    }

    public void setSelect_list(ArrayList<BookInfo> select_list) {
        this.select_list = select_list;
    }

    /**
     * handle Cencel button
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
        BorrowBookController controller = new BorrowBookController();
        int status = controller.checkOut(this.borrower, this.select_list);
        AlertMessage alert = new AlertMessage();
        alert.setHeader(null);

        switch (status){
            case 1:
//                messageLabel.setText("Success borrowed.\n" +
//                        "You can now close this window.");
                alert.setMessage("Success borrowed.\n" +
                        "You can now close this window.");

                alert.execute(Alert.AlertType.INFORMATION);
                break;
            default:
                alert.setMessage("Internal error");
                alert.execute(Alert.AlertType.ERROR);
                break;
        }

    }

    /**
     * create table on load
     */
    public void init() {
        bookHBox.getChildren().add(addTable());
    }

    /**
     * add table with select_list
     * @return GridPane object table-like
     */
    private GridPane addTable() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        gridPane.setVgap(7);
        gridPane.setHgap(7);

        gridPane.add(new Label("No."), 0, 0);
        gridPane.add(new Label("BookNumber"), 1, 0);
        gridPane.add(new Label("Title"), 2, 0);
        gridPane.add(new Label("Author"), 3, 0);
        gridPane.add(new Label("ISBN"), 4, 0);
        gridPane.add(new Label("CopyNumber"), 5, 0);
        gridPane.add(new Label("Price"), 6, 0);

        double total = 0;
        for (int i = 0; i < select_list.size(); i++) {
            Label temp;

            temp = new Label(Integer.toString(i+1));
            temp.setId("no" + i);
            gridPane.add(temp, 0, i + 1);

            temp = new Label(select_list.get(i).getBookID());
            temp.setId("bookNumber" + i);
            gridPane.add(temp, 1, i + 1);

            temp = new Label(select_list.get(i).getTitle());
            temp.setId("title" + i);
            gridPane.add(temp, 2, i + 1);

            temp = new Label(select_list.get(i).getAuthor());
            temp.setId("author" + i);
            gridPane.add(temp, 3, i + 1);

            temp = new Label(select_list.get(i).getISBN());
            temp.setId("isbn" + i);
            gridPane.add(temp, 4, i + 1);

            temp = new Label(Integer.toString(select_list.get(i).getSequenceNumber()));
            temp.setId("sequenceNumber" + i);
            gridPane.add(temp, 5, i + 1);

            temp = new Label(Double.toString(select_list.get(i).getPrice()) + " VND");
            temp.setId("sequenceNumber" + i);
            gridPane.add(temp, 6, i + 1);

            total += select_list.get(i).getPrice();
        }

        Label temp = new Label("Total price: ");
        temp.setId("totalPrice");
        gridPane.add(temp, 5, select_list.size() + 1);

        temp = new Label(Double.toString(total) + " VND");
        temp.setId("totalPrice");
        gridPane.add(temp, 6, select_list.size() + 1);
        return gridPane;
    }
}
