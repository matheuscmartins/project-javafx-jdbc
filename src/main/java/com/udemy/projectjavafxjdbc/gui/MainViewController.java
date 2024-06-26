package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.application.Main;
import com.udemy.projectjavafxjdbc.gui.util.Alerts;
import com.udemy.projectjavafxjdbc.model.services.DepartmentService;
import com.udemy.projectjavafxjdbc.model.services.SellerService;
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
import java.util.function.Consumer;

public class MainViewController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        loadView("/com/udemy/projectjavafxjdbc/gui/SellerList.fxml",
                (SellerListController controller) -> {
                    controller.setSellerService(new SellerService());
                    controller.updateTableView();
                });
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        loadView("/com/udemy/projectjavafxjdbc/gui/DepartmentList.fxml",
                (DepartmentListController controller) -> {
                    controller.setDepartmentService(new DepartmentService());
                    controller.updateTableView();
                });
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("/com/udemy/projectjavafxjdbc/gui/About.fxml",
                x -> {
                });

    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
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

            T controller = loader.getController(); //retorna um DepartmentControler generics
            initializingAction.accept(controller); //executa a ação vinda por paramentro na initializingAction

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error load view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


}
