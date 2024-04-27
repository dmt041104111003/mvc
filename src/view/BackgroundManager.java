package view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class BackgroundManager {
    private static final String[] backgroundPaths = {
        "design/background-1.jpg", 
        "design/background-2.jpg", 
        "design/background-3.png", 
        "design/background-4.jpg", 
    };

    public static ImageIcon getBackgroundImageForLevel(int level) {
        if (level >= 0 && level < backgroundPaths.length) {
            try {
                Image backgroundImage = ImageIO.read(new File(backgroundPaths[level]));
                return new ImageIcon(backgroundImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Image defaultBackground = ImageIO.read(new File("design/background-1.jpg"));
            return new ImageIcon(defaultBackground);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
} 