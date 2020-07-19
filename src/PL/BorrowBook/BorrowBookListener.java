package PL.BorrowBook;

import BLL.BorrowBookController;
import DAL.BookInfo;
import DAL.BorrowerInfo;
import PL.AlertMessage;
import PL.Login.LoginForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BorrowBookListener {
    private BorrowBookController controller = new BorrowBookController();
    private ArrayList<BookInfo> book_list;
    private ArrayList<BookInfo> select_list = new ArrayList<>();
    private BorrowerInfo borrower;

    @FXML
    private TextField searchField;
    @FXML
    private Label numberBookSelected;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button clearButton;
    @FXML
    private HBox bottomHBox;

    public void setBorrower(BorrowerInfo borrower) {
        this.borrower = borrower;
    }

    public void setWelcomeLabel(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }

    @FXML
    private ComboBox catalogueCBox;

    ObservableList<String> catalogueOptions =
            FXCollections.observableArrayList("All", "Title", "Author", "ISBN");

    /**
     * handle Logout button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleLogoutButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        LoginForm form = new LoginForm();
        form.start(primaryStage, "You have been logged out");
    }

    @FXML
    private void initialize() {
        catalogueCBox.setValue("All");
        catalogueCBox.setItems(catalogueOptions);
    }

    /**
     * handle Select button
     * @param actionEvent event created by action
     */
    @FXML
    private void handleSelectButton(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        String i = source.getId();

        Scene scene = source.getScene();

        Label numberLeft = (Label) scene.lookup("#numberLeft" + i);
        if (numberLeft != null) {
            if (Integer.parseInt(numberLeft.getText()) == 0) {
                AlertMessage alert = new AlertMessage();
                alert.setHeader(null);
                alert.setMessage("You can't select this book anymore.");
                alert.execute(Alert.AlertType.ERROR);
            } else {
                int numLeft = Integer.parseInt(numberLeft.getText());
                numberLeft.setText(Integer.toString(numLeft - 1));
                int numSelect = Integer.parseInt(numberBookSelected.getText());
                numberBookSelected.setText(Integer.toString(numSelect + 1));
                int position = Integer.parseInt(i);
                BookInfo book = book_list.get(position);
                int maxPosition = book.getNumberLeft() + position;
                while (position < maxPosition){
                    if (!book_list.get(position).getStatus().equals("Selected")){
                        select_list.add(book_list.get(position));
                        book_list.get(position).setStatus("Selected");
                        break;
                    }
                    position ++;
                }
            }
        }
    }

    /**
     * handle Clear button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleClearButton(ActionEvent actionEvent) {
        if (Integer.parseInt(numberBookSelected.getText()) != 0){
            numberBookSelected.setText("0");
            handleSearchButton(actionEvent);
            select_list.clear();
        }
    }

    /**
     * handle Checkout button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleCheckOutButton(ActionEvent actionEvent) {
        AlertMessage alert = new AlertMessage();
        alert.setHeader(null);

        if (Integer.parseInt(numberBookSelected.getText()) == 0){
            alert.setMessage("You didn't select any book.");
            alert.execute(Alert.AlertType.ERROR);
            return;
        }

        int status = controller.checkRequest(this.borrower, select_list);
        System.out.println("checkRequestStatus: " + status);

        switch (status){
            case 1:
                Node source = (Node) actionEvent.getSource();
                Scene scene = source.getScene();
                Stage stage = (Stage) scene.getWindow();

                CheckOutForm form = new CheckOutForm();
                form.start(stage, this.borrower, this.select_list);

                break;
            case 2:
                alert.setMessage("You aren't able to borrow due to the following reason: \n " +
                        "1. Expired card.\n " +
                        "2. Overdue un-returned book.\n" +
                        "3. You select excess number allowed (ask Librarian for more Info)");
                alert.execute(Alert.AlertType.ERROR);
                break;
            case 3:
                alert.setMessage("Excess number of book allowed.");
                alert.execute(Alert.AlertType.ERROR);
                break;
            default:
                alert.setMessage("Internal error.");
                alert.execute(Alert.AlertType.ERROR);
                break;
        }
    }

    /**
     * handle Search button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleSearchButton(ActionEvent actionEvent) {
        if (bottomHBox != null) {
            bottomHBox.setAlignment(Pos.BASELINE_LEFT);
            bottomHBox.getChildren().clear();
        }

        String keyword = searchField.getText();
        String catalogue = catalogueCBox.getValue().toString().toLowerCase();
        if (catalogue.equals("isbn")) {
            catalogue = catalogue.toUpperCase();
        }
        this.book_list = controller.getBookInfo(keyword, catalogue);

        if (this.book_list != null) {
            bottomHBox.getChildren().clear();
            bottomHBox.getChildren().add(addTable());
        } else {
            Text resultText = new Text("No result match");
            resultText.setId("resultText");

            if (bottomHBox != null) {
                bottomHBox.setAlignment(Pos.BASELINE_CENTER);
                bottomHBox.getChildren().clear();
                bottomHBox.getChildren().addAll(resultText);
            }
        }
    }

    /**
     * add table with book_list
     * @return GridPane object table-like
     */
    private GridPane addTable() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        gridPane.setVgap(7);
        gridPane.setHgap(7);

        gridPane.add(new Label("BookNumber"), 0, 0);
        gridPane.add(new Label("Title"), 1, 0);
        gridPane.add(new Label("Author"), 2, 0);
        gridPane.add(new Label("ISBN"), 3, 0);
        gridPane.add(new Label("#Left"), 4, 0);

        int i = 0;
        while (i < book_list.size()){
            BookInfo one = book_list.get(i);
            int left = one.getNumberLeft();

            Label temp;

            temp = new Label(book_list.get(i).getBookID());
            temp.setId("bookNumber" + i);
            gridPane.add(temp, 0, i + 1);

            temp = new Label(book_list.get(i).getTitle());
            temp.setId("title" + i);
            gridPane.add(temp, 1, i + 1);

            temp = new Label(book_list.get(i).getAuthor());
            temp.setId("author" + i);
            gridPane.add(temp, 2, i + 1);

            temp = new Label(book_list.get(i).getISBN());
            temp.setId("isbn" + i);
            gridPane.add(temp, 3, i + 1);

            temp = new Label(Integer.toString(book_list.get(i).getNumberLeft()));
            temp.setId("numberLeft" + i);
            gridPane.add(temp, 4, i + 1);

            Button selectButton = new Button("Select");
            selectButton.setId(Integer.toString(i));
            selectButton.setOnAction(this::handleSelectButton);
            gridPane.add(selectButton, 5, i + 1);

            i+= left;
        }
        return gridPane;
    }

    public void checkExpire(){
        if (controller.warnExpireTime(borrower)) {
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.setHeader(null);
            alertMessage.setMessage("Your collection date is expired!");
            alertMessage.execute(Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void handleBBNavButton(ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage primaryStage = (Stage) scene.getWindow();
        BorrowBookForm form = new BorrowBookForm();
        form.start(primaryStage, borrower);
    }

    @FXML
    public void handleEPNavButton(ActionEvent actionEvent){}
}
