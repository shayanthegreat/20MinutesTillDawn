package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Texture texture = GameAssetManager.getInstance().getBulletTexture1();
    private Sprite sprite = new Sprite(texture);
    private int damage;
    private CollisionRect rect;
    private Vector2 position;
    private Vector2 direction;
    private float speed = 500f;
    public Bullet(int damage, float x, float y, Vector2 direction) {
        this.damage = damage;
        this.position = new Vector2(x, y);
        this.direction = direction.nor(); // normalized direction
        this.sprite.setPosition(x, y);
        this.sprite.setSize(10, 10);
        this.rect = new CollisionRect(x, y, 10, 10);
    }

    public void update() {
        float dx = direction.x * speed * Gdx.graphics.getDeltaTime();
        float dy = direction.y * speed * Gdx.graphics.getDeltaTime();

        sprite.translate(dx, dy);
        position.add(dx, dy);
        rect.setX(position.x);
        rect.setY(position.y);
        // Update the collision rectangle to match the new sprite position
        rect.move(sprite.getX(), sprite.getY());
    }

    public void monsterHit(Monster monster) {
        float x = (float) damage;
        monster.updateHealth(x);
    }
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public CollisionRect getRect() {
        return rect;
    }
}
