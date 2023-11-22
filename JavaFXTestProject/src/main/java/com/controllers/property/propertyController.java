package com.controllers.property;

import com.controllers.unit.unitListController;
import com.db.IDatabaseOperations;
import com.db.database_controller;
import com.models.Property;
import com.models.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class propertyController {

    //Display Data

    private Property currentProperty;
    private Users currentUser;

    private List<String> amenities;
    private int propertyID;
    private String address;
    private String name;
    private List<String> communityAnnouncements;

    private Stage primaryStage;

    private int userID;



    IDatabaseOperations databaseHandler = database_controller.getInstance();

    @FXML
    private Button homeButton;

    @FXML
    private Button viewUnitsButton;

    @FXML
    private Button announcementsButton;

    @FXML
    private Text propertyIDText;

    @FXML
    private Text nameText;

    @FXML
    private Text addressText;

    @FXML
    private Text amenitiesText;

    @FXML
    private Text communityAnnouncementsText;

    public void initialize(Stage primaryStage, int userID, int propertyID)
    {
        this.primaryStage = primaryStage;
        this.userID = userID;
        this.propertyID = propertyID;
        propertyIDText.setText(String.valueOf(this.propertyID));

        List<Property> getPropertyList = databaseHandler.propertyList();

        currentProperty = getPropertyList.get(0);

        for (int i = 0; i < getPropertyList.size(); i++) {
            if (getPropertyList.get(i).getPropertyID() == propertyID)
            {
                currentProperty = getPropertyList.get(i);
                break;
            }

        }

        List<Users> getUsersList = databaseHandler.userList();
        currentUser = getUsersList.get(0);

        for (int i = 0; i < getUsersList.size(); i++) {
            if (getUsersList.get(i).getUserID() == userID)
            {
                currentUser = getUsersList.get(i);
                break;
            }

        }




        this.amenities = currentProperty.getAmenities();
        this.address = currentProperty.getAddress();
        this.name = currentProperty.getName();
        this.communityAnnouncements = currentProperty.getCommunityAnnouncements();

        amenitiesText.setText(amenities.get(0));
        addressText.setText(address);
        nameText.setText(name);
        communityAnnouncementsText.setText(communityAnnouncements.get(0));

        System.out.println(currentUser.getUserID());
        System.out.println(currentUser.getRole());


        if (currentUser.getRole().equals("Manager"))
        {
            announcementsButton.setVisible(true);
        }
        else {
            announcementsButton.setVisible(false);
        }


    }


    public void goBack(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/com/pages/property/propertyListPage.fxml");
        System.out.println(url.toString());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        propertyListController propertyListViewController = loader.getController();
        propertyListViewController.initialize(primaryStage, userID);
        primaryStage.setScene(new Scene(root));
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
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }

}
