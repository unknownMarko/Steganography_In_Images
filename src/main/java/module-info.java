module com.steganography.steganography {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.steganography.steganography to javafx.fxml;
    exports com.steganography.steganography;
}