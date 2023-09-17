package sum.inventory_manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class errorController {
    @FXML
    TextArea txtError;
    @FXML
    Button btnError;

    public void setErrorMessage(Exception e) {
        txtError.setText(e.getLocalizedMessage());
    }
}
