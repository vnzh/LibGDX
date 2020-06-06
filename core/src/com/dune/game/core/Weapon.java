package com.dune.game.core;

public class Weapon {
    private float period;
    private float time;
    private float angle;
    private int power;
    private Type typeWeapon;


    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getUsageTimePercentage() {
        return time / period;
    }

    public Weapon(Type weapon, float period, int power) {
        this.typeWeapon = weapon;
        this.period = period;
        this.power = power;
    }

    public void reset() {
        time = 0.0f;
    }

    public int use(float dt) {
        time += dt;
        if (time > period) {
            time = 0.0f;
            return power;
        }
        return -1;
    }

    public enum Type {
        HARVEST, GROUND
    }

    public Type getType() {
        return typeWeapon;
    }
}