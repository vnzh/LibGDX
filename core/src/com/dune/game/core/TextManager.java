package com.dune.game.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextManager {
    private String score;
    private float width;
    private float height;
    private BitmapFont font;
    private GlyphLayout layout;

    public TextManager(int score) {
        this.score = new String("Score = " + score);
        this.width = 30f;
        this.height = 20f;
        this.font = new BitmapFont();
        font.setColor(Color.CYAN);
        font.getData().setScale(3);
        this.layout = new GlyphLayout();
    }

//    public   void initialize(float width,float height){
//
//    }
    public void update (int sc) {
    score = "Score = " + sc;
    }


    public void displayMessage(SpriteBatch batch) {
        layout.setText(font, score);
        font.draw(batch, layout, 20f, 690f);
    }


}