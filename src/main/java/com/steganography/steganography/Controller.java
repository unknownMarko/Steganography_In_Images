package com.steganography.steganography;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class Controller {

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

}
