package sum.inventory_manager;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

public class creditsController implements Initializable {
    sample.AutoCompleteTextField<Vehicle> vehicleAutoCompleteText;
    @FXML
    TreeTableView<CreditTableItem> creditTable;
    @FXML
    TreeTableColumn<CreditTableItem, String> colNameDate;
    @FXML
    TreeTableColumn<CreditTableItem, Double> colAmount;
    @FXML
    TextField txtAmount;
    @FXML
    DatePicker date;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNameDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("NameOrDate"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("Amount"));

        creditTable.setShowRoot(false);

        date.setValue(LocalDate.now());

        setUpAutoCompleteTextField();
    }

    @FXML
    protected void search() {
        if (vehicleAutoCompleteText.getLastSelectedObject() == null) {
            PopUp.showMessage("Select a vehicle");
            return;
        }
        creditTable.setRoot(Database.getCreditData(vehicleAutoCompleteText.getLastSelectedObject().getId()));
    }

    private void setUpAutoCompleteTextField() {
        SortedSet<Vehicle> vehicleEntries = new TreeSet<>(Comparator.comparing(Vehicle::getName));
        vehicleEntries.addAll(Database.getAllVehicleData());
        vehicleAutoCompleteText = new sample.AutoCompleteTextField(vehicleEntries);

        vehicleAutoCompleteText.getEntryMenu().setOnAction(e ->
                ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, autoTextEvent ->
                {
                    if (vehicleAutoCompleteText.getLastSelectedObject() != null)
                    {
                        vehicleAutoCompleteText.setText(vehicleAutoCompleteText.getLastSelectedObject().getName());
                    }
                }));

        ((AnchorPane) txtAmount.getParent()).getChildren().add(vehicleAutoCompleteText);
        AnchorPane.setTopAnchor(vehicleAutoCompleteText,20.0);
        AnchorPane.setLeftAnchor(vehicleAutoCompleteText,211.0);
        AnchorPane.setRightAnchor(vehicleAutoCompleteText,221.0);
    }

    @FXML
    protected void addPayment() {
        if (creditTable.getSelectionModel().getSelectedItem() == null) {
            PopUp.showMessage("Select a customer");
            return;
        }
        if (creditTable.getSelectionModel().getSelectedItem().getValue().getCustomerId() == -1) {
            PopUp.showMessage("Select a customer");
            return;
        }
        Database.addCreditPayment(
                date.getValue(),
                creditTable.getSelectionModel().getSelectedItem().getValue().getCustomerId(),
                Double.parseDouble(txtAmount.getText())
        );
        creditTable.setRoot(Database.getCreditData(vehicleAutoCompleteText.getLastSelectedObject().getId()));
    }
}
