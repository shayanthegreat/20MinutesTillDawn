package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tree{
    Sprite sprite;
    float worldX;
    float worldY;
    private CollisionRect rect;
    private float stateTime = 0f;
    private Animation<TextureRegion> animation;
    public Tree(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.worldX = x;
        this.worldY = y;
        this.rect = new CollisionRect(x, y, 140, 182);
        this.animation = new Animation(1f,
            new TextureRegion(new Texture(Gdx.files.internal("item/T_TreeMonster_0.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("item/T_TreeMonster_1.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("item/T_TreeMonster_2.png")))
            );

    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion frame = animation.getKeyFrame(stateTime, true);
        batch.draw(frame, worldX, worldY, 140, 182); // adjust size if needed
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
