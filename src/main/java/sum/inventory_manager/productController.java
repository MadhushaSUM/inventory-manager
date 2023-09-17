package sum.inventory_manager;

import animatefx.animation.Pulse;
import animatefx.animation.RollIn;
import animatefx.animation.RubberBand;
import animatefx.animation.Shake;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class productController implements Initializable {
    @FXML
    TableView<Product> productTable;
    @FXML
    TableColumn<Product, Integer> colId;
    @FXML
    TableColumn<Product, String> colName;
    @FXML
    TableColumn<Product, String> colDescription;
    @FXML
    TableColumn<Product, String> colUnit;
    @FXML
    TextField txtName;
    @FXML
    TextField txtDescription;
    @FXML
    TextField txtUnit;

    public enum ProductDetail {
        NAME,
        DESCRIPTION,
        UNIT
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));

        productTable.setItems(Database.getAllProductData());

        productTable.setEditable(true);

        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(event -> Database.changeProductDetails(ProductDetail.NAME, event));

        colDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescription.setOnEditCommit(event -> Database.changeProductDetails(ProductDetail.DESCRIPTION, event));

        colUnit.setCellFactory(TextFieldTableCell.forTableColumn());
        colUnit.setOnEditCommit(event -> Database.changeProductDetails(ProductDetail.UNIT, event));
    }

    @FXML
    protected void addNewProduct() {
        //new RubberBand(btnAdd).play();
        if (txtName.getText().isEmpty()) {
            PopUp.showMessage("Name is required!");
            return;
        }

        Database.addProduct(txtName.getText(),txtDescription.getText(),txtUnit.getText());
        productTable.setItems(Database.getAllProductData());
        PopUp.showMessage("Product added");
    }
}
