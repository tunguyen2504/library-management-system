package PL.SearchBook;

import BLL.SearchBookController;
import DAL.BookInfo;
import DAL.User;
import PL.AddNewCopy.AddNewCopyForm;
import PL.AddNewEntry.AddNewEntryForm;
import PL.Login.LoginForm;
import PL.ReturnBook.ReturnBookForm;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SearchBookListener {
    private SearchBookController controller = new SearchBookController();
    private ArrayList<BookInfo> book_list;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private TextField searchField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private HBox bottomHBox;
    
    @FXML
    private Pane newEntry;
    
    @FXML
    private Button addNewEntry;

    public void setWelcomeLabel(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }

    @FXML
    private ComboBox catalogueCBox;

    ObservableList<String> catalogueOptions =
            FXCollections.observableArrayList("All", "Title", "Author", "ISBN");

    @FXML
    private void initialize() {
        catalogueCBox.setValue("All");
        catalogueCBox.setItems(catalogueOptions);
    }
    
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

    /**
     * handle Select button
     * @param actionEvent event created by action
     */
    private void handleSelectButton(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        String i = source.getId();

        Scene scene = source.getScene();
        Label bookID = (Label) scene.lookup("#bookNumber" + i);
        Label totalCopy = (Label) scene.lookup("#totalCopy" + i);        
        Stage stage = (Stage) scene.getWindow();
    	AddNewCopyForm form = new AddNewCopyForm();
        form.start(stage,this.user,bookID.getText(),totalCopy.getText());
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
        	newEntry.setVisible(false);
        	addNewEntry.setDisable(true);
            bottomHBox.getChildren().clear();
            bottomHBox.getChildren().add(addTable());
        } else {
            newEntry.setVisible(true);
        	addNewEntry.setDisable(false);
        }
    }
    
    /**
     * handle New Entry button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleNewEntryButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage stage = (Stage) scene.getWindow();
    	AddNewEntryForm form = new AddNewEntryForm();
        form.start(stage,this.user);
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
        gridPane.add(new Label("#Total Copy"), 4, 0);

        int i = 0;
        while (i < book_list.size()){
            BookInfo one = book_list.get(i);
            int left = one.getTotalCopy();

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

            temp = new Label(Integer.toString(book_list.get(i).getTotalCopy()));
            temp.setId("totalCopy" + i);
            gridPane.add(temp, 4, i + 1);

            Button selectButton = new Button("Select");
            selectButton.setId(Integer.toString(i));
            selectButton.setOnAction(this::handleSelectButton);
            gridPane.add(selectButton, 5, i + 1);

            i+= left;
        }
        return gridPane;
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
