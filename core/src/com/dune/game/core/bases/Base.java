package com.dune.game.core.bases;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.BattleMap;
import com.dune.game.core.units.Owner;

public abstract  class Base {
    protected BattleMap battleMap;
    protected Owner owner;
    protected Vector2 position;  ///  центр   базы
    protected TextureRegion textureRegion;
    private final int HALF_BASE_SIZE = 80;

    public Base(BattleMap battleMap) {
        this.battleMap = battleMap;
    }

    public Owner getOwner() {
        return owner;
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public int getHalfBaseSize() {
        return HALF_BASE_SIZE;
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, position.x - HALF_BASE_SIZE,
                position.y - HALF_BASE_SIZE);
    }
    public void basesSetup() {}

}
