package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.application.Main;
import com.udemy.projectjavafxjdbc.gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        loadView("/com/udemy/projectjavafxjdbc/gui/DepartmentList.fxml");
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("/com/udemy/projectjavafxjdbc/gui/About.fxml");

    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {

    }

    private synchronized void loadView(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();

            Scene mainScene = Main.getMainScene(); // pega referencia do Scene da main
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            //faz um casting dos componentes na mainScene para ScrollPane depois outro Casting para VBox

            Node mainMenu = mainVbox.getChildren().get(0); //pega o primeiro filho do Vbox da janela principal
            mainVbox.getChildren().clear();  //Limpa todos os filhos do mainVbox
            mainVbox.getChildren().add(mainMenu); //pega os filhos do mainMenu
            mainVbox.getChildren().add(newVbox); //pega os filhos do newVbox
            mainVbox.getChildren().addAll(newVbox.getChildren()); //adiciona uma coleção pegando os filhos do newVbox

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error load view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
