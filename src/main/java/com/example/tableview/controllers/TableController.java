package com.example.tableview.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class TableController {
    @FXML
    private AnchorPane mainLayout;
    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private VBox vBox;
    private List<List<String>> tableContents;
    private List<String>  tableHead;
    private final SceneController sceneController = new SceneController();

    public void onClose (ActionEvent event) throws IOException {
        sceneController.switchToMainScene(event);
    }

    public void innit (List<List<String>> table) {
        table.get(0).addFirst("");
        for (int i = 1; i < table.size(); i++) {
            table.get(i).addFirst(String.valueOf(i));
        }

        tableContents = table.subList(1, table.size());
        tableHead = table.get(0);

        for (int i = 0; i < tableContents.get(0).size(); i++) {
            TableColumn<List<String>, String> column = getListStringTableColumn(i);

            column.setSortable(true);

            column.setComparator((value1, value2) -> {
                try {
                    double doubleValue1 = Double.parseDouble(value1);
                    double doubleValue2 = Double.parseDouble(value2);
                    return Double.compare(doubleValue1, doubleValue2);
                } catch (NumberFormatException e) {
                    return value1.compareTo(value2);
                }
            });

            tableView.getColumns().add(column);
        }


        mainLayout.widthProperty().addListener((observableValue, oldWidth, newWidth) ->
                vBox.setPrefWidth(newWidth.doubleValue()));

        mainLayout.heightProperty().addListener((observableValue, oldHeight, newHeight) ->
                vBox.setPrefHeight(newHeight.doubleValue()));

        ObservableList<List<String>> observableTable = FXCollections.observableArrayList(tableContents);

        tableView.setItems(observableTable);
    }

    private TableColumn<List<String>, String> getListStringTableColumn(int i) {
        final int columnIndex = i;

        TableColumn<List<String>, String> column = new TableColumn<>(tableHead.get(i));
        column.setCellValueFactory(cellData -> {
            List<String> rowData = cellData.getValue();
            if (columnIndex < rowData.size()) {
                return new SimpleStringProperty(rowData.get(columnIndex));
            } else {
                return new SimpleStringProperty("");
            }
        });
        return column;
    }

    public void saveToFile () {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (PrintWriter pw = new PrintWriter(file)) {
                int[] columnWidths = new int[tableHead.size()];
                for (List<String> row : tableContents) {
                    for (int i = 0; i < row.size(); i++) {
                        columnWidths[i] = Math.max(columnWidths[i], row.get(i).length());
                    }
                }

                for (int i = 0; i < tableHead.size(); i++) {
                    pw.printf("%-" + (columnWidths[i] + 2) + "s", tableHead.get(i));
                }
                pw.println();

                for (List<String> row : tableContents) {
                    for (int i = 0; i < row.size(); i++) {
                        pw.printf("%-" + (columnWidths[i] + 2) + "s", row.get(i));
                    }
                    pw.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
