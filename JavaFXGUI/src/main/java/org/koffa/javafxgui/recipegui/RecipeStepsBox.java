package org.koffa.javafxgui.recipegui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RecipeStepsBox extends VBox {
    private final SplitPane steps = new SplitPane();

    public RecipeStepsBox() {
        steps.setOrientation(javafx.geometry.Orientation.VERTICAL);
        Button addStepButton = new Button("Lägg till steg");
        Button removeStep = new Button("Ta bort steg");
        SplitPane stepButtons = new SplitPane();
        stepButtons.getItems().addAll(addStepButton, removeStep);
        steps.getItems().addAll(new TextField("Skriv steg här"));
        this.getChildren().addAll(stepButtons, steps);

        addStepButton.setOnAction(event -> steps.getItems().add(new TextField("Skriv steg här")));
        removeStep.setOnAction(event -> {
            if (steps.getItems().size() > 1) {
                steps.getItems().remove(steps.getItems().size() - 1);
            }
        });
    }
    public ObservableList<Node> getSteps() {
        return steps.getItems();
    }
}
