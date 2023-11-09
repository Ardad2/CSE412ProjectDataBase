package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.IOException;

public class Register {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Label errorLabel;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    public void returnHome(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("hello-view.fxml");

    }

    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();

    }

    private void checkLogin() throws IOException {
        final String JDBC_DRIVER = "org.postgresql.Driver";
        final String DB_URL = "jdbc:postgresql://localhost:8888/cse412project";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);

            // Query only for email and password, the role will be determined from the result
            String sql = "SELECT ROLE FROM users WHERE email = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email.getText());
            stmt.setString(2, password.getText());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("ROLE");

                HelloApplication m = new HelloApplication();

                if ("PropertyManager".equals(role)) {
                    m.changeScene("homepageManager.fxml");
                } else if ("Customer".equals(role)) {
                    m.changeScene("homepageCustomer.fxml");
                } else {
                    // Handle any other roles if required
                    wrongLogin.setText("Unrecognized role!");
                }
            } else if (email.getText().isEmpty() && password.getText().isEmpty()) {
                wrongLogin.setText("Please enter your data.");
            } else {
                wrongLogin.setText("Wrong email or password!");
            }

            // Clean up
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            wrongLogin.setText("Error connecting to the database.");
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception se) {
                se.printStackTrace();
            }
        }
    }

}