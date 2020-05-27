package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class BattleMap {
    private TextureRegion grassTexture;
    private TextureRegion spiceTexture;
    // редкость    нахождения    spice  на  карте, в %
    private final int spiceRare = 3;
    private int[][] spices;

    public BattleMap() {
        this.grassTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.spiceTexture = Assets.getInstance().getAtlas().findRegion("spice");
        this.spices = setapSpices(1280, 720, spiceRare);
    }

    // текстура    спайса (ресурс  в дюне ,  spice)  размером  16х16
    // наложим  на  окно  сетку   с  шагом    размера  16
    // в каждую  такую   ячейку   рандомно  положим    spice



    public void render(SpriteBatch batch) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(grassTexture, i * 80, j * 80);
            }
        }
        for (int i = 0; i < spices.length; i++) {
            for (int j = 0; j < spices[i].length; j++) {
                if (spices[i][j] == 1) {
                    batch.draw(spiceTexture, i * 16, j * 16);
                }
            }
        }

    }

    // метод   генерации     ресурсов,    всего  ресурсов   3%,
    public int[][] setapSpices(int withScreen, int heighScreen, int spiceRare) {
//        int[][] spices = new int[withScreen / 16][heighScreen / 16];
//        for (int i = 0; i < withScreen / 16; i++) {
//            for (int j = 0; j < heighScreen / 16; j++) {
//                spices[i][j] = (((MathUtils.random(0, 100)) > spiceRare) ? 0 : 1);
//            }
//        }
        int[][] spices = new int[80][45];
        for (int i = 0; i < 75; i++) {
            for (int j = 0; j < 45; j++) {
                spices[i][j] = (((MathUtils.random(0, 100)) > spiceRare) ? 0 : 1);
            }
        }
        return spices;
    }

    public int getSpicesValue(int width, int height) {
        return spices[width][height];
    }

    public void setSpicesValue(int width, int height, int spices) {
        this.spices[width][height] = spices;
    }


}
