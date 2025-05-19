package com.tillDawn.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tree{
    Sprite sprite;
    float worldX;
    float worldY;

    public Tree(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.worldX = x;
        this.worldY = y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getWorldX() {
        return worldX;
    }

    public float getWorldY() {
        return worldY;
    }
}
