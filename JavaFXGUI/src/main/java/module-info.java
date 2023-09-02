module org.koffa.javafxgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires com.dlsc.formsfx;
    requires kafka.clients;

    opens org.koffa.javafxgui to javafx.fxml;
    opens org.koffa.javafxgui.dto to lombok, com.google.gson;
    opens org.koffa.javafxgui.textformatter to javafx.fxml;

    exports org.koffa.javafxgui;
    exports org.koffa.javafxgui.textformatter;
    exports org.koffa.javafxgui.dto;
}