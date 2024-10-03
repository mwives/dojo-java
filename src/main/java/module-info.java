module com.dojo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dojo.controller to javafx.fxml;
    opens com.dojo.controller.aluno to javafx.fxml;

    exports com.dojo;
}
