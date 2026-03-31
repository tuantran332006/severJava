package org.example.controllerForapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class controllerUIbill {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ScrollPane scrollPane;

    private StackPane currentOverlay;

    @FXML
    public void initialize(){
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
    @FXML
    private void createBill() {
        try {
            if (currentOverlay != null) return;

            System.out.println("Mở form tạo hóa đơn");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIframe/createBillForm.fxml"));
            Parent form = loader.load();

            controllerCreateBill childController = loader.getController();
            childController.setParentController(this);

            showOverlay(form);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= EDIT BILL =================
    @FXML
    private void handleEditBill(ActionEvent event) {
        try {
            if (currentOverlay != null) return;

            System.out.println("Mở form chỉnh sửa hóa đơn");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIframe/createBillForm.fxml"));
            Parent form = loader.load();

            showOverlay(form);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE BILL =================
//    @FXML
//    private void deleteBill(ActionEvent event) {
//        System.out.println("Xóa hóa đơn");
//    }

    private void showOverlay(Parent form) {
        StackPane overlay = new StackPane();
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.35);");

        AnchorPane.setTopAnchor(overlay, 0.0);
        AnchorPane.setBottomAnchor(overlay, 0.0);
        AnchorPane.setLeftAnchor(overlay, 0.0);
        AnchorPane.setRightAnchor(overlay, 0.0);

        AnchorPane backdrop = new AnchorPane();
        backdrop.setStyle("-fx-background-color: transparent;");
        backdrop.setOnMouseClicked(e -> closeOverlay());

        StackPane formHolder = new StackPane(form);
        formHolder.setPickOnBounds(false);

        GaussianBlur blur = new GaussianBlur(12);
        for (Node node : rootPane.getChildren()) {
            node.setEffect(blur);
        }

        overlay.getChildren().addAll(backdrop, formHolder);

        currentOverlay = overlay;
        rootPane.getChildren().add(currentOverlay);
    }

    public void closeOverlay() {
        if (currentOverlay != null) {
            rootPane.getChildren().remove(currentOverlay);
            currentOverlay = null;
        }

        for (Node node : rootPane.getChildren()) {
            node.setEffect(null);
        }
    }
}