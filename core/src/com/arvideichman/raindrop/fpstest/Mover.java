package com.arvideichman.raindrop.fpstest;

import com.badlogic.gdx.Gdx;

public class Mover {
    private float xVelocity;
    private float yVelocity;
    private float x;
    private float y;

    public Mover() {
        this.x = (float) Math.random() * (float) Gdx.graphics.getWidth();
        this.y = (float) Math.random() * (float) Gdx.graphics.getHeight();
        this.xVelocity = ((float) Math.random() - 0.5f) * 100;
        this.yVelocity = ((float) Math.random() - 0.5f) * 100;
    }
    public float getxVelocity() {
        return xVelocity;
    }
    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }
    public float getyVelocity() {
        return yVelocity;
    }
    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
}
