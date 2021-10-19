package ru.itis.server;

import javafx.application.Platform;
import ru.itis.BulletTwo;
import ru.itis.controllers.MainController;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements ConnectionListener {
    private Connection connection;
    private final String HOST = "localhost";
    private final int PORT = 4321;
    private final String regexPosX = "posX[0-9]{1,3}(?:[.,][0-9])?";
    private final String regexPosY = "posY[0-9]{1,3}(?:[.,][0-9])?";
    private final String regexOX = "oX[0-9]{1,3}(?:[.,][0-9])?";
    private final String regexOY = "oY[0-9]{1,3}(?:[.,][0-9])?";
    private final String regex = "[0-9]{1,3}(?:[.,][0-9])?";
    private final String regexForImagePlayer = "[W,A,D,S]";
    private final String regexPlayerOne = "Client 1";
    private final String regexPlayerTwo = "Client 2";
    private final String regexFire = "fire";

    boolean playerOneConnect = false;
    boolean playerTwoConnect = false;
    boolean playerReady = false;

    Pattern patternX;
    Pattern patternY;
    Pattern pattern;
    Pattern patternBullets;
    Pattern patternPlayerOne;
    Pattern patternPlayerTwo;
    Pattern patternForImage;
    Matcher matcher;
    Matcher matcherPlayer;
    Matcher matcherBullet;
    Matcher matcherImage;
    String x;
    String y;


    public Client() {
        try {
            connection = new Connection(this, HOST, PORT);
        } catch (IOException e) {
            printMsg("Connection Exception: " + e);
        }
    }

    public void sendMessage(String value) {
        connection.sendString(value);

    }

    @Override
    public void onConnectionReady(Connection connection) {
        printMsg("Connection ready...");
    }

    @Override
    public void onReceiveString(Connection connection, String value) {
        patternPlayerOne = Pattern.compile(regexPlayerOne);
        patternPlayerTwo = Pattern.compile(regexPlayerTwo);
        patternBullets = Pattern.compile(regexFire);
        patternY = Pattern.compile(regexPosY);
        patternForImage = Pattern.compile(regexForImagePlayer);
        pattern = Pattern.compile(regex);
        patternX = Pattern.compile(regexPosX);

        matcherPlayer = patternPlayerTwo.matcher(value);
        if (matcherPlayer.find()){
            System.out.println(matcherPlayer.group());
            MainController.player2Check = true;
        }


        Platform.runLater(() -> {
            move(value);
        });
        firePlayer(value);
    }


    @Override
    public void onDisconnect(Connection connection) {
        printMsg("Connection close");
    }

    @Override
    public void onException(Connection connection, Exception e) {
        printMsg("Connection Exception: " + e);
    }

    private synchronized void printMsg(String msg) {
        System.err.println(msg);
    }

    private void move(String value) {
        matcher = patternX.matcher(value);
        while (matcher.find()) {
            x = matcher.group();
            matcher = pattern.matcher(x);
            while (matcher.find()) {
                MainController.playerTwo.setTranslateX(Double.parseDouble(matcher.group()));
            }
        }

        matcher = patternY.matcher(value);
        while (matcher.find()) {
            y = matcher.group();
            matcher = pattern.matcher(y);
            while (matcher.find()) {
                MainController.playerTwo.setTranslateY(Double.parseDouble(matcher.group()));
            }
        }

        matcherImage = patternForImage.matcher(value);
        while (matcherImage.find()) {
            if ("W".equals(matcherImage.group())) {
                MainController.playerTwo.imageY(-2);
            } else if ("S".equals(matcherImage.group())) {
                MainController.playerTwo.imageY(2);
            } else if ("A".equals(matcherImage.group())) {
                MainController.playerTwo.imageX(-2);
            } else if ("D".equals(matcherImage.group())) {
                MainController.playerTwo.imageX(2);
            }
        }
    }

    private void firePlayer(String line) {
        matcherBullet = patternBullets.matcher(line);
        while (matcherBullet.find()) {
            BulletTwo bulletTwo = new BulletTwo();
            Platform.runLater(() -> {
                MainController.TwoPlayerFire(bulletTwo);
            });
        }
    }
}
