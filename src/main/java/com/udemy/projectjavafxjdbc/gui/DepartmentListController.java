package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.application.Main;
import com.udemy.projectjavafxjdbc.model.entites.Department;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {
    @FXML
    private TableView<Department> departmentTableView;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btnNew;

    @FXML
    public void onBtnNewAction(){
        System.out.println("onBtnNewAction");
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
}
