package org.example;
import javax.swing.*;
import java.awt.*;

public class PacMan extends JPanel {
    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 32;
    private int boardWidth = columnCount * tileSize;
    private int boardHeight = rowCount * tileSize;

    //Images
    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;

    private Image pacmanUpPicture;
    private Image pacmanDownPicture;
    private Image pacmanLeftPicture;
    private Image pacmanRightPicture;

    //Constructor
    PacMan(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

        //loadImages
        wallImage = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();

        pacmanUpPicture = new ImageIcon(getClass().getResource("/pacmanUp.png")).getImage();
        pacmanDownPicture = new ImageIcon(getClass().getResource("/pacmanDown.png")).getImage();
        pacmanLeftPicture = new ImageIcon(getClass().getResource("/pacmanLeft.png")).getImage();
        pacmanRightPicture = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();

    }
}
