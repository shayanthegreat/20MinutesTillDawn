package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Egg {
    private transient Sprite sprite;
    private transient CollisionRect rect;
    private boolean collected = false;

    private String spritePath; // Path used for deserialization

    public Egg() {} // Needed for JSON

    public Egg(String spritePath, float x, float y) {
        this.spritePath = spritePath;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
        this.sprite.setPosition(x, y);
        this.sprite.setSize(18, 18);
        this.rect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void initialize() {
        if (spritePath != null) {
            this.sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
            this.sprite.setPosition(sprite.getX(), sprite.getY());
            this.sprite.setSize(18, 18);
            this.rect = new CollisionRect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        }
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public void render(SpriteBatch batch) {
        if (sprite != null) {
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
