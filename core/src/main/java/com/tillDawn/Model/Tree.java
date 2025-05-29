package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tree {
    public float worldX;
    public float worldY;

    private transient Sprite sprite;
    private transient CollisionRect rect;
    private transient Animation<TextureRegion> animation;
    private float stateTime = 0f;

    public Tree() {
        // Required for JSON deserialization
    }

    public Tree(Sprite sprite, float x, float y) {
        this.worldX = x;
        this.worldY = y;
        this.sprite = sprite;
        initialize();
    }

    public void initialize() {
        rect = new CollisionRect(worldX, worldY, 140, 182);
        animation = new Animation<>(1f,
            new TextureRegion(new Texture(Gdx.files.internal("item/T_TreeMonster_0.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("item/T_TreeMonster_1.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("item/T_TreeMonster_2.png")))
        );
        sprite = new Sprite(animation.getKeyFrame(0f));
        sprite.setPosition(worldX, worldY);
        sprite.setSize(140, 182);
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void draw(SpriteBatch batch) {
        if (animation == null) return; // avoid null if not initialized
        TextureRegion frame = animation.getKeyFrame(stateTime, true);
        batch.draw(frame, worldX, worldY, 140, 182);
    }

    public float getWorldX() {
        return worldX;
    }

    public float getWorldY() {
        return worldY;
    }

    @JsonIgnore
    public Sprite getSprite() { return sprite; }

    @JsonIgnore
    public CollisionRect getRect() { return rect; }
    public void updateRect() {
        if (rect != null)
            rect.move(worldX, worldY);
    }
}
