package sum.inventory_manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PopUp {
    public static void showMessage(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(PopUp.class.getResource("pop-message.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            scene.getStylesheets().add(String.valueOf(PopUp.class.getResource("pop-up-styles.css")));

            stage.setScene(scene);
            stage.show();

            ((popMessageController) loader.getController()).setMessage(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


