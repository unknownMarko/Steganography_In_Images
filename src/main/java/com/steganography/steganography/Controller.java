package com.steganography.steganography;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    //Getting stage from Steganography.java
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Button btn_get_text_from_image;

    @FXML
    private Button btn_hide_text_to_image;

    @FXML
    private Button btn_load_image;

    @FXML
    private Button btn_preview_image_with_input;

    @FXML
    private Button btn_preview_loaded_image;

    @FXML
    private Text text_format;

    @FXML
    private Text text_max_chars;

    @FXML
    private Text text_resolution;

    @FXML
    private Text text_size;

    @FXML
    private TextArea textarea_input_text;

    @FXML
    private TextArea textarea_text_from_image;

    @FXML
    void handleGetTextFromImage(ActionEvent event) {

    }

    @FXML
    void handleHideTextIntoImage(ActionEvent event) {

    }

    @FXML
    void handleLoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(stage);
    }

    @FXML
    void handleShowImageOriginal(ActionEvent event) {

    }

    @FXML
    void handleShowImageWithText(ActionEvent event) {

    }


}
