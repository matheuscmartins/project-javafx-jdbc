package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.application.Main;
import com.udemy.projectjavafxjdbc.gui.util.Alerts;
import com.udemy.projectjavafxjdbc.gui.util.Utils;
import com.udemy.projectjavafxjdbc.model.entites.Department;
import com.udemy.projectjavafxjdbc.model.services.DepartmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {

    private DepartmentService service;
    @FXML
    private TableView<Department> departmentTableView;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btnNew;

    private ObservableList<Department> obsList;

    @FXML
    public void onBtnNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event); //pega a refencia do stage vindo no parametro
        Department department = new Department();
        createdDialogFrom(department, "/com/udemy/projectjavafxjdbc/gui/DepartmentForm.fxml", parentStage);
    }

    public void setDepartmentService(DepartmentService service) {
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
        departmentTableView.prefHeightProperty().bind(stage.heightProperty()); //faz o TableView acompanhar a altura da janela
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        departmentTableView.setItems(obsList);
    }

    public void createdDialogFrom(Department department, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            //trata atualização do obj Department
            DepartmentFormController controller = loader.getController();
            controller.setDepartment(department); //injeta um department
            controller.setDepartmentService(new DepartmentService()); //injeta um departmentService
            controller.updateFormData();

            Stage dialogStage = new Stage(); //instancia um novo Stage
            dialogStage.setTitle("Enter Department data");  //titulo do Stage
            dialogStage.setScene(new Scene(pane)); //instancia uma nova Scena passando o Pane como parametro
            dialogStage.setResizable(false); //não deixa a janela ser redimencionada
            dialogStage.initOwner(parentStage); //informa quem é o pai da janela na hierarquia
            dialogStage.initModality(Modality.WINDOW_MODAL); //define a janela como modal
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error load view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
