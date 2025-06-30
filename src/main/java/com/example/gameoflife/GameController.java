package com.example.gameoflife;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GameController {
    @FXML private TextField seedField;
    @FXML private Text actionText;

    @FXML
    private void handleInput() {
        actionText.setText(seedField.getText());
    }
}
