package PL.Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginForm extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 425);
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public void start(Stage stage, String message){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
        try {
            Pane root = loader.load();
            stage.setScene(new Scene(root, 600, 425));
        } catch (Exception e){
            System.out.println("Error " + e);
        }

        LoginFormListener listener = loader.<LoginFormListener>getController();
        listener.setActionTarget(message);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
