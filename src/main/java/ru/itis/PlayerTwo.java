package ru.itis;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class PlayerTwo extends Pane {
    Image imageUp = new Image("assets/tankRed_up.png");
    Image imageDown = new Image("assets/tankRed_down.png");
    Image imageLeft = new Image("assets/tankRed_left.png");
    Image imageRight = new Image("assets/tankRed_right.png");
    public ImageView imageView;
    private String position = "W";


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PlayerTwo() {
        imageView = new ImageView();
        imageView.setImage(imageUp);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        setTranslateX(400);
        setTranslateY(300);
        imageView.setViewport(new Rectangle2D(0, 0, 100, 100));
        getChildren().addAll(imageView);


    }


    public void imageX(int x){
        boolean down;
        if (x > 0){
            down = true;
        }else {
            down = false;
        }
        for (int i = 0; i < Math.abs(x); i++) {

            if (down) {
                imageView.setImage(imageRight);
                setPosition("D");
            } else {
                imageView.setImage(imageLeft);
                setPosition("A");
            }
        }
    }

    public void imageY(int y){
        boolean down;
        if (y > 0){
            down = true;
        }else {
            down = false;
        }
        for (int i = 0; i < Math.abs(y); i++) {

            if (down) {
                imageView.setImage(imageDown);
                setPosition("S");
            } else {
                imageView.setImage(imageUp);
                setPosition("W");
            }
        }
    }

}
