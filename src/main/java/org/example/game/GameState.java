package org.example.game;

import org.example.entity.Creature;
import org.example.world.Level;
import org.example.world.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Game layer: GameState
 * Contains the current state of the game.
 * Game layer - no Swing dependencies.
 */
public class GameState {
    private Level level;
    private Creature pacman;
    private List<Creature> ghosts;
    private Set<Tile> foods;
    private int score;
    private int lives;
    private boolean gameOver;
    private boolean isPaused;

    public GameState(Level level, Creature pacman, List<Creature> ghosts) {
        this.level = level;
        this.pacman = pacman;
        this.ghosts = new ArrayList<>(ghosts);
        this.foods = new HashSet<>(level.getFoods());
        this.score = 0;
        this.lives = 3;
        this.gameOver = false;
        this.isPaused = false;
    }

    public Level getLevel() {
        return level;
    }

    public Creature getPacman() {
        return pacman;
    }

    public List<Creature> getGhosts() {
        return ghosts;
    }

    public Set<Tile> getFoods() {
        return foods;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void loseLife() {
        this.lives--;
        if (this.lives <= 0) {
            this.gameOver = true;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public void togglePause() {
        this.isPaused = !this.isPaused;
    }

    public void removeFood(Tile food) {
        foods.remove(food);
        level.removeFood(food);
    }

    public boolean hasFood() {
        return !foods.isEmpty();
    }

    public void resetGame(Level newLevel, Creature newPacman, List<Creature> newGhosts) {
        this.level = newLevel;
        this.pacman = newPacman;
        this.ghosts = new ArrayList<>(newGhosts);
        this.foods = new HashSet<>(level.getFoods());
        this.score = 0;
        this.lives = 3;
        this.gameOver = false;
        this.isPaused = false;
    }
}
