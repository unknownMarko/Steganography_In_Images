# Steganography in Images

![](https://github.com/unknownMarko/Steganography/blob/master/screenshots/screenshot.png)

Steganography is the art and science of concealing secret information within non-secret data, such as images, audio, or text files. Unlike cryptography, which scrambles a message to make it unreadable, steganography hides the existence of the message itself. This project focuses on embedding and extracting hidden data within digital images, leveraging techniques like least significant bit (LSB) manipulation to preserve visual quality while securely storing information.

### Linux Project Setup
    sudo apt update
    sudo apt install openjdk-23-jdk
    sudo apt install maven
    mvn clean install

### Windows Project Setup
    Install OpenJDK (ver. 23) (https://www.oracle.com/java/technologies/downloads/)
    Install Maven (https://maven.apache.org/download.cgi)
    In project folder terminal: mvn clean install

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.