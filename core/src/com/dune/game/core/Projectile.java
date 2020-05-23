package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private Vector2 position;
    private Vector2 velocity;
    private TextureRegion textures;
//    private Texture textures;
    private float angle;
//    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("game.pack"));

    public void setup(Vector2 startPosition, float angle) {
        velocity.set(100.0f * MathUtils.cosDeg(angle), 100.0f * MathUtils.sinDeg(angle));

    }

    public Projectile (float x, float y, float angle, TextureAtlas atlas) {
//    public Projectile (float x, float y, float angle) {
        this.position = new Vector2(x, y);
        this.textures = atlas.findRegion("bullet");
//       this.textures =  new Texture("RedCircle.png");
        this.angle = angle;
        velocity = new Vector2();
        setup(position, angle);

    }

    public void update(float dt) {
         position.x += velocity.x * dt;
         position.y += velocity.y * dt;
        position.mulAdd(velocity, dt);


    }
    public void render(SpriteBatch batch) {
        batch.draw(textures, position.x, position.y);

    }

    public Vector2 getPosition() {
        return position;
    }

//    public Texture getTextures() {
//        return textures;
//    }
    public TextureRegion getTextures() {
        return textures;
    }
}
