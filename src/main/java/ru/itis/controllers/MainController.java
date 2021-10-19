package ru.itis.controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import ru.itis.*;
import ru.itis.server.Client;

import java.io.IOException;
import java.util.*;


public class MainController {
    Stage stage = new Stage();
    static Scene gameScene;
    public static Pane pane = new Pane();
    public static Player player = new Player();
    public static ArrayList<Brick> bricks = new ArrayList<>();
    public static final List<Bullet> bullets = new ArrayList<>();
    public static final List<BulletTwo> bulletsTwoPlayer = new ArrayList<BulletTwo>();
    HashMap<KeyCode, Boolean> keys = new HashMap<>();
    Client client = new Client();
    public static PlayerTwo playerTwo = new PlayerTwo();

    ProgressBar pb1 = new ProgressBar(1);
    ProgressBar pb2 = new ProgressBar(1);

    public static boolean player2Check = false;

    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void update() {
        if (isPressed(KeyCode.W)) {
            player.imageY(-2);
            player.moveY(-2);
            client.sendMessage("WposY" + player.getTranslateY());
        } else if (isPressed(KeyCode.S)) {
            player.imageY(2);
            player.moveY(2);
            client.sendMessage("SposY" + player.getTranslateY());
        } else if (isPressed(KeyCode.A)) {
            player.imageX(-2);
            player.moveX(-2);
            client.sendMessage("AposX" + player.getTranslateX());
        } else if (isPressed(KeyCode.D)) {
            player.imageX(2);
            player.moveX(2);
            client.sendMessage("DposX" + player.getTranslateX());
        }
    }

