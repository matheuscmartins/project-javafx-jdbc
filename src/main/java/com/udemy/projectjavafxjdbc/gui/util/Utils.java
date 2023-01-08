package com.udemy.projectjavafxjdbc.gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

    public static Stage currentStage(ActionEvent event){
        return (Stage) ((Node) event.getSource()).getScene().getWindow(); //faz um downcast para NODE depois downcast para Stage
    }
}
