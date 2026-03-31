package org.example.controllerForapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class controllerUIlayout {

    @FXML
    private Button sidebarTongquan;
    @FXML
    private Button sidebarSanpham;
    @FXML
    private Button sidebarKhohang;
    @FXML
    private Button sidebarDonhang;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private ScrollPane scrollPane;

    @FXML //mở layout và load sẵn file overview
    public void initialize() {
        loadPage("/UIframe/uxOverview.fxml");
        setActiveButton(sidebarTongquan);
    }

    @FXML // mở file khi click vào sidebar
    private void handleSidebarClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton == sidebarTongquan) {
            loadPage("/UIframe/uxOverview.fxml");
        } else if (clickedButton == sidebarSanpham) {
            loadPage("/UIframe/uxProduct.fxml");
        } else if (clickedButton == sidebarKhohang) {
            loadPage("/UIframe/uxEmployee.fxml");
        } else if (clickedButton == sidebarDonhang) {
            loadPage("/UIframe/uxBill.fxml");
        }

        setActiveButton(clickedButton);
    }

    //hàm load đè vào anchor trong file layout
    private void loadPage(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentPane.getChildren().setAll(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //xử lí hiệu ứng sidebar
    private void setActiveButton(Button button) {
        sidebarTongquan.getStyleClass().remove("active");
        sidebarSanpham.getStyleClass().remove("active");
        sidebarKhohang.getStyleClass().remove("active");
        sidebarDonhang.getStyleClass().remove("active");

        if (!button.getStyleClass().contains("active")) {
            button.getStyleClass().add("active");
        }
    }
}