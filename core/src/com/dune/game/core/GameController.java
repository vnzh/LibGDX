package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameController {
    private BattleMap map;
    private Tank tank;
//    private Projectile bullet;
    private TextureAtlas atlas;


    public Tank getTank() {
        return tank;
    }

//    public Projectile getBullet() {
//       return bullet;
//    }

    public BattleMap getMap() {
        return map;
    }

    // Инициализация игровой логики
    public GameController() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("game.pack"));
        this.map = new BattleMap(atlas);
        this.tank = new Tank(atlas,200, 200);;
            }

    public void update(float dt) {
        tank.update(dt);

//        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
//            bullet = new Projectile(tank.getPosition().x, tank.getPosition().y, tank.getAngle(), atlas);
//        }
//        if (bullet != null) { bullet.update(dt);}

        checkCollisions(dt);
    }

    public void checkCollisions(float dt) {

    }
}
