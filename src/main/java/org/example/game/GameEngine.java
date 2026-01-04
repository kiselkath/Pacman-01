package org.example.game;

import org.example.collision.CollisionService;
import org.example.entity.Creature;
import org.example.entity.CreatureType;
import org.example.util.Direction;
import org.example.world.Level;
import org.example.world.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game layer: GameEngine
 * Orchestrates game logic and updates game state.
 * Game layer - no Swing dependencies.
 */
public class GameEngine {
    private final GameState gameState;
    private final CollisionService collisionService;
    private final Random random;
    private final Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
    private final int tileSize;

    public GameEngine(GameState gameState, CollisionService collisionService, int tileSize) {
        this.gameState = gameState;
        this.collisionService = collisionService;
        this.random = new Random();
        this.tileSize = tileSize;
    }

    /**
     * Update game state - move all entities and check collisions
     */
    public void update() {
        if (gameState.isPaused() || gameState.isGameOver()) {
            return;
        }

        movePacman();
        moveGhosts();
        checkGhostCollisions();
        checkFoodCollisions();
        checkLevelComplete();
    }

    private void movePacman() {
        Creature pacman = gameState.getPacman();
        pacman.move();

        // Check wall collisions for pacman
        for (Tile wall : gameState.getLevel().getWalls()) {
            if (collisionService.checkCollision(pacman, wall, tileSize, tileSize)) {
                pacman.moveBack();
                break;
            }
        }
    }

    private void moveGhosts() {
        int boardWidth = gameState.getLevel().getBoardWidth();

        for (Creature ghost : gameState.getGhosts()) {
            // Special logic for ghost at row 9 (middle tunnel area)
            if (ghost.getY() == tileSize * 9 && 
                ghost.getDirection() != Direction.UP && 
                ghost.getDirection() != Direction.DOWN) {
                ghost.updateDirection(Direction.UP);
            }

            ghost.move();

            // Check wall collisions and boundaries for ghost
            for (Tile wall : gameState.getLevel().getWalls()) {
                if (collisionService.checkCollision(ghost, wall, tileSize, tileSize) ||
                    ghost.getX() <= 0 || ghost.getX() + tileSize >= boardWidth) {
                    ghost.moveBack();
                    Direction newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                    break;
                }
            }
        }
    }

    private void checkFoodCollisions() {
        Creature pacman = gameState.getPacman();
        Tile foodToRemove = null;

        for (Tile food : gameState.getFoods()) {
            // Food is smaller (4x4 at offset 14,14)
            int foodX = food.getX() + 14;
            int foodY = food.getY() + 14;
            int foodSize = 4;

            if (collisionService.checkCollision(
                    pacman.getX(), pacman.getY(), tileSize, tileSize,
                    foodX, foodY, foodSize, foodSize)) {
                foodToRemove = food;
                gameState.addScore(10);
            }
        }

        if (foodToRemove != null) {
            gameState.removeFood(foodToRemove);
        }
    }

    private void checkLevelComplete() {
        if (!gameState.hasFood()) {
            // Level complete - reload level
            Level newLevel = createLevel();
            List<Creature> newCreatures = createCreatures(newLevel);
            Creature newPacman = newCreatures.get(0);
            List<Creature> newGhosts = newCreatures.subList(1, newCreatures.size());
            
            // Initialize ghost directions
            for (Creature ghost : newGhosts) {
                Direction newDirection = directions[random.nextInt(4)];
                ghost.updateDirection(newDirection);
            }
            
            gameState.resetGame(newLevel, newPacman, newGhosts);
        }
    }

    /**
     * Check ghost collisions with pacman
     */
    private void checkGhostCollisions() {
        Creature pacman = gameState.getPacman();
        
        for (Creature ghost : gameState.getGhosts()) {
            if (collisionService.checkCollision(pacman, ghost, tileSize, tileSize)) {
                gameState.loseLife();
                if (gameState.isGameOver()) {
                    return;
                }
                resetPositions(gameState.getGhosts());
                pacman.reset();
                pacman.updateDirection(Direction.UP);
                return;
            }
        }
    }

    /**
     * Reset all creature positions
     */
    private void resetPositions(List<Creature> ghosts) {
        gameState.getPacman().reset();
        gameState.getPacman().updateDirection(Direction.UP);

        for (Creature ghost : ghosts) {
            ghost.reset();
            Direction newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    /**
     * Create level from tile map
     * Note: Ideally this should be in a factory, but kept here for level completion logic
     */
    private Level createLevel() {
        String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
        };
        return new Level(21, 19, tileSize, tileMap);
    }

    /**
     * Create creatures from level
     * Note: Ideally this should be in a factory, but kept here for level completion logic
     */
    private List<Creature> createCreatures(Level level) {
        List<Creature> creatures = new ArrayList<>();
        String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
        };

        for (int r = 0; r < level.getRowCount(); r++) {
            for (int c = 0; c < level.getColumnCount(); c++) {
                char tileMapChar = tileMap[r].charAt(c);
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
                    creatures.add(0, new Creature(CreatureType.PACMAN, x, y, tileSize)); // Add at index 0
                }
            }
        }
        return creatures;
    }

    public GameState getGameState() {
        return gameState;
    }
}
