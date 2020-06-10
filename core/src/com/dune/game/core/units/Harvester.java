package com.dune.game.core.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.Assets;
import com.dune.game.core.GameController;
import com.dune.game.core.Targetable;
import com.dune.game.core.Weapon;

public class Harvester extends AbstractUnit {
    public Harvester(GameController gc) {
        super(gc);
        this.textures = Assets.getInstance().getAtlas().findRegion("tankcore").split(64, 64)[0];
        this.weaponTexture = Assets.getInstance().getAtlas().findRegion("harvester");
        this.containerCapacity = 10;
        this.minDstToActiveTarget = 5.0f;
        this.speed = 120.0f;
        this.weapon = new Weapon(4.0f, 1);
        this.hpMax = 500;
        this.unitType = UnitType.HARVESTER;
    }

    @Override
    public void setup(Owner ownerType, float x, float y) {
        this.position.set(x, y);
        this.ownerType = ownerType;
        this.hp = this.hpMax;
        this.destination = new Vector2(position);
    }

    public void updateWeapon(float dt) {

//
//        int x = (int)position.x/gc.getMap().getCellSize();
//        int y = (int) position.y/gc.getMap().getCellSize();
        if (onBase()) {
//            System.out.println("Harvester on  base ");
            if (container > 0) {
                int result = weapon.use(dt);
                if (result > -1) {
                    container -= 1;
                    gc.getPlayerLogic().increaseMoney(100);
                    if (container < 0) {
                        container = 0;
                    }
                }
            }
            return;
        }


        if (gc.getMap().getResourceCount(position) > 0) {
            if (container < containerCapacity) {
                int result = weapon.use(dt);
                if (result > -1) {
                    container += gc.getMap().harvestResource(position, result);
                    if (container > containerCapacity) {
                        container = containerCapacity;
                    }
                }
            }
        } else {
            weapon.reset();
        }

    }

    @Override
    public void commandAttack(Targetable target) {
        commandMoveTo(target.getPosition());
    }

    @Override
    public void renderGui(SpriteBatch batch) {
        super.renderGui(batch);
        if (weapon.getUsageTimePercentage() > 0.0f) {
            batch.setColor(0.2f, 0.2f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 32, position.y + 22, 64, 8);
            batch.setColor(1.0f, 1.0f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 30, position.y + 24, 60 * weapon.getUsageTimePercentage(), 4);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        batch.setColor(0.2f, 0.2f, 0.0f, 1.0f);
        batch.draw(progressbarTexture, position.x - 32, position.y - 40, 64, 8);
        batch.setColor(1.0f, 1.0f, 0.0f, 1.0f);
        batch.draw(progressbarTexture, position.x - 30, position.y - 40,
                60 * container / containerCapacity, 4);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public boolean onBase() {
        switch (ownerType) {
            case AI:
                if (position.dst(gc.getMap().getAiBase().getPosition()) < 80) {
                    return true;
                }
                break;
            case PLAYER:
                if (position.dst(gc.getMap().getPlayerBase().getPosition()) < 80) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }
}
