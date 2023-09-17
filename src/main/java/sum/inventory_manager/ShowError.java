package sum.inventory_manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowError {
    public static void show(Exception e) {
        try {
            FXMLLoader loader = new FXMLLoader(errorController.class.getResource("error-stage.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            ((errorController) loader.getController()).setErrorMessage(e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
