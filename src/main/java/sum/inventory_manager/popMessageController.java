package sum.inventory_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class popMessageController {
    @FXML
    Label label;
    @FXML
    Button btn;

    public void setMessage(String message) {
        label.setText(message);
    }

    @FXML
    protected void okayButton_clicked() {
        btn.getScene().getWindow().hide();
    }
}
