package org.example.controllerForapp;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class controllerUIoverview {

    @FXML
    private LineChart<String, Number> revenueChart;

    @FXML
    private BarChart<String, Number> orderChart;

    @FXML
    private javafx.scene.control.Button sidebarTongquan;
    @FXML
    private javafx.scene.control.Button sidebarSanpham;
    @FXML
    private javafx.scene.control.Button sidebarKhohang; // Con thieu 2 cai sidebar//
    @FXML
    private Button sidebarDonhang;
    @FXML
    private  Button sidebarDanhmuc;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private ScrollPane scrollPane;


    public void initialize(){
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFocusTraversable(false);
    }


}