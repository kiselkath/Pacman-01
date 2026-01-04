package org.example.app;

import org.example.collision.CollisionService;
import org.example.entity.Creature;
import org.example.entity.CreatureType;
import org.example.game.GameEngine;
import org.example.game.GameState;
import org.example.input.InputController;
import org.example.ui.PacMan;
import org.example.util.Direction;
import org.example.world.Level;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Application layer: PacmanApplication
 * Creates and wires all components together.
 * Main entry point for the application.
 */
public class App {
    private static final int ROW_COUNT = 21;
    private static final int COLUMN_COUNT = 19;
    private static final int TILE_SIZE = 32;

    private static final String[] TILE_MAP = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       b o       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X    pP     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX"
    };

    public static void main(String[] args) {
        // Create domain services
        CollisionService collisionService = new CollisionService();
        
        // Create level
        Level level = new Level(ROW_COUNT, COLUMN_COUNT, TILE_SIZE, TILE_MAP);
        
        // Create creatures
        List<Creature> creatures = createCreatures(level, TILE_SIZE);
        Creature pacman = creatures.get(0);
        List<Creature> ghosts = creatures.subList(1, creatures.size());
        
        // Initialize ghost directions
        Random random = new Random();
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        for (Creature ghost : ghosts) {
            Direction newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        
        // Create game state
        GameState gameState = new GameState(level, pacman, ghosts);
        
        // Create game engine
        GameEngine gameEngine = new GameEngine(gameState, collisionService, TILE_SIZE);
        
        // Create UI
        int boardWidth = COLUMN_COUNT * TILE_SIZE;
        int boardHeight = ROW_COUNT * TILE_SIZE;
        
        JFrame frame = new JFrame("Pac Man");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create view
        PacMan view = new PacMan(gameState, gameEngine, boardWidth, boardHeight, TILE_SIZE);
        frame.add(view);
        frame.pack();
        view.requestFocus();
        
        // Create input controller
        InputController inputController = new InputController(
            gameEngine,
            view::togglePause,
            () -> restartGame(gameEngine, view, level, TILE_SIZE, random, directions)
        );
        view.addKeyListener(inputController);
        
        // Start game loop
        view.startGameLoop();
    }
    
    private static List<Creature> createCreatures(Level level, int tileSize) {
        List<Creature> creatures = new ArrayList<>();
        
        for (int r = 0; r < level.getRowCount(); r++) {
            for (int c = 0; c < level.getColumnCount(); c++) {
                char tileMapChar = TILE_MAP[r].charAt(c);
                int x = c * tileSize;
                int y = r * tileSize;
                
                if (tileMapChar == 'b') {
                    creatures.add(new Creature(CreatureType.BLUE_GHOST, x, y, tileSize));
                } else if (tileMapChar == 'o') {
                    creatures.add(new Creature(CreatureType.ORANGE_GHOST, x, y, tileSize));
                } else if (tileMapChar == 'p') {
                    creatures.add(new Creature(CreatureType.PINK_GHOST, x, y, tileSize));
                } else if (tileMapChar == 'r') {
                    creatures.add(new Creature(CreatureType.RED_GHOST, x, y, tileSize));
                } else if (tileMapChar == 'P') {
                    creatures.add(0, new Creature(CreatureType.PACMAN, x, y, tileSize));
                }
            }
        }
        
        return creatures;
    }
    
    private static void restartGame(GameEngine gameEngine, PacMan view, Level level, int tileSize, 
                                     Random random, Direction[] directions) {
        Level newLevel = new Level(ROW_COUNT, COLUMN_COUNT, TILE_SIZE, TILE_MAP);
        List<Creature> newCreatures = createCreatures(newLevel, tileSize);
        Creature newPacman = newCreatures.get(0);
        List<Creature> newGhosts = newCreatures.subList(1, newCreatures.size());
        
        for (Creature ghost : newGhosts) {
            Direction newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        
        gameEngine.getGameState().resetGame(newLevel, newPacman, newGhosts);
        view.startGameLoop();
    }
}

