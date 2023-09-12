module org.koffa.gui {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.koffa.gui to javafx.fxml;
    opens org.koffa.gui.helpers to lombok;
    exports org.koffa.gui;
}