package sum.inventory_manager;

import animatefx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import javafx.scene.layout.AnchorPane;
import sample.AutoCompleteTextField;

public class storeStockController implements Initializable {
    @FXML
    TableView<StoreStock> storeStockTable;
    @FXML
    TableColumn<StoreStock, Integer> colId;
    @FXML
    TableColumn<StoreStock, String> colProduct;
    @FXML
    TableColumn<StoreStock, Double> colQuantity;
    @FXML
    TableColumn<StoreStock, Double> colUnitPrice;
    @FXML
    TextField txtQuantity;
    @FXML
    TextField txtUnitPrice;
    @FXML
    DatePicker stockDate;

    AutoCompleteTextField<Product> productAutoCompleteText;

    ScrollPane main_pane;
    Label title_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("StoreStockId"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        storeStockTable.setItems(Database.getAllStoreStockData(false));

        setUpProductAutoTextField();

        stockDate.setValue(LocalDate.now());

        storeStockTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(StoreStock stock, boolean empty) {
                super.updateItem(stock, empty);
                if (stock == null) {
                    setStyle("");
                } else if (stock.getQuantity() <= 0) {
                    setStyle("-fx-background-color: #EC7063;");
                }
            }
        });
    }

    @FXML
    protected void addStoreStock() {
        if (productAutoCompleteText.getLastSelectedObject() == null) {
            PopUp.showMessage("Product is required!");
            return;
        }
        if (stockDate.getValue() == null) {
            PopUp.showMessage("Date is required!");
            return;
        }

        double quantity, unitPrice;
        try {
            quantity = Double.parseDouble(txtQuantity.getText());
            unitPrice = Double.parseDouble(txtUnitPrice.getText());

            Database.addStoreStock(
                    productAutoCompleteText.getLastSelectedObject().getProductId(),
                    quantity,
                    unitPrice,
                    stockDate.getValue());

            storeStockTable.setItems(Database.getAllStoreStockData(false));
        } catch (NumberFormatException e) {
            PopUp.showMessage("Please enter valid numbers");
            e.printStackTrace();
        }
    }

    public void getSceneNodes(ScrollPane scrollPane, Label label) {
        this.main_pane = scrollPane;
        this.title_label = label;
    }
    @FXML
    protected void storeStockLoad_clicked() {
        AnimationFX titleAnimation = new SlideOutRight(title_label);
        titleAnimation.setOnFinished(event1 -> title_label.setText("STORE STOCK LOADS"));
        titleAnimation.playOnFinished(new SlideInRight(title_label));
        titleAnimation.play();

        FXMLLoader storeStockLoadsSceneLoader = new FXMLLoader(storeStockController.class.getResource("store-stock-loads-stage.fxml"));
        AnchorPane storeStockLoadsScene;
        try {
            storeStockLoadsScene = storeStockLoadsSceneLoader.load();
        } catch (IOException ex) {
            ShowError.show(ex);
            throw new RuntimeException(ex);
        }

        storeStockLoadsScene.getStylesheets().add(String.valueOf(storeStockController.class.getResource("main-stage-styles.css")));

        AnimationFX mainAnimation = new SlideOutDown(main_pane);
        mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(storeStockLoadsScene));
        mainAnimation.playOnFinished(new SlideInUp(main_pane));
        mainAnimation.play();
    }

    private void setUpProductAutoTextField() {
        //AutoCompleted text field for Products

        SortedSet<Product> customerEntries = new TreeSet<>(Comparator.comparing(Product::toString));
        customerEntries.addAll(Database.getAllProductData());
        productAutoCompleteText = new AutoCompleteTextField(customerEntries);

        productAutoCompleteText.getEntryMenu().setOnAction(e ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, autoTextEvent ->
            {
                if (productAutoCompleteText.getLastSelectedObject() != null)
                {
                    productAutoCompleteText.setText(productAutoCompleteText.getLastSelectedObject().getName());
                }
            });
        });

        ((AnchorPane) txtQuantity.getParent()).getChildren().add(productAutoCompleteText);
        AnchorPane.setTopAnchor(productAutoCompleteText,337.0);
        AnchorPane.setLeftAnchor(productAutoCompleteText,192.0);
        AnchorPane.setRightAnchor(productAutoCompleteText,252.0);
    }


}
