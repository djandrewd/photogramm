package ua.danit.photogramm.imgs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class ImageResize {
  public static void main(String[] args) throws IOException {
    String directory =
        "C:/Development/eclipse_workspace/dan.it/java-module/photogramm/photo-service/photos/";
    BufferedImage image = ImageIO.read(new File(directory + "IMG_1185.JPG"));
    image = Scalr.resize(image,640, 480);
    image = Scalr.rotate(image, Scalr.Rotation.CW_90);
    ImageIO.write(image, "JPG", new FileOutputStream(new File(directory + "_test_IMG_1185.JPG")));
  }
}
