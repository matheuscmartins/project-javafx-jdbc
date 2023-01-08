package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.Db.DbException;
import com.udemy.projectjavafxjdbc.gui.util.Alerts;
import com.udemy.projectjavafxjdbc.gui.util.Constraints;
import com.udemy.projectjavafxjdbc.gui.util.Utils;
import com.udemy.projectjavafxjdbc.model.entites.Department;
import com.udemy.projectjavafxjdbc.model.services.DepartmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private Department entity;
    private DepartmentService service;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtname;
    @FXML
    private Label labeError;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    public void setDepartment(Department entity) {
        this.entity = entity;
    }
    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }
    private Department getFormData() {
        Department department = new Department();
        department.setId(Utils.tryParseToInt(txtId.getText()));
        department.setName(txtname.getText());
        return department;
    }
    @FXML
    public void onBtnSaveAction(ActionEvent event) {
        if (entity == null){
            throw new IllegalStateException("Entity was null");
        }
        if (service == null){
            throw new IllegalStateException("service was null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            Utils.currentStage(event).close();

        } catch (DbException e){
            Alerts.showAlert("Error saving object",null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onBtnCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtname, 30);
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtname.setText(String.valueOf(entity.getName()));
    }
}
