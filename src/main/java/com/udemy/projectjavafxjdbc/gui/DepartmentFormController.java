package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.Db.DbException;
import com.udemy.projectjavafxjdbc.gui.listeners.DataChangeListener;
import com.udemy.projectjavafxjdbc.gui.util.Alerts;
import com.udemy.projectjavafxjdbc.gui.util.Constraints;
import com.udemy.projectjavafxjdbc.gui.util.Utils;
import com.udemy.projectjavafxjdbc.model.entites.Department;
import com.udemy.projectjavafxjdbc.model.exceptions.ValidationException;
import com.udemy.projectjavafxjdbc.model.services.DepartmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class DepartmentFormController implements Initializable {

    private Department entity;
    private DepartmentService service;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
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
        ValidationException exception = new ValidationException("Validation error");

        department.setId(Utils.tryParseToInt(txtId.getText()));
        if (txtName.getText() == null || txtName.getText().trim().equals("") || txtName.getText() == "null"){
            exception.addError("name", "Field can't be empty");
        }
        department.setName(txtName.getText());

        if (exception.getErrors().size() > 0){
            throw exception;
        }
        return department;
    }
    public void subscribeDataChangeListner(DataChangeListener listener){
        dataChangeListeners.add(listener);
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
            notifyDataChangeListeners();
            Utils.currentStage(event).close();

        } catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }
        catch (DbException e){
            Alerts.showAlert("Error saving object",null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChangeListeners() {
        //executa o onDataChanged em cada um dos listener para notificar os listner
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
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
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(String.valueOf(entity.getName()));
    }
    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        if (fields.contains("name")){
            labeError.setText(errors.get("name"));
        }
    }
}
