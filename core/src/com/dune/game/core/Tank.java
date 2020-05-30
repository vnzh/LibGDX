package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tank extends GameObject implements Poolable {
    public enum Owner {
        PLAYER, AI
    }

    private Owner ownerType;
    private Weapon weapon;
    private Vector2 destination;
    private Vector2 currPosition;
    private TextureRegion[] textures;
    private TextureRegion progressbarTexture;
    private int hp;
    private float angle;
    private float speed;
    private float rotationSpeed;

    private float moveTimer;
    private float timePerFrame;
    private int container;
    private final int containerMax = 50;

    @Override
    public boolean isActive() {
        return hp > 0;
    }

    public Tank(GameController gc) {
        super(gc);
        this.progressbarTexture = Assets.getInstance().getAtlas().findRegion("progressbar");
        this.timePerFrame = 0.08f;
        this.rotationSpeed = 90.0f;
    }

    public void setup(Owner ownerType, float x, float y) {
        this.textures = Assets.getInstance().getAtlas().findRegion("tankanim").split(64, 64)[0];
        this.position.set(x, y);
//        this.currPosition.set(x, y);//  Почему  на такую  иничиализацию   выдает       ошибку  на нудевой  указатель?
//        this.currPosition.set(position.x, position.y);
        this.currPosition = new Vector2(position);
        this.ownerType = ownerType;
        this.speed = 120.0f;
        this.hp = 100;
        this.weapon = new Weapon(Weapon.Type.HARVEST, 3.0f, 1);
        this.destination = new Vector2(position);
    }

    private int getCurrentFrameIndex() {
        return (int) (moveTimer / timePerFrame) % textures.length;
    }

    public void update(float dt) {



//Вы бор  активного     танка.   По  умолчанию  все  танки  род  управлением AI.
//        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
//            if ( (position.x -40) / gc.getMap().CELL_SIZE == (int) Gdx.input.getX() / gc.getMap().CELL_SIZE &&
//                    (int) (position.y -40) / gc.getMap().CELL_SIZE == (int) (720 - Gdx.input.getY()) / gc.getMap().CELL_SIZE) {
//                ownerType = (ownerType == Owner.PLAYER ? Owner.AI : Owner.PLAYER);
//            }
//        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if ((position.x - 40) <= Gdx.input.getX() && Gdx.input.getX() <= (position.x + 40) &&
                    (position.y - 40) <= (720 - Gdx.input.getY()) && (720 - Gdx.input.getY()) <= (position.y + 40)) {
                ownerType = (ownerType == Owner.PLAYER ? Owner.AI : Owner.PLAYER);
            }
        }
//        currPosition.set(this.position.x, this.position.y);
        currPosition.x = position.x;
        currPosition.y = position.y;

        switch (ownerType) {
            case PLAYER:
                if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                    destination.set(Gdx.input.getX(), 720 - Gdx.input.getY());
                }

                if (position.dst(destination) > 3.0f) {
                    float angleTo = tmp.set(destination).sub(position).angle();
                    if (Math.abs(angle - angleTo) > 3.0f) {
                        if (angle > angleTo) {
                            if (Math.abs(angle - angleTo) <= 180.0f) {
                                angle -= rotationSpeed * dt;
                            } else {
                                angle += rotationSpeed * dt;
                            }
                        } else {
                            if (Math.abs(angle - angleTo) <= 180.0f) {
                                angle += rotationSpeed * dt;
                            } else {
                                angle -= rotationSpeed * dt;
                            }
                        }
                    }
                    if (angle < 0.0f) {
                        angle += 360.0f;
                    }
                    if (angle > 360.0f) {
                        angle -= 360.0f;
                    }

                    moveTimer += dt;
                    tmp.set(speed, 0).rotate(angle);
                    position.mulAdd(tmp, dt);
                    if (position.dst(destination) < 120.0f && Math.abs(angleTo - angle) > 10) {
                        position.mulAdd(tmp, -dt);
                    }
                }
                break;
            case AI:
                break;
            default:
                break;
        }
        if (checkColllisionTanks()) {
//            System.out.println(checkColllisionTanks(position));
//            position = currPosition;
//            position.x = currPosition.x;
//            position.y = currPosition.y;
            position.set(currPosition.x, currPosition.y);

        }
        System.out.println(checkColllisionTanks());
        updateWeapon(dt);
        checkBounds();


    }

    public void updateWeapon(float dt) {
        if (weapon.getType() == Weapon.Type.HARVEST) {
            if (gc.getMap().getResourceCount(this) > 0) {
                int result = weapon.use(dt);
                if (result > -1) {
                    container += gc.getMap().harvestResource(this, result);
                }
            } else {
                weapon.reset();
            }
        }
    }

    public void checkBounds() {
        if (position.x < 40) {
            position.x = 40;
        }
        if (position.y < 40) {
            position.y = 40;
        }
        if (position.x > 1240) {
            position.x = 1240;
        }
        if (position.y > 680) {
            position.y = 680;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(textures[getCurrentFrameIndex()], position.x - 40, position.y - 40, 40, 40,
                80, 80, 1, 1, angle);
//        batch.draw(textures[getCurrentFrameIndex()], position.x , position.y , 40, 40,
//                80, 80, 1, 1, angle);
        if (weapon.getType() == Weapon.Type.HARVEST && weapon.getUsageTimePercentage() > 0.0f) {
            batch.setColor(0.2f, 0.2f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 32, position.y + 30, 64, 12);
            batch.setColor(1.0f, 1.0f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 30, position.y + 32,
                    60 * weapon.getUsageTimePercentage(), 8);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
//Отрисовка загруженности контейнера
        if (weapon.getType() == Weapon.Type.HARVEST) {
            batch.setColor(0.2f, 0.2f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 32, position.y - 40, 32, 20);
            batch.setColor(1.0f, 1.0f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 30, position.y - 40,
                    30 * container / containerMax, 18);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }


    public boolean checkColllisionTanks() {
        for (int i = 0; i < gc.getTanksController().activeList.size(); i++) {
            if (gc.getTanksController().activeList.get(i) == this) {
                continue;  //  если   сылка на этот же  объект, то   пропускаем   итерацию
            }
            if (  gc.getTanksController().activeList.get(i).position.dst(position) <= 80){
                return true;
            }
//            if ((int) (position.x - 80) / gc.getMap().CELL_SIZE ==
//                    (int) (gc.getTanksController().activeList.get(i).position.x  -80)/ gc.getMap().CELL_SIZE &&
//                    (int) (position.y - 80) / gc.getMap().CELL_SIZE ==
//                    (int) (gc.getTanksController().activeList.get(i).position.y - 80) / gc.getMap().CELL_SIZE) {
//                return true;
//            }
        }
        return false;
    }
}
