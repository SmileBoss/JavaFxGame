package ru.itis.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;



public class SettingController {

    @FXML
    private Button closeSettingBtn;

    public void clickMouseOnSettingButton(){
        closeSettingBtn.setOnMouseClicked(mouseEvent -> {
            closeSettingBtn.getScene().getWindow().hide();
        });

    }
}
