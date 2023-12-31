package com.controllers.homepages;

import com.controllers.loginregister.loginController;
import com.controllers.property.propertyController;
import com.controllers.property.propertyListController;
import com.controllers.sessions.UserSession;
import com.controllers.unit.unitListController;
import com.db.IDatabaseOperations;
import com.db.database_controller;
import com.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class customerController {

    private int userID;
    private Stage primaryStage;
    private Users currentUser;
    private Customers currentCustomer;
    private String leaseStatus;


    @FXML
    private TabPane tabPane;

    @FXML
    private Tab homeTab;

    @FXML
    private Tab viewPropertiesTab;

    @FXML
    private Tab myUnitTab;

    @FXML
    private Tab myPropertyTab;

    @FXML
    private Tab maintenanceTab;

    @FXML
    private Tab rentTab;

    @FXML
    private Tab leaseTab;

    @FXML
    private Tab announcementsTab;



    @FXML
    private Label userName1; 
    @FXML
    private Label userName2; 
    @FXML
    private Label userName3; 
    @FXML
    private Label userName4; 
    @FXML
    private Label userName5; 
    @FXML
    private Label userName6; 
    @FXML
    private Label userName7; 
    @FXML
    private Label userName8; 

    IDatabaseOperations databaseHandler = database_controller.getInstance();

    @FXML
    private Button logout;

    //Home page Stuff

    //Home page Stuff
    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneNumberLabel;

    //View Property Tab Stuff

    @FXML
    private Button homeButton;

    @FXML
    private Button viewPropertyPage;

    @FXML
    // private ListView<Propery> propertyListView = new ListView(FXCollections.observableList(Arrays.asList("one", "2", "3")));
    // private ListView<String> propertyListView = new ListView<>();

    private TableView<Property> propertyTableView;

    @FXML
    private TableColumn<Property, Integer> propertyIDCol;

    @FXML
    private TableColumn<Property, String> nameCol;

    @FXML
    private TableColumn<Property, String> amenitiesCol;

    @FXML
    private TableColumn<Property, String> addressCol;

    @FXML
    private TableColumn<Property, Integer> freeUnitsCol;

    private int selectedPropertyId;


    //My UNITS TAB STUFF

    private Unit currentUnit;

    @FXML
    private Text unitIDText;

    @FXML
    private Text propertyIDText;

    @FXML
    private Text floorplanText;

    @FXML
    private Text conditionText;

    @FXML
    private Text isFurnishedText;

    @FXML
    private Text isRentedText;

    // My Property Tab Stuff

    private Property currentProperty;


    @FXML
    private Text propertyIDText1;

    @FXML
    private Text nameText;

    @FXML
    private Text addressText;

    @FXML
    private Text amenitiesText;

    @FXML
    private Text communityAnnouncementsText;

    // Maintenace  Tab Stuff

    private int selectedRequestID;


    @FXML

    private TableView<MaintenanceRequest> maintenanceRequestTableView;

    @FXML
    private TableColumn<MaintenanceRequest, Boolean> isDealtWithCol;

    @FXML
    private TableColumn<MaintenanceRequest, Integer> requestIDCol;

    @FXML
    private TableColumn<MaintenanceRequest, Date> timestampCol;

    //Rent Tab Stuff:

    @FXML
    private Label rentAmountLabel;

    @FXML
    private Label rentDueLabel;

    @FXML
    private Label rentPaidLabel;

    //Lease Tab Stuff:

    @FXML
    private Label leasePaymentLabel;

    @FXML
    private Label leaseStartLabel;

    @FXML
    private Label leaseEndLabel;

    @FXML
    private Label leaseApprovedLabel;

    @FXML
    private Button viewLeaseUnit;

    @FXML
    private Button cancelLeaseButton;

    //Announcements Tab Stuff:


    @FXML
    private Button newAnnounce;
    private Button delAnnounce;

    @FXML
    private ListView announcementsListView;

    @FXML
    private ObservableList<String> communityAnnouncements = FXCollections.observableArrayList();






    public void initialize(Stage primaryStage, int userID)
    {
        try {
            String usr = UserSession.getInstance().getEmail();
            database_controller dbController = database_controller.getInstance(); 
            userName1.setText(dbController.getName(usr));
            userName2.setText(dbController.getName(usr));
            userName3.setText(dbController.getName(usr));
            userName4.setText(dbController.getName(usr));
            userName5.setText(dbController.getName(usr));
            userName6.setText(dbController.getName(usr));
            userName7.setText(dbController.getName(usr));
            userName8.setText(dbController.getName(usr));

            //Initialize homeTab

            nameLabel.setText(dbController.getName(usr));
            emailLabel.setText(UserSession.getInstance().getEmail());
            phoneNumberLabel.setText(dbController.getPhone(usr));

        } catch (IllegalStateException e) {
            UserSession.cleanUserSession();
            //username.setText("ERROR");
        }

        checkStatus();


        this.primaryStage = primaryStage;
        this.userID = UserSession.getInstance().getUserID();

        //View Property Tab Stuff
        this.selectedPropertyId = -1;
        propertyIDCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("propertyID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Property, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        amenitiesCol.setCellValueFactory(new PropertyValueFactory<Property, String>("amenities"));
        freeUnitsCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("freeUnits"));
        setupTable();

        //Remove these tabs if the user does not have unit.

        System.out.println(leaseStatus);

        if (leaseStatus == "none") {

            tabPane.getTabs().remove(myUnitTab);
            tabPane.getTabs().remove(myPropertyTab);
            tabPane.getTabs().remove(maintenanceTab);
            tabPane.getTabs().remove(rentTab);
            tabPane.getTabs().remove(leaseTab);
            tabPane.getTabs().remove(announcementsTab);
        }
        else if (leaseStatus == "pending")
        {
            //tabPane.getTabs().remove(myUnitTab);
            //tabPane.getTabs().remove(myPropertyTab);
            tabPane.getTabs().remove(maintenanceTab);
            tabPane.getTabs().remove(rentTab);
            tabPane.getTabs().remove(announcementsTab);
            initializeLease();
            initializeMyUnit();
            initializeMyProperty();
        }


        //Unit Tabs Stuff

        else
        {
            initializeMyUnit();
            initializeMyProperty();
            initializeMaintenance();
            initializeRent();
            initializeLease();
            initializeAnnouncements();
        }


    }

    public void checkStatus()
    {
        //Fetch the customer object

        List<Customers> getCustomerList = databaseHandler.customerList();

        currentCustomer = getCustomerList.get(0);

        System.out.println("The current userID is: " + UserSession.getInstance().getUserID());


        for (int i = 0; i < getCustomerList.size(); i++)
        {
            if (getCustomerList.get(i).getUserID() == UserSession.getInstance().getUserID())
            {
                currentCustomer = getCustomerList.get(i);
                System.out.println("Current customer FOUND! ->" + getCustomerList.get(i).getUnitID());
                break;
            }
        }

        System.out.println("Could not find the given user on customers!");

        System.out.println("Current Unit ID: " + currentCustomer.getUnitID());

        leaseStatus ="Error";


        //Check if the customer does not have any lease requests


        if (currentCustomer.getUnitID() == 0 && currentCustomer.isApproved() == false)
        {
            leaseStatus = "none";
        }
        else if (currentCustomer.getUnitID() != 0 && currentCustomer.isApproved() == false)
        {
            leaseStatus = "pending";
        }
        else {
            leaseStatus = "approved";
        }

    }

    public void initializeMyUnit()
    {
        List<Unit> getUnitList = databaseHandler.unitList();

        currentUnit = getUnitList.get(0);

        for (int i = 0; i < getUnitList.size(); i++) {
            if (getUnitList.get(i).getUnitID() == currentCustomer.getUnitID())
            {
                currentUnit = getUnitList.get(i);
                break;
            }
        }

        System.out.println("Intialized unit!");


        unitIDText.setText(String.valueOf(currentUnit.getUnitID()));
        propertyIDText.setText(String.valueOf(currentUnit.getPropertyID()));
        floorplanText.setText(String.valueOf(currentUnit.getFloorplan()));
        conditionText.setText(String.valueOf(currentUnit.getCondition()));
        isFurnishedText.setText(String.valueOf(currentUnit.isFurnished()));
        isRentedText.setText(String.valueOf(currentUnit.isRented()));
    }

    public void initializeMyProperty()
    {
        List<Property> getPropertyList = databaseHandler.propertyList();

        currentProperty = getPropertyList.get(0);

        for (int i = 0; i < getPropertyList.size(); i++) {
            if (getPropertyList.get(i).getPropertyID() == currentUnit.getPropertyID())
            {
                currentProperty = getPropertyList.get(i);
                break;
            }

        }

        propertyIDText1.setText(String.valueOf(currentProperty.getPropertyID()));
        nameText.setText(String.valueOf(currentProperty.getName()));
        addressText.setText(String.valueOf(currentProperty.getAddress()));
        amenitiesText.setText(String.valueOf(currentProperty.getAmenities()));
    }

    //Maintenance Tabs stuff

    @FXML
    private Button createRequestButton;

    private void initializeMaintenance(){

        isDealtWithCol.setCellValueFactory(new PropertyValueFactory<MaintenanceRequest, Boolean>("isDealtWith"));
        requestIDCol.setCellValueFactory(new PropertyValueFactory<MaintenanceRequest, Integer>("requestID"));
        timestampCol.setCellValueFactory(new PropertyValueFactory<MaintenanceRequest, Date>("timestamp"));


        List<MaintenanceRequest> getMaintenanceRequestList = databaseHandler.requestList();

        Collections.sort(getMaintenanceRequestList, new Comparator<MaintenanceRequest>() {
            @Override
            public int compare(MaintenanceRequest a, MaintenanceRequest b) {
                return Boolean.compare(a.getIsDealtWith(),b.getIsDealtWith());
            }
        });

        System.out.println(currentUnit.getPropertyID());

        System.out.println(getMaintenanceRequestList.size());

        maintenanceRequestTableView.getItems().clear();

        for (int i = 0; i < getMaintenanceRequestList.size(); i++) {
            if (getMaintenanceRequestList.get(i).getPropertyID() == currentUnit.getPropertyID() && getMaintenanceRequestList.get(i).getUnitID() == currentUnit.getUnitID() && getMaintenanceRequestList.get(i).getUserID() == UserSession.getInstance().getUserID() ) {
                maintenanceRequestTableView.getItems().addAll(getMaintenanceRequestList.get(i));
            }
        }
    }

    public void createMaintenanceRequest(ActionEvent event) throws IOException {
        System.out.println("Creating maintenance request");

        if (checkMaintenanceRequest())
        {
            /*
            URL url = getClass().getResource("/com/pages/homepages/customerPage.fxml");
            System.out.println(url.toString());
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            customerController cController = loader.getController();
            cController.initialize(primaryStage, userID);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();*/
        }
        initialize(primaryStage, userID);


    }



    public boolean checkMaintenanceRequest()
    {
            database_controller dbController = new database_controller();
            String registrationResult = dbController.createRequest(currentUnit.getPropertyID(), currentUnit.getUnitID(), UserSession.getInstance().getUserID());
            System.out.println("Request creation below: ");
            System.out.println(registrationResult);
            return "Success".equals(registrationResult);

    }

    @FXML
    void rowClickedProperty(MouseEvent event) {
        MaintenanceRequest clickedRequest = maintenanceRequestTableView.getSelectionModel().getSelectedItem();
        selectedRequestID = clickedRequest.getUnitID();
        System.out.println("selected maintenance request: " + clickedRequest);
    }

    //Rent tab Stuff

    @FXML
    private Button payRentButton;


    private void initializeRent()
    {
        rentAmountLabel.setText("Rent Amount: $" + String.valueOf(currentUnit.getRentAmount()));
        rentDueLabel.setText("Rent Due:    " + String.valueOf(currentUnit.getRentDue()));
        String rentStatus = "";
        if (String.valueOf(currentUnit.isRentPaid()).toLowerCase().equals("true")) 
        {
            rentStatus = "Paid";
        }
        else 
        {
            rentStatus = "Due";
        }
        rentPaidLabel.setText("Rent Status: " + rentStatus);

        if (currentUnit.isRentPaid())
        {
            payRentButton.setText("Rent Paid");
            payRentButton.isDisabled();
            payRentButton.setOnAction(null);
        }

    }

    @FXML
    void payRent(ActionEvent event) throws IOException {
        if (checkPayRent())
        {

        }
        initialize(primaryStage, userID);
    }

    boolean checkPayRent()
    {
        database_controller dbController = new database_controller();
        String payRentResult = dbController.payRent(currentUnit.getPropertyID(), currentUnit.getUnitID());
        return "Success".equals(payRentResult);

    }



    //Lease tab Stuff

    private void initializeLease()
    {

        leasePaymentLabel.setText("Payment Type: " + String.valueOf(currentCustomer.getPaymentType()));
        leaseStartLabel.setText("Lease Start:  " + String.valueOf(currentCustomer.getLeaseStart()));
        leaseEndLabel.setText("Lease End:    " + String.valueOf(currentCustomer.getLeaseEnd()));

        String leaseStatus = "";
        if (String.valueOf(currentCustomer.isApproved()).toLowerCase().equals("true"))
        {
            leaseStatus = "Current";
        }
        else 
        {
            leaseStatus = "Expired";
        }
        leaseApprovedLabel.setText("Lease Status: " + leaseStatus);

        if (currentCustomer.isApproved() == true)
        {
            viewLeaseUnit.setVisible(false);
        }


    }

    //Announcemnets Tab Stuff

    private void initializeAnnouncements()
    {
        List<String> getAnnouncements = currentProperty.getCommunityAnnouncements();

        announcementsListView.getItems().clear();


        for (int i = 0; i < getAnnouncements.size(); i++)
        {
            System.out.println(getAnnouncements.get(i));
            communityAnnouncements.add(getAnnouncements.get(i));
        }

        announcementsListView.setItems(communityAnnouncements);

    }


    public void userLogOut(ActionEvent event) throws IOException {
        /*MainApplication m = new MainApplication();
        m.changeScene("/com/resources/loginPage.fxml");*/
        UserSession.cleanUserSession(); // clean users session at logout 
        URL url = getClass().getResource("/com/pages/loginregister/loginPage.fxml");
        System.out.println(url.toString());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        loginController login = loader.getController();
        login.setStage(primaryStage);
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();


    }

    //Property Tab Stuff

    private void setupTable(){

        List<Property> getPropertyList = databaseHandler.propertyList();

        propertyTableView.getItems().clear();

        for (int i = 0; i < getPropertyList.size(); i++) {
            if (leaseStatus != "none")
            {
                if (getPropertyList.get(i).getPropertyID() != currentCustomer.getPropertyID())
                {
                    propertyTableView.getItems().addAll(getPropertyList.get(i));
                }
                else {

                }
            }
            else {
                propertyTableView.getItems().addAll(getPropertyList.get(i));
            }
        }
    }

    @FXML
    void rowClickedMaintenance(MouseEvent event) {
        Property clickedProperty = propertyTableView.getSelectionModel().getSelectedItem();
        selectedPropertyId = clickedProperty.getPropertyID();
        System.out.println("selected propertyID: " + clickedProperty);
    }

    public void goToPropertyPage(ActionEvent event) throws IOException {
      /*  System.out.println("Property View -> Customer Home Page");
        MainApplication m = new MainApplication();
        m.changeScene("/com/resources/propertyPage.fxml");*/



        URL url = getClass().getResource("/com/pages/property/propertyPage.fxml");
        System.out.println(url.toString());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        propertyController propertyViewController = loader.getController();
        propertyViewController.initialize(primaryStage, userID, propertyTableView.getSelectionModel().getSelectedItem().getPropertyID());
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();


    }

    public void goToUnitList(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/com/pages/unit/unitListPage.fxml");
        System.out.println(url.toString());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        unitListController unitListViewController = loader.getController();
        System.out.println("Passing propertyID: "+ currentProperty.getPropertyID());
        unitListViewController.initializeValues(primaryStage, userID, currentProperty.getPropertyID());
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();


    }

    public void cancelLease(ActionEvent event) throws IOException {
      /*  System.out.println("Property View -> Customer Home Page");
        MainApplication m = new MainApplication();
        m.changeScene("/com/resources/propertyPage.fxml");*/
        if (checkRequest())
        {

        }
            initialize(primaryStage, UserSession.getInstance().getUserID());


    }



    public boolean checkRequest()
    {


            database_controller dbController = new database_controller();
            String registrationResult = dbController.cancelLeaseRequest(UserSession.getInstance().getUserID());
            return "Success".equals(registrationResult);


    }




}