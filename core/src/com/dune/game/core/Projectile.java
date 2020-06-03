package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends GameObject implements Poolable {
    private TextureRegion texture;
    private Vector2 velocity;
    private float speed;
    private float angle;
    private boolean active;
    private final int projectileOdinaryPower = 25;

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public Projectile(GameController gc) {
        super(gc);
        this.velocity = new Vector2();
        this.speed = 640.0f;
    }

    public void setup(Vector2 startPosition, float angle, TextureRegion texture) {
        this.texture = texture;
        this.angle = angle;
        this.position.set(startPosition.x + MathUtils.cosDeg(angle) * 50, startPosition.y + MathUtils.sinDeg(angle) *50);
        this.velocity.set(speed * MathUtils.cosDeg(angle), speed * MathUtils.sinDeg(angle));
        this.active = true;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 8, position.y - 8);
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < 0 || position.x > 1280 || position.y < 0 || position.y > 720) {
            deactivate();
        }


    }

    public int getProjectileOdinaryPower() {
        return projectileOdinaryPower;
    }
}
