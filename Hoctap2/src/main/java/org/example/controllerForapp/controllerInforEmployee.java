package org.example.controllerForapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;


public class controllerInforEmployee {

    @FXML private Label lblName;
    @FXML private Label lblEmail;
    @FXML private Label lblPhone;
    @FXML private Label lblShift;
    @FXML private Label lblStatus;

    private controllerUIemployee parentController;

    public void setParentController(controllerUIemployee parentController) {
        this.parentController = parentController;
    }

    @FXML // khởi tạo cơ sở dữ liệu ở đây, các cái dưới là test
    public void initialize() {
        lblName.setText("Nguyễn Văn An");
        lblEmail.setText("nguyenvana@gmail.com");
        lblPhone.setText("0123456798");
        lblShift.setText("Sáng");
        lblStatus.setText("Đang làm việc");
    }

    @FXML // mở pane edit thông tin nhân viên
    private void handleEdit(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIframe/editEmployee.fxml"));
            Parent editTab = loader.load();
            controllerEditEmployee loaderController = loader.getController();
            loaderController.setParentController(parentController);
            parentController.showOverlay(editTab);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML // xoá thông tin nhân viên *chưa hoàn thành
    private void handleDelete(ActionEvent event) {
        System.out.println("Xóa");
    }

    @FXML
    private void handleClose(ActionEvent event) {
        if (parentController != null) {
            parentController.closeOverlay();
        }
    }
}