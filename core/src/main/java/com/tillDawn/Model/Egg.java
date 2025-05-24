package com.tillDawn.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Egg {
    private Sprite sprite;
    private CollisionRect rect;
    private boolean collected = false;

    public Egg(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.sprite.setPosition(x, y);
        this.sprite.setSize(18, 18);
        this.rect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void render(SpriteBatch batch) {
        if (!collected) {
            sprite.draw(batch);
        }
    }

    public CollisionRect getRect() {
        return rect;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
