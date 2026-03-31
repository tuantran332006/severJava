package org.example.controllerForapp;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class controllerLoader {

    @FXML
    private StackPane rootPane;

    @FXML
    private ImageView nyanImageView;

    @FXML
    private Circle purpleOverlay;

    @FXML
    private HBox helloBox;

    @FXML
    private ImageView helloIcon;

    @FXML
    public void initialize() {
        Image gif = new Image(
                getClass().getResource("/image/nyan-cat-cat.gif").toExternalForm()
        );
        nyanImageView.setImage(gif);
        playIntro();
    }

    private void playIntro() {
        PauseTransition catPhase = new PauseTransition(Duration.seconds(2.2));

        catPhase.setOnFinished(e -> {
            FadeTransition catFade = new FadeTransition(Duration.seconds(0.5), nyanImageView);
            catFade.setFromValue(1.0);
            catFade.setToValue(0.0);

            purpleOverlay.setVisible(true);
            purpleOverlay.setOpacity(1);

            ScaleTransition overlayScale = new ScaleTransition(Duration.seconds(0.8), purpleOverlay);
            overlayScale.setFromX(1);
            overlayScale.setFromY(1);
            overlayScale.setToX(30);
            overlayScale.setToY(30);

            ParallelTransition spreadPurple = new ParallelTransition(catFade, overlayScale);
            spreadPurple.setOnFinished(ev -> showHello());
            spreadPurple.play();
        });

        catPhase.play();
    }

    private void showHello() {
        helloBox.setVisible(true);

        FadeTransition helloFade = new FadeTransition(Duration.seconds(0.6), helloBox);
        helloFade.setFromValue(0.0);
        helloFade.setToValue(1.0);

        ScaleTransition helloPop = new ScaleTransition(Duration.seconds(0.6), helloBox);
        helloBox.setScaleX(0.85);
        helloBox.setScaleY(0.85);
        helloPop.setToX(1.0);
        helloPop.setToY(1.0);

        ParallelTransition helloIntro = new ParallelTransition(helloFade, helloPop);

        helloIntro.setOnFinished(e -> {
            PauseTransition hold = new PauseTransition(Duration.seconds(1.0));
            hold.setOnFinished(ev -> zoomIconAndOpenLogin());
            hold.play();
        });

        helloIntro.play();
    }

    private void zoomIconAndOpenLogin() {
        ScaleTransition iconZoom = new ScaleTransition(Duration.seconds(0.8), helloIcon);
        iconZoom.setToX(22);
        iconZoom.setToY(22);

        FadeTransition textFade = new FadeTransition(Duration.seconds(0.4), helloBox);
        textFade.setFromValue(1.0);
        textFade.setToValue(0.0);

        ParallelTransition lastAnim = new ParallelTransition(iconZoom, textFade);
        lastAnim.setOnFinished(e -> openLogin());
        lastAnim.play();
    }

    private void openLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIframe/loginPage.fxml"));

            Parent loginRoot = loader.load();
            loginRoot.setOpacity(0);

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.getScene().setRoot(loginRoot);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), loginRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}