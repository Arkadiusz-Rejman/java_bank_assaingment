package org.example.java_bank_app.AnimationsPackage;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class CustomAnimations {

    public static void glowOnMouseEnter(Color color, Node... nodes){

        for(Node node : nodes){
            DropShadow glow = new DropShadow();
            glow.setColor(color);
            glow.setWidth(20);
            glow.setHeight(20);


            node.setOnMouseEntered(event -> {
                node.setEffect(glow);
            });

            node.setOnMouseExited(event -> {
                node.setEffect(null);
            });
        }

    }

    public static void scaleOnMousePress(Node... nodes){
        for(Node node : nodes){
            node.setOnMousePressed(event -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.1), node);
                scaleTransition.setToX(1.1);
                scaleTransition.setToY(1.1);
                scaleTransition.setOnFinished(e -> {
                    // Po zakończeniu animacji, przywracamy oryginalną skalę
                    ScaleTransition resetTransition = new ScaleTransition(Duration.seconds(0.1), node);
                    resetTransition.setToX(1.0);
                    resetTransition.setToY(1.0);
                    resetTransition.play();
                });
                scaleTransition.play();
            });
        }
    }
}
