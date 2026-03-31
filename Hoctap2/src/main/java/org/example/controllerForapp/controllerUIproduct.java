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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class controllerUIproduct {

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private VBox listProduct;

    @FXML
    private AnchorPane rootPane;

    private StackPane currentOverlay;

    private controllerUIproduct parentController;

    public void setParentController(controllerUIproduct parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void initialize() {
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Rectangle clip = new Rectangle();
        clip.setArcWidth(40);
        clip.setArcHeight(40);

        listProduct.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
            clip.setWidth(newVal.getWidth());
            clip.setHeight(newVal.getHeight());
        });

        listProduct.setClip(clip);
    }

    @FXML
    private void addProduction() {
        try {
            if (currentOverlay != null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIframe/addProductForm.fxml"));
            Parent form = loader.load();

            controllerUIproduct childController = loader.getController();
            childController.setParentController(this);

            showOverlay(form);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editAction(ActionEvent event) {
        try {
            if (currentOverlay != null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIframe/editProductForm.fxml"));
            Parent form = loader.load();

            controllerEditProduct childController = loader.getController();
            childController.setParentController(this);

            showOverlay(form);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        try {
            Button clickedButton = (Button) event.getSource();
            HBox actionBox = (HBox) clickedButton.getParent();
            HBox productRow = (HBox) actionBox.getParent();
            VBox productListWrapper = (VBox) productRow.getParent();

            productListWrapper.getChildren().remove(productRow);

            System.out.println("Đã xóa sản phẩm khỏi giao diện");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}