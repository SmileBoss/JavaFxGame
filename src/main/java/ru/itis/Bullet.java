package ru.itis;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Bullet extends Pane {
    Image imageUp = new Image("assets/bulletGreen_up.png");
    Image imageDown = new Image("assets/bulletGreen_down.png");
    Image imageLeft = new Image("assets/bulletGreen_left.png");
    Image imageRight = new Image("assets/bulletGreen_right.png");
    ImageView imageView;
    String position;

    public Bullet() {
        imageView = new ImageView();
        getChildren().add(imageView);
    }

    public void setImageBullet(String key) {
        if ("W".equals(key)) {
            imageView.setImage(imageUp);
            position = "W";
        } else if ("S".equals(key)) {
            imageView.setImage(imageDown);
            position = "S";
        } else if ("A".equals(key)) {
            imageView.setImage(imageLeft);
            position = "A";
        } else if ("D".equals(key)) {
            imageView.setImage(imageRight);
            position = "D";
        }
    }

    public void fire() {
        if ("W".equals(position)) {
            setTranslateY(this.getTranslateY() - 7);
        } else if ("S".equals(position)) {
            setTranslateY(this.getTranslateY() + 7);
        } else if ("A".equals(position)) {
            setTranslateX(this.getTranslateX() - 7);
        } else if ("D".equals(position)) {
            setTranslateX(this.getTranslateX() + 7);
        }
    }
}
