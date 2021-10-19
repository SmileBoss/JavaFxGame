package ru.itis;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Brick extends Pane {
    Image blocksImg = new Image("assets/crateMetal.png");
    ImageView block;
    public Brick(int x, int y) {
        block = new ImageView(blocksImg);
        setTranslateX(x);
        setTranslateY(y);
        block.setFitHeight(50);
        block.setFitWidth(50);
        block.setViewport(new Rectangle2D(0,0,55,55));
        getChildren().add(block);
    }


}
