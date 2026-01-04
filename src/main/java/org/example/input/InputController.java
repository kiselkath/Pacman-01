package org.example.input;

import org.example.game.GameEngine;
import org.example.util.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Input layer: InputController
 * Handles keyboard input and translates it to game commands.
 * Input layer - processes KeyEvent, translates to GameEngine commands.
 */
public class InputController implements KeyListener {
    private final GameEngine gameEngine;
    private final Runnable onPauseToggle;
    private final Runnable onRestart;

    public InputController(GameEngine gameEngine, Runnable onPauseToggle, Runnable onRestart) {
        this.gameEngine = gameEngine;
        this.onPauseToggle = onPauseToggle;
        this.onRestart = onRestart;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameEngine.getGameState().isGameOver()) {
            if (onRestart != null) {
                onRestart.run();
            }
            return;
        }

        int keyCode = e.getKeyCode();
        
        if (keyCode == KeyEvent.VK_UP) {
            gameEngine.getGameState().getPacman().updateDirection(Direction.UP);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            gameEngine.getGameState().getPacman().updateDirection(Direction.DOWN);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            gameEngine.getGameState().getPacman().updateDirection(Direction.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            gameEngine.getGameState().getPacman().updateDirection(Direction.RIGHT);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            if (onPauseToggle != null) {
                onPauseToggle.run();
            }
        }
    }
}
