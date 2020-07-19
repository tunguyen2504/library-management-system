/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PL.UpdateBorrowingCardInfo;

import BLL.UpdateBorrowingCardInfoController;
import DAL.BorrowerInfo;
import DAL.CardInfo;
import DAL.User;
import PL.Login.LoginForm;
import PL.ReturnBook.ReturnBookForm;
import PL.SearchBook.SearchBookForm;
import PL.UpdateBookInformation.UpdateBookInformationForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author tunguyen
 */
public class UpdateBorrowingCardInfoListener {

    UpdateBorrowingCardInfoController controller = new UpdateBorrowingCardInfoController();
    ArrayList<CardInfo> card_list;
    private User user;
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//    private ArrayList<Button> selectButtonList = new ArrayList();

    @FXML
    private TextField searchField;
    @FXML
    private HBox bottomHBox;
    @FXML
    private Label welcomeLabel;

    /**
     * What to do when click Logout
     * @param actionEvent 
     */
    @FXML
    public void handleLogoutButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        LoginForm form = new LoginForm();
        form.start(primaryStage, "You have been logged out");
    }

    /**
     * What to do when click Search
     * @param actionEvent 
     */
    @FXML
    public void handleSearchButton(ActionEvent actionEvent) {
        if (bottomHBox != null) {
            bottomHBox.setAlignment(Pos.BASELINE_LEFT);
            bottomHBox.getChildren().clear();
        }

        int numb = Integer.parseInt(searchField.getText());

        this.card_list = controller.getCardInfo(numb);

        if (this.card_list != null) {
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setWelcomeLabel(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }

    /**
     * Draw table which contains the list of Borrower Card
     * @return GridPane which is the table
     */
    private GridPane addTable() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        gridPane.setVgap(10);
        gridPane.setHgap(7);

        gridPane.add(new Label("Card Number"), 0, 0);
        gridPane.add(new Label("User"), 1, 0);
        gridPane.add(new Label("Full Name"), 2, 0);
        gridPane.add(new Label("Borrower ID"), 3, 0);
        gridPane.add(new Label("Expiry Date"), 4, 0);

        BorrowerInfo borrower = null;

        for (int i = 0; i < card_list.size(); i++) {
            CardInfo oneCard = card_list.get(i);

            Label temp;

            temp = new Label(Integer.toString(oneCard.getCardID()));
            temp.setId("cardNumber" + i);
            gridPane.add(temp, 0, i + 1);

            if (oneCard.getBorrowerID() != 0) {
                borrower = controller.getBorrowerById(oneCard.getBorrowerID());
                String userName = borrower.getUsername();
                String fullName = borrower.getName();

                temp = new Label(userName);
                temp.setId("userName" + i);
                gridPane.add(temp, 1, i + 1);

                temp = new Label(fullName);
                temp.setId("fullName" + i);
                gridPane.add(temp, 2, i + 1);

                temp = new Label(Integer.toString(oneCard.getBorrowerID()));
                temp.setId("borrowerID" + i);
                gridPane.add(temp, 3, i + 1);

                temp = new Label(formatter.format(oneCard.getExpiredDate()));
                temp.setId("expiredDate" + i);
                gridPane.add(temp, 4, i + 1);
            } else {
                temp = new Label("N/A");
                temp.setId("userName" + i);
                gridPane.add(temp, 1, i + 1);
                temp = new Label("N/A");
                temp.setId("fullName" + i);
                gridPane.add(temp, 2, i + 1);
                temp = new Label("N/A");
                temp.setId("borrowerID" + i);
                gridPane.add(temp, 3, i + 1);
                temp = new Label("N/A");
                temp.setId("expiredDate" + i);
                gridPane.add(temp, 4, i + 1);
            }

            Button selectButton = new Button("Select");
            selectButton.setId(Integer.toString(i));
            selectButton.setOnAction(this::handleSelectButton);
            gridPane.add(selectButton, 5, i + 1);
        }
        return gridPane;
    }

    /**
     * What to do when click Select
     * @param event 
     */
    private void handleSelectButton(ActionEvent event) {
//        Button source = (Button) event.getSource();

        Node source = (Node) event.getSource();
        String i = source.getId();
        Stage stage = (Stage) source.getScene().getWindow();
//        Scene scene = source.getScene();
        CardDetailUpdateForm form = new CardDetailUpdateForm();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("CardDetailUpdate.fxml"));
        form.start(stage, user);
        CardDetailUpdateListener cardDetailUpdateListener = form.getListener();
        CardInfo oneCard = card_list.get(Integer.parseInt(i));
        cardDetailUpdateListener.setCardNumber(oneCard.getCardID());
        LocalDate localDate = null;
        if (oneCard.getBorrowerID() != 0) {
            BorrowerInfo borrower = controller.getBorrowerById(oneCard.getBorrowerID());
            cardDetailUpdateListener.setUserName(borrower.getUsername());
            cardDetailUpdateListener.setName(borrower.getName());
            cardDetailUpdateListener.setBorrowerId(oneCard.getBorrowerID());
            localDate = LocalDate.parse(formatter.format(oneCard.getExpiredDate()), dateFormatter);
            cardDetailUpdateListener.setExpiryDate(localDate);
        } else {
            cardDetailUpdateListener.setUserName("N/A");
            cardDetailUpdateListener.setName("N/A");
            cardDetailUpdateListener.setBorrowerId(oneCard.getBorrowerID());
            cardDetailUpdateListener.setExpiryDate(localDate);
        }
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
