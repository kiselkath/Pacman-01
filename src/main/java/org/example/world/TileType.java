package org.example.world;

/**
 * Domain model: TileType enum
 * Represents different types of tiles in the game world.
 * Domain layer - no Swing dependencies.
 */
public enum TileType {
    WALL,      // Wall tile (X)
    EMPTY,     // Empty space with food
    FOOD,      // Food dot
    SKIP       // Skip tile (O) - teleport tunnel
}
