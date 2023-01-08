module com.udemy.projectjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.udemy.projectjavafxjdbc to javafx.fxml;
    exports com.udemy.projectjavafxjdbc.application;
    opens com.udemy.projectjavafxjdbc.application to javafx.fxml;
    opens com.udemy.projectjavafxjdbc.gui to javafx.fxml;
    exports com.udemy.projectjavafxjdbc.gui to javafx.fxml;
    opens com.udemy.projectjavafxjdbc.model.entites to javafx.base;


}