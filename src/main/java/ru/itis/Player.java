package ru.itis;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.itis.controllers.MainController;

public class Player extends Pane {
    Image imageUp = new Image("assets/tank_green_up.png");
    Image imageDown = new Image("assets/tank_green_down.png");
    Image imageLeft = new Image("assets/tank_green_left.png");
    Image imageRight = new Image("assets/tank_green_right.png");
    ImageView imageView;
    private String position = "W";



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Player() {
        imageView = new ImageView();
        imageView.setImage(imageUp);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        setTranslateX(100);
        setTranslateY(150);
        imageView.setViewport(new Rectangle2D(0, 0, 100, 100));
        getChildren().addAll(imageView);


    }

    public void moveX(int x) {
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            for (Brick brick : MainController.bricks) {
                if (this.getBoundsInParent().intersects(brick.getBoundsInParent())) {

                    if (right) {
                        if (this.getTranslateX() + 90 == brick.getTranslateX()) {
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == brick.getTranslateX() + 50) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }
                if (this.getBoundsInParent().intersects(MainController.playerTwo.getBoundsInParent())){
                    if (right){
                        if (this.getTranslateX() + 90 == MainController.playerTwo.getTranslateX()) {
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == MainController.playerTwo.getTranslateX() + 75) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (right ? 1 : -1));
        }
    }


    public void moveY(int y) {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            for (Brick brick : MainController.bricks) {
                if (this.getBoundsInParent().intersects(brick.getBoundsInParent())) {
                    if (down) {
                        if (this.getTranslateY() + 90 == brick.getTranslateY()) {
                            this.setTranslateY(this.getTranslateY() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateY() == brick.getTranslateY() + 50) {
                            this.setTranslateY(this.getTranslateY() + 1);
                            return;
                        }
                    }
                }
                if (this.getBoundsInParent().intersects(MainController.playerTwo.getBoundsInParent())){
                    if (down) {
                        if (this.getTranslateY() + 90 == MainController.playerTwo.getTranslateY()) {
                            this.setTranslateY(this.getTranslateY() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateY() == MainController.playerTwo.getTranslateY() + 90) {
                            this.setTranslateY(this.getTranslateY() + 1);
                            return;
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (down ? 1 : -1));
        }
    }

    public void imageX(int x) {
        boolean down = x > 0 ? true : false;
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

    public void imageY(int y) {
        boolean down = y > 0 ? true : false;
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
