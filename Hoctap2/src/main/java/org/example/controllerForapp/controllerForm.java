package org.example.controllerForapp;

import com.example.demo.service.DangNhapService;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class controllerForm {

    @FXML private AnchorPane imagePane;
    @FXML private AnchorPane formPane;
    @FXML private VBox loginForm;
    @FXML private VBox registerForm;
    @FXML private Pane transitionPane;
    @FXML private Button loginBtn;
    @FXML private Pane loaderPane;
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;
    @FXML private Label messageLabel;

    private boolean isLoginMode = true;

    @FXML
    public void showRegisterForm() {
        if (!isLoginMode) return;

        swapPanels();
        loginForm.setVisible(false);
        loginForm.setManaged(false);
        registerForm.setVisible(true);
        registerForm.setManaged(true);
        fadeIn(registerForm);
        isLoginMode = false;
        messageLabel.setVisible(false);
    }

    @FXML
    public void showLoginForm() {
        if (isLoginMode) return;

        swapPanels();
        registerForm.setVisible(false);
        registerForm.setManaged(false);
        loginForm.setVisible(true);
        loginForm.setManaged(true);
        fadeIn(loginForm);
        isLoginMode = true;
        messageLabel.setVisible(false);
    }

    private void swapPanels() {
        TranslateTransition imageTransition = new TranslateTransition(Duration.seconds(0.6), imagePane);
        TranslateTransition formTransition = new TranslateTransition(Duration.seconds(0.6), formPane);

        if (isLoginMode) {
            imageTransition.setToX(380);
            formTransition.setToX(-380);
        } else {
            imageTransition.setToX(0);
            formTransition.setToX(0);
        }

        imageTransition.play();
        formTransition.play();
    }

    private void fadeIn(VBox form) {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.4), form);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }

    public void playLoader() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1.5), loaderPane);
        rotateTransition.setByAngle(90);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), loaderPane);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0.4);
        scaleTransition.setToY(0.4);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(Animation.INDEFINITE);

        ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, scaleTransition);
        parallelTransition.play();
    }

    @FXML
    public void handleLoginbtn() {
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        if (username.isBlank() || password.isBlank()) {
            messageLabel.setText("Chưa nhập đủ thông tin");
            messageLabel.setVisible(true);
            return;
        }

        DangNhapService.UserView user = dangNhapService.dangNhap(username, password);

        if (user != null) {
            loaderPane.setVisible(true);
            formPane.setVisible(false);
            imagePane.setVisible(false);
            playLoader();

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> openMainUI());
            pause.play();
        } else {
            messageLabel.setText("Sai tài khoản hoặc mật khẩu");
            messageLabel.setVisible(true);
        }
    }

    public void openMainUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIframe/uxLayout.fxml"));
            loader.setControllerFactory(applicationContext::getBean);

            Parent root = loader.load();

            Stage stage = (Stage) loaderPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            loginBtn.setDisable(false);
            messageLabel.setText("Không mở được giao diện chính");
        }
    }
}