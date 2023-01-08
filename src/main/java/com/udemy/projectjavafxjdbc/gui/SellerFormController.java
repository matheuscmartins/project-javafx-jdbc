package com.udemy.projectjavafxjdbc.gui;

import com.udemy.projectjavafxjdbc.Db.DbException;
import com.udemy.projectjavafxjdbc.gui.listeners.DataChangeListener;
import com.udemy.projectjavafxjdbc.gui.util.Alerts;
import com.udemy.projectjavafxjdbc.gui.util.Constraints;
import com.udemy.projectjavafxjdbc.gui.util.Utils;
import com.udemy.projectjavafxjdbc.model.entites.Seller;
import com.udemy.projectjavafxjdbc.model.exceptions.ValidationException;
import com.udemy.projectjavafxjdbc.model.services.SellerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;
    private SellerService service;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextField txtBaseSalary;
    @FXML
    private Label labeErrorName;
    @FXML
    private Label labeErrorEmail;
    @FXML
    private Label labeErrorBirthDate;
    @FXML
    private Label labeErrorBaseSalary;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    public void setSeller(Seller entity) {
        this.entity = entity;
    }
    public void setSellerService(SellerService service) {
        this.service = service;
    }
    private Seller getFormData() {
        Seller seller = new Seller();
        ValidationException exception = new ValidationException("Validation error");

        seller.setId(Utils.tryParseToInt(txtId.getText()));
        if (txtName.getText() == null || txtName.getText().trim().equals("") || txtName.getText() == "null"){
            exception.addError("name", "Field can't be empty");
        }
        seller.setName(txtName.getText());

        if (exception.getErrors().size() > 0){
            throw exception;
        }
        return seller;
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
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail,  60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(String.valueOf(entity.getName()));
        txtEmail.setText(String.valueOf(entity.getEmail()));
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
    }
    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        if (fields.contains("name")){
            labeErrorName.setText(errors.get("name"));
        }
    }
}
