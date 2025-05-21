package com.tillDawn.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tree{
    Sprite sprite;
    float worldX;
    float worldY;
    private CollisionRect rect;
    public Tree(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.worldX = x;
        this.worldY = y;
        this.rect = new CollisionRect(x, y, 45, 45);
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

    public CollisionRect getRect() {
        return rect;
    }

    public void updateRect() {
        rect.move(worldX, worldY); // update based on world coordinates
    }
}
