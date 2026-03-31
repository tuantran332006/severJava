package org.example.controllerForapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class controllerUIemployee {

    @FXML
    private BorderPane mainContainer;

    @FXML
    private StackPane overlayContainer;
    @FXML
    private ScrollPane scrollPane;

    @FXML //xoá các scrollbar ko cần thiết
    public void initialize() {
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @FXML // mở các thẻ nhân viên
    private void openDetail(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/UIframe/informationCard.fxml")
            );

            Parent infoCard = loader.load();

            controllerInforEmployee main2 = loader.getController();
            main2.setParentController(this);

            overlayContainer.getChildren().clear();
            overlayContainer.getChildren().add(infoCard);

            overlayContainer.setVisible(true);
            overlayContainer.setManaged(true);

            mainContainer.setEffect(new GaussianBlur(18));

            overlayContainer.setOnMouseClicked(e -> closeOverlay());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //đóng tab nhân viên
    public void closeOverlay() {
        overlayContainer.getChildren().clear();
        overlayContainer.setVisible(false);
        overlayContainer.setManaged(false);
        mainContainer.setEffect(null);
    }

    //hiển thị thẻ nhân viên
    public void showOverlay(Parent content) {
        overlayContainer.setOnMouseClicked(e -> closeOverlay());
        content.setOnMouseClicked(e -> e.consume());

        overlayContainer.getChildren().setAll(content);
        overlayContainer.setVisible(true);
        overlayContainer.setManaged(true);
        mainContainer.setEffect(new javafx.scene.effect.GaussianBlur(18));
    }
}