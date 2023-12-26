package com.example.tableview.controllers;

import com.example.tableview.utils.FileIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/com/example/tableview/main-view.fxml"))
        );
        stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass()
                .getResource("/com/example/tableview/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToTableScene(ActionEvent event, Path filePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/tableview/table-view.fxml")
        );

        root = loader.load();

        TableController tableController = loader.getController();
        if (filePath.toString().toLowerCase().endsWith(".txt")) {
            tableController.innit(FileIO.readTxt(filePath));
        } else {
            tableController.innit(FileIO.readExcel(filePath));
        }

        stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass()
                .getResource("/com/example/tableview/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }
}
