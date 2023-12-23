package com.example.tableview.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class TableController {

    private final SceneController sceneController = new SceneController();

    public void onClose (ActionEvent event) throws IOException {
        sceneController.switchToMainScene(event);
    }
}
