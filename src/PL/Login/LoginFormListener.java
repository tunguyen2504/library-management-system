package PL.Login;

import BLL.LoginController;
import DAL.BorrowerInfo;
import DAL.User;
import PL.BorrowBook.BorrowBookForm;
import PL.SearchBook.SearchBookForm;
import PL.UpdateBorrowingCardInfo.UpdateBorrowingCardInfoForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginFormListener {
    private LoginController controller = new LoginController();
    private User user;

    @FXML private Text actionTarget;

    public void setActionTarget(String message){
        actionTarget.setText(message);
    }

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passField;

    @FXML
    public void handleLoginButton(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        String username = userTextField.getText();
        String password = passField.getText();

        user = controller.validateUser(username, password);

        if (username == null || password == null){
            actionTarget.setText("You must enter username and password");
            return;
        }

        if (user != null) {
            if (user.getRole().equals("Borrower")) {
                BorrowBookForm form = new BorrowBookForm();
                BorrowerInfo borrower = controller.getBorrower(user.getUsername());
                form.start(primaryStage, borrower);
            }
            if (user.getRole().equals("Librarian")) {
                UpdateBorrowingCardInfoForm form = new UpdateBorrowingCardInfoForm();
                User librarian = controller.getUser(user.getUsername());
                form.start(primaryStage, librarian);
            }

//            if (user.getRole().equals("Guest"))
        } else {
            actionTarget.setText("Wrong username or password");
        }
    }

    @FXML
    public void handleExitButton(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();
    }
}
