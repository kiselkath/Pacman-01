package org.example.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Domain model: Level
 * Represents the game level with tiles, walls, and food.
 * Domain layer - no Swing dependencies.
 */
public class Level {
    private final int rowCount;
    private final int columnCount;
    private final int tileSize;
    private final List<List<Tile>> tiles;
    private final Set<Tile> walls;
    private final Set<Tile> foods;
    private final String[] tileMap;

    public Level(int rowCount, int columnCount, int tileSize, String[] tileMap) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tileSize = tileSize;
        this.tileMap = tileMap;
        this.tiles = new ArrayList<>();
        this.walls = new HashSet<>();
        this.foods = new HashSet<>();
        loadMap();
    }

    private void loadMap() {
        tiles.clear();
        walls.clear();
        foods.clear();

        for (int r = 0; r < rowCount; r++) {
            List<Tile> row = new ArrayList<>();
            String mapRow = tileMap[r];
            
            for (int c = 0; c < columnCount; c++) {
                char tileMapChar = mapRow.charAt(c);
                int x = c * tileSize;
                int y = r * tileSize;

                Tile tile;
                if (tileMapChar == 'X') {
                    tile = new Tile(TileType.WALL, x, y, tileSize, tileSize);
                    walls.add(tile);
                } else if (tileMapChar == ' ') {
                    // Empty space with food
                    tile = new Tile(TileType.FOOD, x, y, tileSize, tileSize);
                    foods.add(tile);
                } else if (tileMapChar == 'O') {
                    tile = new Tile(TileType.SKIP, x, y, tileSize, tileSize);
                } else {
                    // Characters for creatures are handled separately
                    tile = new Tile(TileType.EMPTY, x, y, tileSize, tileSize);
                }
                row.add(tile);
            }
            tiles.add(row);
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getBoardWidth() {
        return columnCount * tileSize;
    }

    public int getBoardHeight() {
        return rowCount * tileSize;
    }

    public Set<Tile> getWalls() {
        return walls;
    }

    public Set<Tile> getFoods() {
        return foods;
    }

    public Tile getTile(int row, int col) {
        if (row >= 0 && row < rowCount && col >= 0 && col < columnCount) {
            return tiles.get(row).get(col);
        }
        return null;
    }

    /**
     * Reload the level map
     */
    public void reload() {
        loadMap();
    }

    /**
     * Remove a food tile
     */
    public void removeFood(Tile food) {
        foods.remove(food);
    }

    public boolean hasFood() {
        return !foods.isEmpty();
    }
}
