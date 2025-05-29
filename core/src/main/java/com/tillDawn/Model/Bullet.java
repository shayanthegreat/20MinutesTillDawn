package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Bullet {

    @JsonIgnore
    private Texture texture;

    @JsonIgnore
    private Sprite sprite;


    private int damage;
    private CollisionRect rect;
    private Vector2 position;
    private Vector2 direction;
    private float speed = 2000f;

    public Bullet() {
        this.position = new Vector2();
        this.direction = new Vector2();
    }

    public Bullet(int damage, float x, float y, Vector2 direction) {
        this.damage = damage;
        this.position = new Vector2(x, y);
        this.direction = direction.nor();
        this.rect = new CollisionRect(x, y, 15, 15);
        initialize();
    }

    public void initialize() {
        this.texture = GameAssetManager.getInstance().getBulletTexture1();
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(position.x, position.y);
        this.sprite.setSize(10, 10);
    }

    public void update() {
        float dx = direction.x * speed * Gdx.graphics.getDeltaTime();
        float dy = direction.y * speed * Gdx.graphics.getDeltaTime();

        sprite.translate(dx, dy);
        position.add(dx, dy);
        rect.setX(position.x);
        rect.setY(position.y);
        rect.move(sprite.getX(), sprite.getY());

        if (rect.collidesWith(App.getInstance().getCurrentPlayer().getRect())) {
            App.getInstance().getCurrentPlayer().updateHealth(-damage);
        }
    }

    public void monsterHit(Monster monster) {
        float x = damage;
        if (App.getInstance().getCurrentPlayer().isDamageBoosted()) {
            x += damage / 4f;
        }
        monster.updateHealth(x);

        // Apply knockback
        float knockbackForce = 3f; // Tune this value based on your game's scale
        monster.knockback(direction, knockbackForce);
    }


    public void draw(Batch batch) {
        if (sprite != null) sprite.draw(batch);
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
