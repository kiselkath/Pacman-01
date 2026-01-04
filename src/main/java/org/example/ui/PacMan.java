package org.example.ui;

import org.example.entity.Creature;
import org.example.entity.CreatureType;
import org.example.game.GameEngine;
import org.example.game.GameState;
import org.example.util.Direction;
import org.example.world.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UI layer: PacMan (View)
 * Pure view component - renders game state, no game logic.
 * UI layer - contains JPanel, Graphics, images.
 */
public class PacMan extends JPanel implements ActionListener {
    private final GameState gameState;
    private final GameEngine gameEngine;
    private final int boardWidth;
    private final int boardHeight;
    private final int tileSize;

    // Images - UI concern
    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    private Timer gameLoop;

    public PacMan(GameState gameState, GameEngine gameEngine, int boardWidth, int boardHeight, int tileSize) {
        this.gameState = gameState;
        this.gameEngine = gameEngine;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);

        loadImages();
    }

    private void loadImages() {
        wallImage = new ImageIcon(getClass().getResource("/Pictures/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/Pictures/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/Pictures/orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("/Pictures/pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/Pictures/redGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("/Pictures/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("/Pictures/pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/Pictures/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("/Pictures/pacmanRight.png")).getImage();
    }

    /**
     * Start the game loop timer
     */
    public void startGameLoop() {
        if (gameLoop == null) {
            gameLoop = new Timer(50, this); // 20fps (1000/50) 50ms
        }
        gameLoop.start();
    }

    /**
     * Stop the game loop timer
     */
    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * Toggle game pause
     */
    public void togglePause() {
        gameState.togglePause();
        if (gameState.isPaused()) {
            gameLoop.stop();
        } else {
            gameLoop.start();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Render the game state
     */
    private void draw(Graphics g) {
        // Draw walls
        for (Tile wall : gameState.getLevel().getWalls()) {
            g.drawImage(wallImage, wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null);
        }

        // Draw food
        g.setColor(Color.WHITE);
        for (Tile food : gameState.getFoods()) {
            // Food is smaller (4x4 at offset 14,14)
            g.fillRect(food.getX() + 14, food.getY() + 14, 4, 4);
        }

        // Draw pacman with direction-based image
        Creature pacman = gameState.getPacman();
        Image pacmanImage = getPacmanImage(pacman.getDirection());
        g.drawImage(pacmanImage, pacman.getX(), pacman.getY(), tileSize, tileSize, null);

        // Draw ghosts
        for (Creature ghost : gameState.getGhosts()) {
            Image ghostImage = getGhostImage(ghost.getType());
            g.drawImage(ghostImage, ghost.getX(), ghost.getY(), tileSize, tileSize, null);
        }

        // Draw UI text
        drawUI(g);
    }

    private Image getPacmanImage(Direction direction) {
        if (direction == Direction.UP) {
            return pacmanUpImage;
        } else if (direction == Direction.DOWN) {
            return pacmanDownImage;
        } else if (direction == Direction.LEFT) {
            return pacmanLeftImage;
        } else {
            return pacmanRightImage;
        }
    }

    private Image getGhostImage(CreatureType type) {
        if (type == CreatureType.BLUE_GHOST) {
            return blueGhostImage;
        } else if (type == CreatureType.ORANGE_GHOST) {
            return orangeGhostImage;
        } else if (type == CreatureType.PINK_GHOST) {
            return pinkGhostImage;
        } else if (type == CreatureType.RED_GHOST) {
            return redGhostImage;
        } else {
            return blueGhostImage; // fallback
        }
    }

    private void drawUI(Graphics g) {
        String pausedText = "    GAME IS PAUSED";
        String gameOverText = "GAME OVER: ";
        String restartGameText = "Press any key to RESTART ";
        String scoreText = " SCORE: ";

        // Center position
        int midx = boardWidth / 2 - (3 * tileSize);
        int midy = boardHeight / 2 - tileSize;

        // Left corner
        int leftX = tileSize / 2;
        int leftY = tileSize / 2;

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if (gameState.isGameOver()) {
            g.drawString(gameOverText + String.valueOf(gameState.getScore()), leftX, leftY);
            g.drawString(restartGameText, midx, midy);
        } else {
            g.drawString("x" + String.valueOf(gameState.getLives()) + scoreText + String.valueOf(gameState.getScore()), leftX, leftY);
        }

        // Pause banner
        if (gameState.isPaused()) {
            g.setFont(new Font("Arial", Font.BOLD, 64));
            g.drawString(pausedText, midx, midy);
        }
    }

    // ActionListener - called by Timer
    @Override
    public void actionPerformed(ActionEvent e) {
        gameEngine.update();
        repaint();
        if (gameState.isGameOver()) {
            gameLoop.stop();
        }
    }
}
