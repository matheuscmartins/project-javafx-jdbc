package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.Db.DbIntegrityException;
import com.udemy.projectjavafxjdbc.application.Main;
import com.udemy.projectjavafxjdbc.gui.listeners.DataChangeListener;
import com.udemy.projectjavafxjdbc.gui.util.Alerts;
import com.udemy.projectjavafxjdbc.gui.util.Utils;
import com.udemy.projectjavafxjdbc.model.entites.Department;
import com.udemy.projectjavafxjdbc.model.entites.Seller;
import com.udemy.projectjavafxjdbc.model.services.DepartmentService;
import com.udemy.projectjavafxjdbc.model.services.SellerService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerListController implements Initializable, DataChangeListener {

    private SellerService service;
    @FXML
    private TableView<Seller> sellerTableView;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;
    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;
    @FXML
    private Button btnNew;

    private ObservableList<Seller> obsList;

    @FXML
    public void onBtnNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event); //pega a refencia do stage vindo no parametro
        Seller seller = new Seller();
       // createdDialogForm(seller, "/com/udemy/projectjavafxjdbc/gui/SellerForm.fxml", parentStage);
    }

    public void setSellerService(SellerService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeNodes();
    }

    private void initializeNodes() {
        //padrão para inicializar o padrão das colunas
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow(); //faz um downcasting para Stege
        sellerTableView.prefHeightProperty().bind(stage.heightProperty()); //faz o TableView acompanhar a altura da janela
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        sellerTableView.setItems(obsList);
       // initEditButtons();
        initRemoveButtons();
    }
/*
    public void createdDialogForm(Seller seller, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            //trata atualização do obj Department
            SellerFormController controller = loader.getController();
            controller.setSeller(seller); //injeta um Seller
            controller.setSellerService(new SellerService()); //injeta um SellerService
            controller.subscribeDataChangeListner(this); //se auto escreve para receber o evendo listner
            controller.updateFormData();

            Stage dialogStage = new Stage(); //instancia um novo Stage
            dialogStage.setTitle("Enter Seller data");  //titulo do Stage
            dialogStage.setScene(new Scene(pane)); //instancia uma nova Scena passando o Pane como parametro
            dialogStage.setResizable(false); //não deixa a janela ser redimencionada
            dialogStage.initOwner(parentStage); //informa quem é o pai da janela na hierarquia
            dialogStage.initModality(Modality.WINDOW_MODAL); //define a janela como modal
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error load view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
*/
    @Override
    public void onDataChanged() {
        updateTableView();
    }
/*
    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createdDialogForm(
                                obj, "/com/udemy/projectjavafxjdbc/gui/SellerForm.fxml", Utils.currentStage(event)));
            }
        });
    }
*/
    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Seller obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }
            try {
                service.remove(obj);
                updateTableView();
            } catch (DbIntegrityException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
