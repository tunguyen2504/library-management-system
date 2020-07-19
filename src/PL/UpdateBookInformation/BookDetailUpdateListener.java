/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PL.UpdateBookInformation;

import BLL.SearchBookController;
import BLL.UpdateBookInformationController;
import DAL.BookInfo;
import DAL.User;
import PL.AlertMessage;
import PL.Login.LoginForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author tunguyen
 */
public class BookDetailUpdateListener {

    private User user;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label bookNumberField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField isbnField;

    @FXML
    private ComboBox copyNumberField;
    @FXML
    private ComboBox copyTypeField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField statusField;

    private SearchBookController searchController = new SearchBookController();
    private UpdateBookInformationController updateController = new UpdateBookInformationController();
    private String newTitle, newAuthor, newPublisher, newISBN, newType, newStatus;
    private long newPrice;

    public void setUser(User user) {
        this.user = user;
    }

    public void setWelcomeLabel(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }

    public void setBookNumberField(String bookNumber) {
        bookNumberField.setText(bookNumber);
    }

    public void setTitleField(String title) {
        titleField.setText(title);
    }

    public void setAuthorField(String author) {
        authorField.setText(author);
    }

    public void setPublisherField(String publisher) {
        publisherField.setText(publisher);
    }

    public void setIsbnField(String isbn) {
        isbnField.setText(isbn);
    }

    ObservableList copyNumberOptions;

    ObservableList copyTypeOptions;

    public void setCopyNumberOptions(ArrayList sequenceNumber) {
        copyNumberOptions = FXCollections.observableArrayList(sequenceNumber);
    }

    public void setCopyTypeOptions(ArrayList typeOptions) {
        copyTypeOptions = FXCollections.observableArrayList(typeOptions);
    }

    public void setCopyNumberField(ObservableList copyNumberOptions) {
        copyNumberField.setValue(copyNumberOptions.get(0));
        copyNumberField.setItems(copyNumberOptions);
    }

    public void setCopyTypeField(ObservableList copyTypeOptions) {
        String sequenceNumb = copyNumberField.getValue().toString();
        int copyNumber = Integer.parseInt(sequenceNumb);
        copyTypeField.setValue(searchController.getCopyTypeByID(copyNumber, bookNumberField.getText()));
        copyTypeField.setItems(copyTypeOptions);
    }

    public void setComboBox() {
        setCopyNumberField(copyNumberOptions);
        setCopyTypeField(copyTypeOptions);
    }

    public void setPriceField() {
        String sequenceNumb = copyNumberField.getValue().toString();
        int copyNumber = Integer.parseInt(sequenceNumb);
        priceField.setText(Long.toString(searchController.getPriceByID(copyNumber)));
    }

    public void setStatusField() {
        String sequenceNumb = copyNumberField.getValue().toString();
        int copyNumber = Integer.parseInt(sequenceNumb);
        statusField.setText(searchController.getStatusByID(copyNumber));
        statusField.setEditable(false);
    }

    /**
     * What to do when copy number is changed
     * @param event 
     */
    @FXML
    public void onCopyNumberChanged(ActionEvent event) {
        String sequenceNumb = copyNumberField.getValue().toString();
        int copyNumber = Integer.parseInt(sequenceNumb);
        System.out.println(copyNumber);
        System.out.println(searchController.getCopyTypeByID(copyNumber, bookNumberField.getText()));
        copyTypeField.setValue(searchController.getCopyTypeByID(copyNumber, bookNumberField.getText()));
        priceField.setText(Long.toString(searchController.getPriceByID(copyNumber)));
        statusField.setText(searchController.getStatusByID(copyNumber));
    }

    /**
     * What to do when field is changed
     * @param event 
     */
    @FXML
    public void handleInfoField(ActionEvent event) {
        newTitle = titleField.getText();
        newAuthor = authorField.getText();
        newPublisher = publisherField.getText();
        newISBN = isbnField.getText();
        newType = copyTypeField.getValue().toString();
        newPrice = Long.parseLong(priceField.getText());
        newStatus = statusField.getText();
    }

    /**
     * What to do when Logout button is clicked
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
     * What to do when click Update
     * @param event 
     */
    @FXML
    public void handleUpdateButton(ActionEvent event) {
        Node source = (Node) event.getSource();
        BookInfo book = updateController.getSelectedBookInfo(bookNumberField.getText());
        String sequenceNumb = copyNumberField.getValue().toString();
        int copyNumber = Integer.parseInt(sequenceNumb);
        if (book != null) {
            if (updateController.updateSelectedBook(book, newTitle, newAuthor, newPublisher, newISBN, copyNumber, newType, newPrice, newStatus)) {
                AlertMessage alert = new AlertMessage();
                alert.setMessage("Update successful");
                alert.setHeader(null);
                alert.execute(Alert.AlertType.INFORMATION);
                System.out.println("Update book successfully!");
            } else {
                AlertMessage alert = new AlertMessage();
                alert.setMessage("Error update");
                alert.setHeader(null);
                alert.execute(Alert.AlertType.ERROR);
                System.out.println("Update book failed.");
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
        UpdateBookInformationForm form = new UpdateBookInformationForm();
        form.start(stage, user);
    }
}
