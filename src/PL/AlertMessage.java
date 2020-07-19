package PL;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class AlertMessage {
    private String message;
    private String header;

    public void setHeader(String header) {
        this.header = header;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void execute(Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("../CSS/alertBox.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait();
    }
}
