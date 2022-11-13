module de.kozdemir.agenda {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;


    opens de.kozdemir.javaFXAgenda to javafx.fxml;
    opens de.kozdemir.javaFXAgenda.controller to javafx.fxml;
    opens de.kozdemir.javaFXAgenda.model to javafx.fxml, javafx.base;
    exports de.kozdemir.javaFXAgenda;
}