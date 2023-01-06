module com.udemy.projectjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.udemy.projectjavafxjdbc to javafx.fxml;
    exports com.udemy.projectjavafxjdbc;
}