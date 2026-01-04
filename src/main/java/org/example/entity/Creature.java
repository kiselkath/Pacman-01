package org.example.entity;

import org.example.util.Direction;
import org.example.util.Position;

/**
 * Domain model: Creature
 * Represents a creature (Pacman or Ghost) in the game world.
 * Contains position, direction, velocity, and movement logic.
 * Domain layer - no Swing dependencies.
 */
public class Creature {
    private final CreatureType type;
    private Position position;
    private Position startPosition;
    private Direction direction;
    private int velocityX;
    private int velocityY;
    private final int tileSize;

    public Creature(CreatureType type, int x, int y, int tileSize) {
        this.type = type;
        this.position = new Position(x, y);
        this.startPosition = new Position(x, y);
        this.tileSize = tileSize;
        this.direction = Direction.UP;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public CreatureType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    /**
     * Update direction and calculate velocity
     */
    public void updateDirection(Direction newDirection) {
        this.direction = newDirection;
        updateVelocity();
    }

    /**
     * Calculate velocity based on current direction
     */
    public void updateVelocity() {
        switch (direction) {
            case UP:
                this.velocityX = 0;
                this.velocityY = -tileSize / 4;
                break;
            case DOWN:
                this.velocityX = 0;
                this.velocityY = tileSize / 4;
                break;
            case RIGHT:
                this.velocityX = tileSize / 4;
                this.velocityY = 0;
                break;
            case LEFT:
                this.velocityX = -tileSize / 4;
                this.velocityY = 0;
                break;
        }
    }

    /**
     * Move creature by its velocity
     */
    public void move() {
        position.add(velocityX, velocityY);
    }

    /**
     * Move creature back (undo last movement)
     */
    public void moveBack() {
        position.add(-velocityX, -velocityY);
    }

    /**
     * Reset creature to starting position
     */
    public void reset() {
        this.position = new Position(startPosition.getX(), startPosition.getY());
        this.velocityX = 0;
        this.velocityY = 0;
    }

    /**
     * Set position directly (for collision handling)
     */
    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }
}
