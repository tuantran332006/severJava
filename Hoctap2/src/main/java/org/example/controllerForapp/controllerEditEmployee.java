package org.example.controllerForapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Component;

@Component
public class controllerEditEmployee {

    @FXML
    private BorderPane popupPane;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtStatus;

    private controllerUIemployee parentController;

    @FXML
    public void initialize() {
        Rectangle clip = new Rectangle();
        clip.setArcWidth(52);
        clip.setArcHeight(52);

        clip.widthProperty().bind(popupPane.widthProperty());
        clip.heightProperty().bind(popupPane.heightProperty());

        popupPane.setClip(clip);

        // dữ liệu mẫu tạm thời
        txtName.setText("Nguyễn Văn An");
        txtRole.setText("Quản lý kho");
        txtEmail.setText("nguyenvana@gmail.com");
        txtPhone.setText("0123456798");
        txtStatus.setText("Đang làm việc");
    }

    //đặt PaneParent
    public void setParentController(controllerUIemployee parentController) {
        this.parentController = parentController;
    }

    @FXML //huỷ sửa dữ liệu
    private void handleCancel(ActionEvent event) {
        if (parentController != null) {
            parentController.closeOverlay();
        }
    }

    @FXML // xác nhận sửa dữ liệu
    private void handleConfirm(ActionEvent event) {
        System.out.println("Xác nhận sửa nhân viên");
        System.out.println("Tên: " + txtName.getText());
        System.out.println("Chức vụ: " + txtRole.getText());
        System.out.println("Email: " + txtEmail.getText());
        System.out.println("SĐT: " + txtPhone.getText());
        System.out.println("Trạng thái: " + txtStatus.getText());

        if (parentController != null) {
            parentController.closeOverlay();
        }
    }
}