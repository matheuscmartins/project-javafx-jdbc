package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

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
}
