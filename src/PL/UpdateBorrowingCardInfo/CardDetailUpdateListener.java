/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PL.UpdateBorrowingCardInfo;

import BLL.UpdateBorrowingCardInfoController;
import DAL.CardInfo;
import DAL.User;
import PL.AlertMessage;
import PL.Login.LoginForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * FXML Controller class
 *
 * @author tunguyen
 */
public class CardDetailUpdateListener {

    UpdateBorrowingCardInfoController controller = new UpdateBorrowingCardInfoController();
    ArrayList<CardInfo> card_list;
    private CardInfo cardInfo;
    private Date expiredDate;
    private User user;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label cardNumberField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField borrowerIdField;
    @FXML
    private DatePicker dateField;

    public void setUser(User user) {
        this.user = user;
    }

    public void setWelcomeLabel(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }

    public void setCardNumber(int cardId) {
        cardNumberField.setText(Integer.toString(cardId));
    }

    public void setUserName(String userName) {
        userNameField.setText(userName);
        userNameField.setEditable(false);
    }

    public void setName(String name) {
        nameField.setText(name);
        nameField.setEditable(false);
    }

    public void setBorrowerId(int borrowerId) {
        if (borrowerId != 0) {
            borrowerIdField.setText(Integer.toString(borrowerId));
        } else {
            borrowerIdField.setText("N/A");
        }
        borrowerIdField.setEditable(false);
    }

    public void setExpiryDate(LocalDate expiryDate) {
        if (expiryDate != null) {
            dateField.setValue(expiryDate);
        } else {
            dateField.setDisable(true);
        }
    }

    /**
     * What to do when click Logout
     * @param event 
     */
    @FXML
    public void handleLogoutButton(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        LoginForm form = new LoginForm();
        form.start(primaryStage, "You have been logged out");
    }

    /**
     * What to do when change Date in DatePicker
     * @param event 
     */
    @FXML
    public void handleDateField(ActionEvent event) {
        LocalDate localDate = dateField.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        expiredDate = Date.from(instant);
    }

    /**
     * What to do when click Update
     * @param event 
     */
    @FXML
    public void handleUpdateButton(ActionEvent event) {
        Node source = (Node) event.getSource();
        CardInfo oneCard = controller.getSelectedCardInfo(Integer.parseInt(cardNumberField.getText()));
        if (oneCard != null) {
            if (controller.updateSelectedCardInfo(oneCard, expiredDate)) {
                AlertMessage alert = new AlertMessage();
                alert.setMessage("Update successful");
                alert.setHeader(null);
                alert.execute(Alert.AlertType.INFORMATION);
            } else {
                AlertMessage alert = new AlertMessage();
                alert.setMessage("Error update");
                alert.setHeader(null);
                alert.execute(Alert.AlertType.ERROR);
            }
        }

    }

    /**
     * What to do when click Cancel
     * @param event 
     */
    @FXML
    public void handleCancelButton(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        UpdateBorrowingCardInfoForm form = new UpdateBorrowingCardInfoForm();
        form.start(stage, user);
    }
}
