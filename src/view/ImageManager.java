package view;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import controller.logic;

public class ImageManager {
    public static ImageIcon iBlockIcon;
    public static ImageIcon jBlockIcon;
    public static ImageIcon lBlockIcon;
    public static ImageIcon oBlockIcon;
    public static ImageIcon sBlockIcon;
    public static ImageIcon tBlockIcon;
    public static ImageIcon zBlockIcon;
    public static ImageIcon getIBlockIcon() {return iBlockIcon;}
    public static ImageIcon getJBlockIcon() {return jBlockIcon;}
    public static ImageIcon getLBlockIcon() {return iBlockIcon;}
    public static ImageIcon getOBlockIcon() {return jBlockIcon;}
    public static ImageIcon getSBlockIcon() {return iBlockIcon;}
    public static ImageIcon getTBlockIcon() {return jBlockIcon;}
    public static ImageIcon getZBlockIcon() {return iBlockIcon;}

    public static void initImageIcons() {
        try {
            iBlockIcon = new ImageIcon(ImageIO.read(new File("design/IBlock.png")).getScaledInstance(100, 25, Image.SCALE_SMOOTH));
            jBlockIcon = new ImageIcon(ImageIO.read(new File("design/JBlock.png")).getScaledInstance(75, 50, Image.SCALE_SMOOTH));
            lBlockIcon = new ImageIcon(ImageIO.read(new File("design/LBlock.png")).getScaledInstance(75, 50, Image.SCALE_SMOOTH));
            oBlockIcon = new ImageIcon(ImageIO.read(new File("design/OBlock.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            sBlockIcon = new ImageIcon(ImageIO.read(new File("design/SBlock.png")).getScaledInstance(75, 50, Image.SCALE_SMOOTH));
            tBlockIcon = new ImageIcon(ImageIO.read(new File("design/TBlock.png")).getScaledInstance(75, 50, Image.SCALE_SMOOTH));
            zBlockIcon = new ImageIcon(ImageIO.read(new File("design/ZBlock.png")).getScaledInstance(75, 50, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ImageIcon getBlockIcon(int blockType) {
        return switch (blockType) {
            case 0 -> iBlockIcon;
            case 1 -> jBlockIcon;
            case 2 -> lBlockIcon;
            case 3 -> oBlockIcon;
            case 4 -> sBlockIcon;
            case 5 -> tBlockIcon;
            case 6 -> zBlockIcon;
            default -> null;
        };
    }
    public static void updateHoldImage(JLabel holdImgLabel, logic game) {
        if (game.getCurrentHoldBlock() != -1) {
            ImageIcon icon = switch (game.getCurrentHoldBlock()) {
                case 0 -> iBlockIcon;
                case 1 -> jBlockIcon;
                case 2 -> lBlockIcon;
                case 3 -> oBlockIcon;
                case 4 -> sBlockIcon;
                case 5 -> tBlockIcon;
                case 6 -> zBlockIcon;
                default -> null;
            };
            holdImgLabel.setIcon(icon);
        } else {
            holdImgLabel.setIcon(null);
        }
    }
    public static void updateCurrentBlock(JPanel[][] gameGrid, logic game, boolean isNewBlock) {
        if (!isNewBlock) {
            for (int i = 0; i < 4; i++) {
                gameGrid[game.getPreviousBlockPos()[i][0]][game.getPreviousBlockPos()[i][1]].setBackground(Color.WHITE);
                gameGrid[game.getPreviousBlockPos()[i][0]][game.getPreviousBlockPos()[i][1]].setBorder(null);
                gameGrid[game.getPreviousBlockPos()[i][0]][game.getPreviousBlockPos()[i][1]].setOpaque(false);
            }
        }
        for (int i = 0; i < 4; i++) {
            gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setOpaque(true);
            gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setBackground(game.getCurrentBlockColor());
            gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setBorder(new LineBorder(Color.BLACK));
        }
        
    }
}