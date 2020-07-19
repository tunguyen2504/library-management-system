package PL.ReturnBook;

import BLL.ReturnBookController;
import DAL.BorrowingInfo;
import DAL.User;
import PL.Login.LoginForm;
import PL.SearchBook.SearchBookForm;
import PL.UpdateBookInformation.UpdateBookInformationForm;
import PL.UpdateBorrowingCardInfo.UpdateBorrowingCardInfoForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReturnBookListener {
    private ArrayList<BorrowingInfo> infos;
    private User user;
    private ReturnBookController controller = new ReturnBookController();
    private ArrayList<BorrowingInfo> select_infos = new ArrayList<>();


    @FXML
    private TextField addFeeTextField;
    @FXML
    private ComboBox catalogueCBox;

    ObservableList<String> catalogueOptions =
            FXCollections.observableArrayList("CardID", "BorrowingID");

    @FXML
    private TextField searchField;

    @FXML
    private HBox bottomHBox;

    @FXML
    private Label welcomeLabel;

    public void setWelcomeLabel(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * init scene
     */
    @FXML
    private void initialize() {
        catalogueCBox.setValue("CardID");
        catalogueCBox.setItems(catalogueOptions);
    }

    /**
     * handle Logout button
     *
     * @param actionEvent event created by action
     */
    @FXML
    public void handleLogoutButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        LoginForm form = new LoginForm();
        form.start(primaryStage, "You have been logged out");
    }

    /**
     * handle Search button
     *
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
        infos = controller.getBorrowingInfo(keyword, catalogue);

        if (infos != null) {
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
     * handle Collect button
     *
     * @param actionEvent event created by action
     */
    @FXML
    private void handleCollectButton(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        int i = Integer.parseInt(source.getId());
        select_infos.add(infos.get(i));

        source.setVisible(false);
    }

    /**
     * handle Print Bill button
     *
     * @param actionEvent event created by action
     */
    @FXML
    private void handlePrintBillButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage stage = (Stage) scene.getWindow();

        BillForm form = new BillForm();

        double addFee;
        if (addFeeTextField.getText().equals("")) {
            addFee = 0;
        } else {
            addFee = Double.parseDouble(addFeeTextField.getText());
        }
        form.start(stage, select_infos, addFee);
    }

    /**
     * handle Undo button
     *
     * @param actionEvent event created by action
     */
    @FXML
    private void handleUndoButton(ActionEvent actionEvent) {
        if (!select_infos.isEmpty())
            select_infos.clear();

        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();

        if (infos != null) {
            if (!infos.isEmpty()) {
                for (int i = 0; i < infos.size(); i++) {
                    Button button = (Button) scene.lookup("#" + i);
                    button.setVisible(true);
                }
            }
        }
    }


    /**
     * add table with borrowing info list
     *
     * @return GridPane object table-like
     */
    private GridPane addTable() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        gridPane.setVgap(7);
        gridPane.setHgap(7);

        gridPane.add(new Label("BorrowingID"), 0, 0);
        gridPane.add(new Label("CardID"), 1, 0);
        gridPane.add(new Label("BookNumber"), 2, 0);
        gridPane.add(new Label("CopyNumber"), 3, 0);
        gridPane.add(new Label("Status"), 4, 0);
        gridPane.add(new Label("DueDate"), 5, 0);

        int i = 0;
        while (i < infos.size()) {
            BorrowingInfo one = infos.get(i);

            Label temp;

            temp = new Label(Integer.toString(infos.get(i).getBorrowingID()));
            temp.setId("BorrowingID" + i);
            gridPane.add(temp, 0, i + 1);

            temp = new Label(Integer.toString(infos.get(i).getCardNumber()));
            temp.setId("CardID" + i);
            gridPane.add(temp, 1, i + 1);

            temp = new Label(infos.get(i).getBookNumber());
            temp.setId("BookNumber" + i);
            gridPane.add(temp, 2, i + 1);

            temp = new Label(Integer.toString(infos.get(i).getCopyNumber()));
            temp.setId("CopyNumber" + i);
            gridPane.add(temp, 3, i + 1);

            temp = new Label(infos.get(i).getStatus());
            temp.setId("Status" + i);
            gridPane.add(temp, 4, i + 1);

            temp = new Label(extractDate(infos.get(i).getDueDate()));
            temp.setId("DueDate" + i);
            gridPane.add(temp, 5, i + 1);

            Button selectButton = new Button("Collect");
            selectButton.setId(Integer.toString(i));
            selectButton.setOnAction(this::handleCollectButton);
            gridPane.add(selectButton, 6, i + 1);

            i++;
        }

        Button selectButton = new Button("PrintBill");
        selectButton.setOnAction(this::handlePrintBillButton);
        gridPane.add(selectButton, 6, i + 1);

        selectButton = new Button("Undo");
        selectButton.setOnAction(this::handleUndoButton);
        gridPane.add(selectButton, 5, i + 1);
        return gridPane;
    }

    private String extractDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    @FXML
    public void handleRBNavButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage stage = (Stage) scene.getWindow();

        ReturnBookForm form = new ReturnBookForm();
        form.start(stage, user);
    }

    @FXML
    public void handleABNavButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage stage = (Stage) scene.getWindow();

        SearchBookForm form = new SearchBookForm();
        form.start(stage, user);
    }

    @FXML
    public void handleUCNavButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage stage = (Stage) scene.getWindow();

        UpdateBorrowingCardInfoForm form = new UpdateBorrowingCardInfoForm();
        form.start(stage, user);
    }

    @FXML
    public void handleUBNavButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage stage = (Stage) scene.getWindow();

        UpdateBookInformationForm form = new UpdateBookInformationForm();
        form.start(stage, user);
    }
}
