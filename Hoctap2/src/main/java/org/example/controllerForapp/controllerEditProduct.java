package org.example.controllerForapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class controllerEditProduct {

    @FXML
    private ComboBox<String> cbDanhMuc;

    @FXML
    private ComboBox<String> cbTrangThai;

    @FXML
    private TextArea txtMoTa;

    private com.example.demo.controllerForapp.controllerUIproduct parentController;

    @FXML
    public void initialize() {
        cbDanhMuc.setItems(FXCollections.observableArrayList(
                "Đồ uống", "Bánh kẹo", "Thực phẩm", "Gia dụng", "Mỹ phẩm"
        ));

        cbTrangThai.setItems(FXCollections.observableArrayList(
                "Còn hàng", "Hết hàng", "Ngừng kinh doanh"
        ));
    }

    public void setParentController(com.example.demo.controllerForapp.controllerUIproduct parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void handleCancel() {
        if (parentController != null) {
            parentController.closeOverlay();
        }
    }
    @FXML
    private void handleSave() {
        System.out.println("Bấm lưu (test)");

        if (parentController != null) {
            parentController.closeOverlay();
        }
    }
}