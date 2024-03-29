package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;
/**
 * Sample Skeleton for 'ListView.fxml' Controller Class
 */

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.beans.binding.Bindings;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import java.time.LocalDate;

public class ListViewController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="searchByDate"
    private DatePicker searchByDate; // Value injected by FXMLLoader

    @FXML private TableView<TOMaintenanceTicket> overviewTable;

    @FXML private Button searchButton;

    @FXML private TextField searchTicketTextField;

    public void initialize() {
        // initialize the overview table by adding new columns
        
        //TICKET ID COLUMN (int)
        overviewTable.getColumns().add(createTableColumn("Ticket ID", "number"));

        //STATUS COLUMN (string)
        var statusColumn = createTableColumn("Status", "status");
        overviewTable.getColumns().add(statusColumn);
    
        //RAISED BY EMAIL COLUMN (customizable string)
        var raisedColumn = new TableColumn<TOMaintenanceTicket, String>("Ticket Raiser");
        raisedColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getRaisedByEmail()));
        overviewTable.getColumns().add(raisedColumn);

        //DESCRIPTION COLUMN (customizable string)
        var descriptionColumn = new TableColumn<TOMaintenanceTicket, String>("Description");
        descriptionColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getDescription()));
        overviewTable.getColumns().add(descriptionColumn);

        //FIXED BY EMAIL COLUMN (customizable string)
        var fixedempColumn = new TableColumn<TOMaintenanceTicket, String>("Assigned Employee");
        fixedempColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getFixedByEmail()));
        overviewTable.getColumns().add(fixedempColumn);

        //TIME TO RESOLVE COLUMN (customizable string)
        var timeResColumn = new TableColumn<TOMaintenanceTicket, String>("Time To Resolve");
        timeResColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getTimeToResolve()));
        overviewTable.getColumns().add(timeResColumn);

        //PRIORITY COLUMN (string)
        overviewTable.getColumns().add(createTableColumn("Priority", "prioritylevel"));

        //APPROVAL REQUIRED (Boolean)
        overviewTable.getColumns().add(createTableColumn("Approval Required", "approvalRequired"));

        //ASSET NAME (customizable string)
        var assetNameColumn = new TableColumn<TOMaintenanceTicket, String>("Asset Name");
        assetNameColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getAssetName()));
        overviewTable.getColumns().add(assetNameColumn);

        //LIFESPAN COLUMN (string)
        overviewTable.getColumns().add(createTableColumn("Lifespan", "lifespan"));

        //Purchase date (Date)
        var purchaseDateColumn = new TableColumn<TOMaintenanceTicket, String>("Purchase Date");
        purchaseDateColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getPurchaseDate().toString()));
        overviewTable.getColumns().add(purchaseDateColumn);

        //FloorNumber int
        overviewTable.getColumns().add(createTableColumn("Floor Number", "floornumber"));

        //RoomNumber int
        overviewTable.getColumns().add(createTableColumn("Room Number", "roomnumber"));

        //Image URLS 
        var imageColumn = new TableColumn<TOMaintenanceTicket, String>("Images");
        imageColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getImageURLs().toString()));
        overviewTable.getColumns().add(imageColumn);

        //Maintenance Notes 
        var notesColumn = new TableColumn<TOMaintenanceTicket, String>("Notes");
        notesColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getNotes().toString()));
        overviewTable.getColumns().add(notesColumn);


        //Set colors 
        overviewTable.setRowFactory(tv -> new TableRow<TOMaintenanceTicket>() {
            @Override
            protected void updateItem(TOMaintenanceTicket item, boolean empty) {
                super.updateItem(item, empty);
    
                if (item == null || item.getStatus() == null) {
                    // If the status is null, set the default row color
                    setStyle("");
                } else {
                    // Set row color based on the value of the "Status" column
                    switch (item.getStatus()) {
                        case "Open":
                            setStyle("-fx-background-color: lightgreen;");
                            break;
                        case "InProgress":
                            setStyle("-fx-background-color: #FFFFE0;"); //Light yellow
                            break;
                        case "Assigned":
                            setStyle("-fx-background-color: #FFDAB9;");  //light orange
                            break;
                        case "Closed":
                            setStyle("-fx-background-color: ##FFB6C1"); //light red 
                        case "Resolved":
                            setStyle("-fx-background-color: #E6E6FA"); //light purple
                        default:
                            // Set the default row color for other statuses
                            setStyle("");
                            break;
                    }
                }
            }
        });


        // configure data picker
        // set editable to false so that the user cannot choose from the calendar
        searchByDate.setEditable(false);
        // set default value to today
        searchByDate.setValue(LocalDate.now());
    
        // overview table if a refreshable element
        overviewTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> overviewTable.setItems(getTicketItems()));
    
        // register refreshable nodes
        AssetPlusFxmlView.getInstance().registerRefreshEvent(overviewTable);
      }

    @FXML
    void selectedDateChanged(ActionEvent event) {
        AssetPlusFxmlView.getInstance().refresh();
    }


    // the table column will automatically display the string value of the property for each instance in the table
    public static TableColumn<TOMaintenanceTicket, String> createTableColumn(String header, String propertyName) {
        TableColumn<TOMaintenanceTicket, String> column = new TableColumn<>(header);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return column;
    }
  
    @FXML 
    void searchButtonClicked(ActionEvent event) {
        String stringid = searchTicketTextField.getText().trim();
        if (stringid.isEmpty()) {
            ViewUtils.showError("Please input a valid ticket ID");
        } else {
            int id = Integer.parseInt(stringid);
            // Search tickets by ID and update the table
            ObservableList<TOMaintenanceTicket> filteredTickets = getAllTickets().filtered(ticket -> ticket.getId() ==id);

            if (filteredTickets.isEmpty()) {
                ViewUtils.showError("No tickets found with the specified ID");
            } else {
                overviewTable.setItems(filteredTickets);
                AssetPlusFxmlView.getInstance().registerRefreshEvent(overviewTable);
            }
        }
    }

    public ObservableList<TOMaintenanceTicket> getAllTickets() {
        if (AssetPlusFeatureSet6Controller.getTickets() == null){
            return FXCollections.emptyObservableList();
        }
        return FXCollections.observableList(AssetPlusFeatureSet6Controller.getTickets());
    }




    public ObservableList<TOMaintenanceTicket> getTicketItems() {
        LocalDate selectedDate = searchByDate.getValue();
      
        if (selectedDate == null) {
          ViewUtils.showError("Please select a valid date");
          return FXCollections.emptyObservableList();
        }
        var date = Date.valueOf(selectedDate);
        if (AssetPlusFeatureSet6Controller.getTicketForSpecificDay(date) == null) {
            return FXCollections.emptyObservableList();
        }
        return FXCollections.observableList(AssetPlusFeatureSet6Controller.getTicketForSpecificDay(date));
    }

    
    /*@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert SearchById != null : "fx:id=\"SearchById\" was not injected: check your FXML file 'ListView.fxml'.";
        assert overviewTable != null : "fx:id=\"overviewTable\" was not injected: check your FXML file 'ListView.fxml'.";
        assert searchByDate != null : "fx:id=\"searchByDate\" was not injected: check your FXML file 'ListView.fxml'.";

    }*/

}
