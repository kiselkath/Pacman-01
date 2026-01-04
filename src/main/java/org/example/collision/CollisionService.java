package org.example.collision;

import org.example.entity.Creature;
import org.example.world.Tile;

/**
 * Domain model: CollisionService
 * Handles collision detection between game entities.
 * Domain layer - no Swing dependencies.
 */
public class CollisionService {

    /**
     * Check collision between two rectangles
     */
    public boolean checkCollision(int x1, int y1, int w1, int h1,
                                   int x2, int y2, int w2, int h2) {
        return x1 < x2 + w2 &&
               x1 + w1 > x2 &&
               y1 < y2 + h2 &&
               y1 + h1 > y2;
    }

    /**
     * Check collision between a creature and a tile
     */
    public boolean checkCollision(Creature creature, Tile tile, int creatureWidth, int creatureHeight) {
        return checkCollision(
            creature.getX(), creature.getY(), creatureWidth, creatureHeight,
            tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight()
        );
    }

    /**
     * Check collision between two creatures
     */
    public boolean checkCollision(Creature a, Creature b, int width, int height) {
        return checkCollision(
            a.getX(), a.getY(), width, height,
            b.getX(), b.getY(), width, height
        );
    }
}
