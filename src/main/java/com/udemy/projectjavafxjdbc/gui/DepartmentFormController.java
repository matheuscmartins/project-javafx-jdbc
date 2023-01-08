package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.gui.util.Constraints;
import com.udemy.projectjavafxjdbc.model.entites.Department;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private Department entity;
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

    @FXML
    public void onBtnSaveAction(){
        System.out.println("onBtnSaveAction");
    }
    @FXML
    public void onBtnCancelAction(){
        System.out.println("onBtnCancelAction");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtname,30);
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtname.setText(String.valueOf(entity.getName()));
    }
}
