package org.example.java_bank_app.UtilsPackage;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


import java.text.DecimalFormat;

public class HoveredThresholdNode extends StackPane {

    int seriesColor;

    public HoveredThresholdNode(double value, int seriesColor, Color labelTextColor) {

        DecimalFormat decimalFormat = new DecimalFormat("#. ##");
        String formattedDouble = decimalFormat.format(value);
        initialize(formattedDouble, seriesColor, labelTextColor);

    }

    public HoveredThresholdNode(String value, int seriesColor, Color labelTextColor) {

        initialize(value, seriesColor, labelTextColor);

    }

    private Label createDataThresholdLabel(String text) {

        final Label label = new Label(text);
        String default_color = "default-color" + (this.seriesColor);
        label.getStyleClass().addAll(default_color, "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }

    private void initialize(String value, int seriesColor, Color color){
        this.seriesColor = seriesColor;
        setPrefSize(10, 10);

        final Label label = createDataThresholdLabel(value);
        label.setTextFill(color);

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


}
