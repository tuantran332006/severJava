package org.example.controllerForapp;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class controllerAddProduct {
    @FXML
    public void initialize() {
    }

    private controllerUIproduct parentController;

    public void setParentController(controllerUIproduct parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void handleClose(ActionEvent event) {
        if (parentController != null) {
            parentController.closeOverlay();
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (parentController != null) {
            parentController.closeOverlay();
        }
    }
}