    public void updateFire() {
        if (isPressed(KeyCode.SPACE)) {
            Platform.runLater(() -> {
                Bullet bullet = new Bullet();
                bullets.add(bullet);
                bullet.setImageBullet(player.getPosition());
                bullet.setTranslateX(player.getTranslateX() + 34);
                bullet.setTranslateY(player.getTranslateY() + 34);
                pane.getChildren().add(bullet);
                client.sendMessage("fire " + player.getPosition());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Bullet bullet : bullets) {
            bullet.fire();
        }
    }

    public static void TwoPlayerFire(BulletTwo bulletTwo) {
        bulletsTwoPlayer.add(bulletTwo);
        bulletTwo.setImageBullet(playerTwo.getPosition());
        bulletTwo.setTranslateX(playerTwo.getTranslateX() + 34);
        bulletTwo.setTranslateY(playerTwo.getTranslateY() + 34);
        pane.getChildren().add(bulletTwo);
        bulletTwo.fire();
    }

    public void bulletsRedUpdate(){
        for (BulletTwo bulletTwo : bulletsTwoPlayer) {
            bulletTwo.fire();
        }
    }


    public void collisionBricksAndBullets() {
        for (Bullet bull : bullets) {
            for (Brick bri : bricks) {
                if (bull.getBoundsInParent().intersects(bri.getBoundsInParent())) {
                    pane.getChildren().remove(bull);
                    bullets.remove(bull);

                }
            }
        }
    }

    public void collisionPlayerAndRedBullets(){
        for (BulletTwo bulletTwo : bulletsTwoPlayer){
            if (bulletTwo.getBoundsInParent().intersects(player.getBoundsInParent())){
                pane.getChildren().remove(bulletTwo);
                bulletsTwoPlayer.remove(bulletTwo);
                pb1.setProgress(pb1.getProgress() - 0.2);

            }
        }
    }

    public void collisionPlayerTwoAndBullets(){
        for (Bullet bullet : bullets){
            if (bullet.getBoundsInParent().intersects(playerTwo.getBoundsInParent())){
                pane.getChildren().remove(bullet);
                bulletsTwoPlayer.remove(bullet);
                pb2.setProgress(pb2.getProgress() - 0.005);
            }
        }
    }

    public void collisionBricksAndRedBullets(){
        for (BulletTwo bulletTwo : bulletsTwoPlayer){
            for(Brick brick : bricks){
                if (bulletTwo.getBoundsInParent().intersects(brick.getBoundsInParent())){
                    pane.getChildren().remove(bulletTwo);
                    bulletsTwoPlayer.remove(bulletTwo);
                }
            }
        }
    }


    @FXML
    public Button exitBtn;
    @FXML
    public Button playBtn;
    @FXML
    public Button settingBtn;


    @FXML
    public void buttonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clickMouseOnButtonPlay() {
        playBtn.setOnMouseClicked(this::handle);
    }

    @FXML
    public void clickMouseOnButtonSetting() {
        settingBtn.setOnMouseClicked(mouseEvent -> {
            settingBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/settingView.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            Scene settingScene = new Scene(root);
            stage.initStyle(StageStyle.TRANSPARENT);
            settingScene.setFill(Color.TRANSPARENT);
            stage.setScene(settingScene);
            stage.showAndWait();
            ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).show();
        });
    }

    private void handle(MouseEvent mouseEvent) {
        settingBtn.getScene().getWindow().hide();
        int yBlock = 0;
        int xBlock = 0;
        for (int i = 0; i < 11; i++) {
            Brick brick = new Brick(0, yBlock);
            Brick brick1 = new Brick(550, yBlock);
            bricks.add(brick);
            bricks.add(brick1);
            yBlock += 50;
            pane.getChildren().add(brick);
            pane.getChildren().add(brick1);
        }
        for (int i = 0; i < 12; i++) {
            Brick brick = new Brick(xBlock, yBlock);
            Brick brick1 = new Brick(xBlock, 0);
            bricks.add(brick);
            bricks.add(brick1);
            xBlock += 50;
            pane.getChildren().add(brick);
            pane.getChildren().add(brick1);
        }
        int dopYbrick = 200;
        int dopXbrick = 250;
        for (int i = 0; i < 3; i++) {
            Brick brick = new Brick(dopXbrick, dopYbrick);
            bricks.add(brick);
            dopYbrick += 50;
            pane.getChildren().add(brick);
        }


        pane.setBackground(new Background(new BackgroundImage(new Image("assets/tileSand.png"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        pane.setPrefSize(600, 600);
        gameScene = new Scene(pane);


        //add progressbar health player 1
        pane.getChildren().add(pb1);

        //add progressbar health player 2
        pb2.setLayoutX(500);
        pane.getChildren().add(pb2);

        MainController.gameScene.setOnKeyPressed(keyEvent -> {
            keys.put(keyEvent.getCode(), true);
        });
        MainController.gameScene.setOnKeyReleased(keyEvent -> {
            keys.put(keyEvent.getCode(), false);
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                collisionBricksAndBullets();
                collisionBricksAndRedBullets();
                collisionPlayerAndRedBullets();
                collisionPlayerTwoAndBullets();
                gameResult(mouseEvent);
            }
        };


        AnimationTimer timerBullet = new AnimationTimer() {

            int frameCount = 0;

            @Override
            public void handle(long currentNanoTime) {

                if (frameCount % 2 == 0) {
                    updateFire();
                    Platform.runLater(() -> {
                        bulletsRedUpdate();
                    });
                }
                frameCount++;

                if (player2Check){
                    player.setTranslateX(400);
                    player.setTranslateY(300);
                    playerTwo.setTranslateX(100);
                    playerTwo.setTranslateY(150);
                    player2Check = false;
                }
            }
        };

        //add Player
        pane.getChildren().add(player);

        //add Player Two
        pane.getChildren().add(playerTwo);

        timerBullet.start();

        timer.start();

        stage.setScene(gameScene);
        stage.showAndWait();
        ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).show();
    }

    public void gameResult(MouseEvent mouseEvent){
        if (pb1.getProgress() <= 0 || pb2.getProgress() <= 0) {
            Pane paneResult = new Pane();
            paneResult.setStyle("-fx-background-color: linear-gradient(to right top, #051937, #004d7a, #008793, #00bf72, #a8eb12);");

            if (pb1.getProgress() <= 0){
                Text text = new Text("You lose");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
                text.setFill(Color.WHITE);
                text.setX(30);
                text.setY(100);
                paneResult.getChildren().add(text);
            } else if (pb2.getProgress() <= 0){
                Text text = new Text("You win");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
                text.setFill(Color.WHITE);
                text.setX(30);
                text.setY(100);
                paneResult.getChildren().add(text);
            }
            Scene scene = new Scene(paneResult,300, 200);
            stage.setScene(scene);
        }
    }
}
