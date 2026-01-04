package org.example.world;

import org.example.util.Position;

/**
 * Domain model: Tile
 * Represents a single tile in the game world.
 * Domain layer - no Swing dependencies.
 */
public class Tile {
    private final TileType type;
    private final Position position;
    private final int width;
    private final int height;

    public Tile(TileType type, int x, int y, int width, int height) {
        this.type = type;
        this.position = new Position(x, y);
        this.width = width;
        this.height = height;
    }

    public TileType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
