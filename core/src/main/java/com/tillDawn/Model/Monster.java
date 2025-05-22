package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Monster {
    private Texture texture = new Texture(Gdx.files.internal("monster.png"));
    private Sprite sprite;
    private float posX = 0;
    private float posY = 0;
    private float health = 100;
    private boolean dead = false;
    private CollisionRect rect;
    private float time = 0;
    private float speed;
    private float damage;

    public Monster(float posX, float posY, float health, CollisionRect rect, float time, float speed, float damage) {
        this.posX = posX;
        this.posY = posY;
        this.health = health;
        this.rect = rect;
        this.time = time;
        this.speed = speed;
        this.damage = damage;
        this.sprite = new Sprite(texture);
        this.sprite.setSize(40, 40);
    }

    public void update(float deltaTime, float playerX, float playerY) {
        float dirX = playerX - posX;
        float dirY = playerY - posY;
        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY);

        if (length != 0) {
            dirX /= length;
            dirY /= length;

            posX += dirX * speed * deltaTime;
            posY += dirY * speed * deltaTime;

            if (rect != null) {
                rect.move(posX, posY);
            }

            sprite.setPosition(posX, posY);
        }
    }

    public void damage(Player player) {
        player.updateHealth(-(damage / 60));
    }

    public void updateHealth(float x) {
        this.health -= x;
        if (this.health <= 0) {
            dead = true;
        }
    }
    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isDead() {
        return dead;
    }


}
