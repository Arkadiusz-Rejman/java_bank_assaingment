package org.example.java_bank_app.HoveredThreshholdNodePackage;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


import java.text.DecimalFormat;

public class HoveredThresholdNode extends StackPane {

    int seriesColor;

    public HoveredThresholdNode(double value, int seriesColor) {
        this.seriesColor = seriesColor;
        setPrefSize(10, 10);

        DecimalFormat decimalFormat = new DecimalFormat("#. ##");
        String formattedDouble = decimalFormat.format(value);
        final Label label = createDataThresholdLabel(formattedDouble);

        setOnMouseEntered(mouseEvent -> {
            getChildren().setAll(label);
            setCursor(Cursor.NONE);
            toFront();
        });
        setOnMouseExited(mouseEvent -> {
            getChildren().clear();
            setCursor(Cursor.DEFAULT);
        });


    }

    private Label createDataThresholdLabel(String text) {

        final Label label = new Label(text);
        String default_color = "default-color" + (this.seriesColor);
        label.getStyleClass().addAll(default_color, "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }


}
