package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class main1 extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UIframe/loaderNyancat.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(loader.load(), 1280, 800);
        scene.setFill(Color.BLACK);

        primaryStage.setTitle("QLST");
        primaryStage.setScene(scene);
        primaryStage.show();
    }}

