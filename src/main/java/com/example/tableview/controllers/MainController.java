package com.example.tableview.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class MainController {
    @FXML
    private MenuItem importItem;

    private final SceneController sceneController = new SceneController();

    @FXML
    public void onPressedImport (ActionEvent event) throws IOException {
        sceneController.switchToTableScene(event);
    }
}
