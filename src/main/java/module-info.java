module com.steganography.steganography {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.steganography.steganography to javafx.fxml;
    exports com.steganography.steganography;
}