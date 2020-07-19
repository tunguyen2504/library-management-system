package PL.AddNewCopy;

import BLL.RegisterNewBookCopyController;
import DAL.User;
import PL.AlertMessage;
import PL.Login.LoginForm;
import PL.SearchBook.SearchBookForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddNewCopyListener {
    private User user;
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label bookIDField;
    @FXML
    private Label sequenceField;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox typeCBox;
    @FXML
    private Button backButton;
    @FXML
    private Button submitButton;
    @FXML
    private Label warnTarget;
    
    //cbox handler
    ObservableList<String> typeOptions = FXCollections.observableArrayList("Reference", "Borrowable");
    @FXML
    private void initialize() {
        typeCBox.setValue("Reference");
        typeCBox.setItems(typeOptions);
    }
    //
    
    public void setWelcomeLabel(String name) {
        welcomeLabel.setText("Welcome, " + name);
    }
    
	public void setBookID(String bookID) {
		bookIDField.setText(bookID);
	}
	
	public void setSequence(int sequence) {
		sequenceField.setText(String.valueOf(sequence));		
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
     * handle Back button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage stage = (Stage) scene.getWindow();
    	SearchBookForm form = new SearchBookForm();
        form.start(stage,this.user);
    }
    
    /**
     * handle Submit button
     * @param actionEvent event created by action
     */
    @FXML
    public void handleSubmitButton(ActionEvent actionEvent) {
    	RegisterNewBookCopyController controller = new RegisterNewBookCopyController();
    	String bookID = bookIDField.getText();
        String type = typeCBox.getValue().toString();
        String status = "Available";
        AlertMessage alert = new AlertMessage();
        alert.setHeader(null);
        try{
        	int copy = Integer.parseInt(sequenceField.getText());
        	double price = Double.parseDouble(priceField.getText());
        	checkInput(bookID, copy, price);
        	boolean check = controller.addNewCopy(copy, bookID, type, price, status);
        	if (check){
                alert.setMessage("Register New Book Copy Completed");
                alert.execute(Alert.AlertType.INFORMATION);
                Node source = (Node) actionEvent.getSource();
                Scene scene = source.getScene();
                Stage stage = (Stage) scene.getWindow();
            	SearchBookForm form = new SearchBookForm();
                form.start(stage,this.user);
        	} else {
                alert.setMessage("Incorrect Input.");
                alert.execute(Alert.AlertType.ERROR);
        		return;
        	}
        		
        	} catch (NumberFormatException e) {
                alert.setMessage("Incorrect Input.");
                alert.execute(Alert.AlertType.ERROR);
        		return;
        	}
       
    }
    
    /**
     * check submitted input
     * @param bookID the bookID of the new book copy
     * @param sequence the copy number of the new book copy
     * @param price the price of the new book copy
     */
    public void checkInput(String bookID, int sequence, double price){
    	if (bookID.isEmpty()){
    		warnTarget.setText("Invalid Book ID.");
    		return;
    	}
    	if (sequence < 0){
    		warnTarget.setText("Invalid Sequence Number.");
    		return;
    	}
    	if (price < 0){
    		warnTarget.setText("Invalid Price.");
    		return;
    	}
    }




}

