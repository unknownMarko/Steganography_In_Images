package com.steganography.steganography;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;

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
    private Text text_max_chars;

    @FXML
    private Text text_resolution;

    @FXML
    private Text text_size;

    @FXML
    private TextArea textarea_input_text;

    @FXML
    private TextArea textarea_path;

    @FXML
    private TextArea textarea_text_from_image;

    SteganographyLogic logic = new SteganographyLogic();
    String[][] imageColorsBin;
    String[][] imageTextBin;
    String[] textBin;
    String filePath;
    File loadImageFile;
    int maxChars;

    int inputImageWidth;
    int inputImageHeight;

    @FXML
    void initialize() {
        textarea_path.setWrapText(true);
        textarea_input_text.setWrapText(true);
        textarea_text_from_image.setWrapText(true);

        //At Start turned off buttons
        btn_get_text_from_image.setDisable(true);
        btn_hide_text_to_image.setDisable(true);
        btn_preview_image_with_input.setDisable(true);
        btn_preview_loaded_image.setDisable(true);
    }

    @FXML
    void handleGetTextFromImage(ActionEvent event) {
        textarea_text_from_image.setText("");
        char[] output = logic.imageToText(imageColorsBin, maxChars);
        for (int i = 0; i < output.length; i++) {
            if (output[i] > 31 && output[i] < 127) {
                textarea_text_from_image.setText(textarea_text_from_image.getText()+output[i]);
            }
        }
    }

    @FXML
    void handle_input_textarea() {
        if (textarea_input_text.getText().length() > maxChars) {
            textarea_input_text.setText(textarea_input_text.getText().substring(0, maxChars));
        }
        if (!textarea_input_text.getText().isEmpty() && filePath != null) {
            btn_preview_image_with_input.setDisable(false);
            btn_hide_text_to_image.setDisable(false);
        } else {
            btn_preview_image_with_input.setDisable(true);
            btn_hide_text_to_image.setDisable(true);
        }
    }

    @FXML
    void handleHideTextIntoImage(ActionEvent event) {
        textBin = logic.inputToBin(textarea_input_text.getText());
        imageTextBin = logic.writeTextIntoImageBin(imageColorsBin, textBin, Integer.parseInt(text_max_chars.getText()));
        if (filePath.charAt(filePath.length()-1) == 'G') {
            logic.saveImage(imageTextBin, inputImageWidth, inputImageHeight, "png", loadImageFile.getName(), false);
        } else {
            logic.saveImage(imageTextBin, inputImageWidth, inputImageHeight, "bmp", loadImageFile.getName(), false);
        }
        textarea_text_from_image.setText("");
    }

    @FXML
    void handleLoadImage(ActionEvent event) throws IOException {
        textarea_input_text.setText("");
        handle_input_textarea();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG/BMP", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().addAll(extensionFilter);

        fileChooser.setTitle("Select Image");

        loadImageFile = fileChooser.showOpenDialog(stage);

        if (loadImageFile != null) {
            //If File loaded successfully

            //Get Data about file and show them in GUI
            filePath = loadImageFile.getAbsolutePath();
            textarea_path.setText(filePath);

            text_size.setText((loadImageFile.length() / 1024)+1 + " KB");

            BufferedImage bufferedImage = ImageIO.read(loadImageFile);

            inputImageWidth = bufferedImage.getWidth();
            inputImageHeight = bufferedImage.getHeight();
            maxChars = (inputImageWidth*inputImageHeight*3)/8;
            text_max_chars.setText(String.valueOf(maxChars));

            text_resolution.setText(inputImageWidth + "x" + inputImageHeight);

            //Get Colors from Image in Bin.
            imageColorsBin = logic.getColorsFromImageInBin(bufferedImage);

            //Handle GUI buttons
            btn_preview_loaded_image.setDisable(false);
            btn_get_text_from_image.setDisable(false);
            handle_input_textarea();
        } else {
            //If File somehow wasn't loaded. Should not ever be the case (But just in case :D)
            System.err.println("Error while loading file..");
            btn_preview_loaded_image.setDisable(true);
            btn_get_text_from_image.setDisable(true);
        }
    }

    @FXML
    void handleShowImageOriginal(ActionEvent event) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(loadImageFile);
            } catch (IOException e) {
                System.err.println("Cannot open OS Photo Viewer..");
            }
        } else {
            System.err.println("Desktop is not supported to open Photo Image Viewer..");
        }
    }

    @FXML
    void handleShowImageWithText(ActionEvent event) {
        logic.saveImage(imageTextBin, inputImageWidth, inputImageHeight, "png", loadImageFile.getName(), true);
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(loadImageFile);
            } catch (IOException e) {
                System.err.println("Cannot open OS Photo Viewer..");
            }
        } else {
            System.err.println("Desktop is not supported to open Photo Image Viewer..");
        }
    }
}
