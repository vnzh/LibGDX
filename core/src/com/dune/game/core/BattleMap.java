package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.bases.AiBase;
import com.dune.game.core.bases.Base;
import com.dune.game.core.bases.PlayerBase;
import com.dune.game.core.units.Owner;

import java.util.ArrayList;

public class BattleMap {
    private class Cell {
        private int cellX, cellY;
        private int resource;
        private float resourceRegenerationRate;
        private float resourceRegenerationTime;
        private boolean base;


        public Cell(int cellX, int cellY) {
            this.cellX = cellX;
            this.cellY = cellY;
            if (MathUtils.random() < 0.1f) {
                resource = MathUtils.random(1, 3);
            }
            resourceRegenerationRate = MathUtils.random(5.0f) - 4.5f;
            if (resourceRegenerationRate < 0.0f) {
                resourceRegenerationRate = 0.0f;
            } else {
                resourceRegenerationRate *= 20.0f;
                resourceRegenerationRate += 10.0f;
            }
        }
//        public boolean isBase() {
//            return base;
//        }


        private void update(float dt) {
            if (resourceRegenerationRate > 0.01f) {
                resourceRegenerationTime += dt;
                if (resourceRegenerationTime > resourceRegenerationRate) {
                    resourceRegenerationTime = 0.0f;
                    resource++;
                    if (resource > 5) {
                        resource = 5;
                    }
                }
            }
        }

        private void render(SpriteBatch batch) {
            if (resource > 0) {
                float scale = 0.5f + resource * 0.2f;
                batch.draw(resourceTexture, cellX * 80, cellY * 80, 40, 40, 80, 80, scale, scale, 0.0f);
            } else {
                if (resourceRegenerationRate > 0.01f) {
                    batch.draw(resourceTexture, cellX * 80, cellY * 80, 40, 40, 80, 80, 0.1f, 0.1f, 0.0f);
                }
            }
        }
    }

    public static final int COLUMNS_COUNT = 20;
    public static final int ROWS_COUNT = 12;
    public static final int CELL_SIZE = 80;
    public static final int MAP_WIDTH_PX = COLUMNS_COUNT * CELL_SIZE;
    public static final int MAP_HEIGHT_PX = ROWS_COUNT * CELL_SIZE;
    private ArrayList<Base> bases;
    private  AiBase aiBase;
    private  PlayerBase playerBase;

    private TextureRegion grassTexture;
    private TextureRegion resourceTexture;
    private Cell[][] cells;

    public AiBase getAiBase() {
        return aiBase;
    }

    public PlayerBase getPlayerBase() {
        return playerBase;
    }

    public BattleMap() {
        this.grassTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.resourceTexture = Assets.getInstance().getAtlas().findRegion("resource");
        this.cells = new Cell[COLUMNS_COUNT][ROWS_COUNT];
        for (int i = 0; i < COLUMNS_COUNT; i++) {
            for (int j = 0; j < ROWS_COUNT; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
//        bases = new ArrayList<>();
//        bases.add(new AiBase(this));
//        bases.add(new PlayerBase(this));
         this.aiBase = new AiBase(this);
         this.playerBase = new PlayerBase(this);
    }

//    public Owner getOwnerBase (Vector2 position) {
//
//        for (int i = 0; i < bases.size()-1; i++) {
//            if (bases.get(i).getPosition().dst(position) < bases.get(i).getHalfBaseSize()) {
//                return bases.get(i).getOwner();
//            }
//        }
//        return  null;
//    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public boolean isBase(int x, int y) {
        return cells[x][y].base;
    }

    public void setCell(int x, int y, int resourse, boolean base) {
        this.cells[x][y].resource = 0;
        this.cells[x][y].base = true;
    }

    public void setCellBase(int x, int y) {
        this.cells[x][y].resource = 0;
        this.cells[x][y].base = true;
    }

    public  int getCellSize() {
        return CELL_SIZE;
    }

    public int getResourceCount(Vector2 point) {
        int cx = (int) (point.x / CELL_SIZE);
        int cy = (int) (point.y / CELL_SIZE);
        return cells[cx][cy].resource;
    }


    public int harvestResource(Vector2 point, int power) {
        int value = 0;
        int cx = (int) (point.x / CELL_SIZE);
        int cy = (int) (point.y / CELL_SIZE);
        if (cells[cx][cy].resource >= power) {
            value = power;
            cells[cx][cy].resource -= power;
        } else {
            value = cells[cx][cy].resource;
            cells[cx][cy].resource = 0;
        }
        return value;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < COLUMNS_COUNT; i++) {
            for (int j = 0; j < ROWS_COUNT; j++) {
                batch.draw(grassTexture, i * 80, j * 80);
                cells[i][j].render(batch);
            }
        }
        playerBase.render(batch);
        aiBase.render(batch);
//        for (Base base: bases ) {
//            batch.draw(base.getTextureRegion(), base.getPosition().x, base.getPosition().y);
//        }
    }

    public void update(float dt) {
        for (int i = 0; i < COLUMNS_COUNT; i++) {
            for (int j = 0; j < ROWS_COUNT; j++) {
                cells[i][j].update(dt);
            }
        }
    }


}
