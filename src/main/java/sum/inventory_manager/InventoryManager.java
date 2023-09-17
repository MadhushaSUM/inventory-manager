package sum.inventory_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Map;

public class InventoryManager extends Application {
    @Override
    public void start(Stage stage) throws IOException {

//        String LicensedKeyword = "DESKTOP-68O3I2U";
//        Map<String,String> env = System.getenv();
//        if (!env.get("COMPUTERNAME").equalsIgnoreCase(LicensedKeyword)) {
//            PopUp.showMessage("You are not licenced to\nuse this Application");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.exit(0);
//        }

        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManager.class.getResource("main-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(String.valueOf(InventoryManager.class.getResource("main-stage-styles.css")));
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Inventory Manager");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}