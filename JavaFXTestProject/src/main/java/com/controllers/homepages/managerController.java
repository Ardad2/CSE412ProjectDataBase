package com.controllers.homepages;

import com.controllers.loginregister.loginController;
import com.controllers.property.managerPropertyController;
import com.db.IDatabaseOperations;
import com.db.database_controller;
import com.models.Property;
import com.models.PropertyManager;
import com.models.Unit;
import com.models.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.controllers.sessions.UserSession;
import com.db.IDatabaseOperations;
import com.db.database_controller;
import com.main.MainApplication;
import javafx.stage.Stage;

public class managerController {

    private int userID;
    private Stage primaryStage;
    private Users currentUser;
    private PropertyManager currentManager;
    private int selectedUnitID;

    IDatabaseOperations databaseHandler = database_controller.getInstance();

    @FXML
    private Button logout;
    private Button ediButton; 
    private Button deleteButton; 
    private Button newAnnounce; 
    private Button delAnnounce;

    @FXML
    private Label user_name1; 
    @FXML
    private Label user_name2; 
    @FXML
    private Label user_name3; 
    @FXML
    private Label user_name4; 
    @FXML
    private Label user_name5; 
    @FXML
    private Label name; 
    @FXML
    private Label address;
    @FXML
    private Label amenities; 

    @FXML
    private TableView<Unit> unitTableView;

    @FXML
    private TableColumn<Unit, Integer> unitIDCol;

    @FXML
    private TableColumn<Unit, String> floorplanCol;

    @FXML
    private TableColumn<Unit, Boolean> isFurnishedCol;

    @FXML
    private TableColumn<Unit, String> conditionCol;

    @FXML
    private TableColumn<Unit, Boolean> isRentedCol;


    public void initialize(Stage primaryStage, int userID)
    {
        this.primaryStage = primaryStage;
        this.userID = userID;

        try {
            String usr = UserSession.getInstance().getEmail();
            database_controller dbController = database_controller.getInstance(); 
            user_name1.setText(dbController.getName(usr));
            user_name2.setText(dbController.getName(usr));
            user_name3.setText(dbController.getName(usr));
            user_name4.setText(dbController.getName(usr));
            user_name5.setText(dbController.getName(usr));
            this.selectedUnitID = -1;
            unitIDCol.setCellValueFactory(new PropertyValueFactory<Unit, Integer>("unitID"));
            floorplanCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("floorplan"));
            isFurnishedCol.setCellValueFactory(new PropertyValueFactory<Unit, Boolean>("isFurnished"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("condition"));
            isRentedCol.setCellValueFactory(new PropertyValueFactory<Unit, Boolean>("isRented"));
            int property_id = dbController.getPropertyId(UserSession.getInstance().getUserID());
            name.setText("Property Name: " + dbController.getPropertyName(property_id));
            address.setText("Address: "+ dbController.getPropertyAddress(property_id));
            List<String> amenities_list = dbController.getAmmenities(property_id);
            amenities.setText("Amenities: "+String.join(",", amenities_list));
            setupTable();
        } catch (IllegalStateException e) {
            UserSession.cleanUserSession();
            //username.setText("ERROR");
        }

    }

    @FXML
    void rowClicked(MouseEvent event) {
        Unit clickedUnit = unitTableView.getSelectionModel().getSelectedItem();
        selectedUnitID = clickedUnit.getUnitID();
        System.out.println("selected UnitID: " + clickedUnit);
    }

    public void userLogOut(ActionEvent event) throws IOException {
        UserSession.cleanUserSession();
        URL url = getClass().getResource("/com/pages/loginregister/loginPage.fxml");
        System.out.println(url.toString());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        loginController login = loader.getController();
        login.setStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void getTenants(ActionEvent event)throws IOException 
    {

    }

    public void getLeases(ActionEvent event)throws IOException 
    {

    }


    // public void getProperties(ActionEvent event) throws IOException {
    //     URL url = getClass().getResource("/com/pages/property/managerProperties.fxml");
    //     System.out.println(url.toString());
    //     FXMLLoader loader = new FXMLLoader(url);
    //     Parent root = loader.load();
    //     managerPropertyController mpc = loader.getController(); 
    //     mpc.initialize(primaryStage);
    //     primaryStage.setScene(new Scene(root));
    //     primaryStage.show();
    // }

    public void announcement(ActionEvent event) throws IOException {
        
    }

    public void editProperty(ActionEvent event) throws IOException {
        
    }

    public void deleteProperty(ActionEvent event) throws IOException {
        
    }

    public void newAnnounce(ActionEvent event) throws IOException {
        
    }

    public void delAnnounce(ActionEvent event) throws IOException {
        
    }

    private void setupTable(){

        database_controller db = new database_controller(); 
        int property_id = db.getPropertyId(UserSession.getInstance().getUserID());
        List<Unit> getUnitList = databaseHandler.unitList();
        System.out.println(getUnitList.size());
        System.out.println(property_id);

        for (int i = 0; i < getUnitList.size(); i++) {
            
            if (getUnitList.get(i).getPropertyID() == property_id) {
                
                unitTableView.getItems().addAll(getUnitList.get(i));
            }
        }
    }



}