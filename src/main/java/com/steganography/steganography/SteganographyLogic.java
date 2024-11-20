package com.steganography.steganography;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SteganographyLogic {
    //Input string, returns array of chars in bits
    public String[] inputToBin(String inputText) {

        String[] inputBin = new String[inputText.length()];

        for (int i = 0; i < inputText.length(); i++) {
            inputBin[i] = String.format("%8s", Integer.toBinaryString(inputText.charAt(i))).replace(' ', '0');
        }

        for (int i = 0; i < inputText.length(); i++) {
            System.out.println(inputBin[i]);
        }

        return inputBin;
    }

    //Input image in array of colors. Get LSBits and save them in char array
    //Note: Char in bit 00000100 means end of hidden message
    public char[] imageToText(String[][] imageColorsBin) {
        String[] output = new String[imageColorsBin.length*3];
        for (int i = 0; i < imageColorsBin.length; i++) {
            output[i*3] = String.valueOf(imageColorsBin[i][0].charAt(7));
            output[i*3+1] = String.valueOf(imageColorsBin[i][1].charAt(7));
            output[i*3+2] = String.valueOf(imageColorsBin[i][2].charAt(7));
        }


        StringBuilder textInFile = new StringBuilder();
        for (int index = 0; index < output.length; index++) {
            if (index > 0 && index % 8 == 0) {
                textInFile.append(' ');
            }
            textInFile.append(output[index]);
        }

        String[] textInFileArray = textInFile.toString().split(" ");
        char[] textInFileArrayChar = new char[textInFileArray.length];

        for (int i = 0; i < textInFileArray.length; i++) {
            if (textInFileArray[i].length() == 8) {
                if (!textInFileArray[i].equals("00000100")) {
                    textInFileArrayChar[i] = (char) Integer.parseInt(textInFileArray[i], 2);
                } else {
                    break;
                }
            }
        }
        return textInFileArrayChar;
    }

    //Input image and return char of colors in image
    public String[][] getColorsFromImageInBin(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();

        String[][] imageColorsBin = new String[width * height][3];

        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int rgb = image.getRGB(x, y);

                imageColorsBin[index][0] = String.format("%8s", Integer.toBinaryString(((rgb >> 16) & 0xFF))).replace(' ', '0');
                imageColorsBin[index][1] = String.format("%8s", Integer.toBinaryString(((rgb >> 8) & 0xFF))).replace(' ', '0');
                imageColorsBin[index][2] = String.format("%8s", Integer.toBinaryString((rgb & 0xFF))).replace(' ', '0');

                index++;
            }
        }

        return imageColorsBin;
    }

    //Returns image with hidden message
    public String[][] writeTextIntoImageBin(String[][] imageColorsBin, String[] inputText, int maxChar) {
        String[] inputTextArray;

        if (inputText.length < maxChar) {
            inputTextArray = new String[inputText.length + 1];
            System.arraycopy(inputText, 0, inputTextArray, 0, inputText.length);
            inputTextArray[inputTextArray.length - 1] = "00000100";
        } else {
            inputTextArray = new String[inputText.length];
            System.arraycopy(inputText, 0, inputTextArray, 0, inputText.length);
        }
        int textArrayIndex = 0;
        int bitPosition = 0;

        for (int pixelPosition = 0; pixelPosition < imageColorsBin.length && textArrayIndex < inputTextArray.length; pixelPosition++) {
            for (int colorIndex = 0; colorIndex < 3 && textArrayIndex < inputTextArray.length; colorIndex++) {
                String currentColor = imageColorsBin[pixelPosition][colorIndex];

                char textBit = inputTextArray[textArrayIndex].charAt(bitPosition);

                String newColor = currentColor.substring(0, 7) + textBit;

                imageColorsBin[pixelPosition][colorIndex] = newColor;

                bitPosition++;

                if (bitPosition == 8) {
                    textArrayIndex++;
                    bitPosition = 0;
                }
            }
        }

        return imageColorsBin;
    }

    private Stage stage;

    //Format imageColorsBin into file, save and return File
    public File saveImage(String[][] imageColorsBin, int width, int height, String format, String fileName, boolean preview){

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = (
                        Integer.parseInt(imageColorsBin[index][0], 2) << 16) |
                        Integer.parseInt(imageColorsBin[index][1], 2) << 8 |
                        Integer.parseInt(imageColorsBin[index][2], 2);
                image.setRGB(x, y, rgb);
                index++;
            }
        }

        File file = null;

        if (!preview) {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter(".png", "*.png");
            FileChooser.ExtensionFilter bmpFilter = new FileChooser.ExtensionFilter(".bmp", "*.bmp");
            fileChooser.getExtensionFilters().addAll(pngFilter, bmpFilter);

            fileChooser.setTitle("Save Image");
            fileChooser.setInitialFileName(fileName);
            file = fileChooser.showSaveDialog(stage);

            try {
                ImageIO.write(image, format, file);
                return file;
            } catch (IOException e) {
                System.err.println("Error while saving file..");
            }
        } else {
            try {
                file = new File(System.getProperty("user.dir"), "temp_output.png");
                file.deleteOnExit();
                ImageIO.write(image, "png", file);
                return file;

            } catch (IOException e) {
                System.err.println("Error while saving file!");
            }
        }
        return file;
    }
}
