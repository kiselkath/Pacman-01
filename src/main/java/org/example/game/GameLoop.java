package org.example.game;

/**
 * Game layer: GameLoop
 * Manages the game loop timing.
 * Game layer - no Swing dependencies.
 * Note: Actual timer is managed by UI layer (JPanel), this is just an interface.
 */
public interface GameLoop {
    void start();
    void stop();
    boolean isRunning();
}
