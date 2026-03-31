package org.example.controllerForapp;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

@Component
public class controllerCreateBill {

    private controllerUIbill parentController;

    public void setParentController(com.example.demo.controllerForapp.controllerUIbill parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void handleClose() {
        if (parentController != null) {
            parentController.closeOverlay();
        }
    }

    @FXML
    private void handleAddProduct() {
        System.out.println("Thêm sản phẩm");
    }

//    @FXML
//    private void handleSearchProduct() {
//        System.out.println("Tìm sản phẩm");
//    }

    @FXML
    private void handleSaveDraft() {
        System.out.println("Lưu tạm");
    }

    @FXML
    private void handleCreateInvoice() {
        System.out.println("Tạo hóa đơn");
    }
}