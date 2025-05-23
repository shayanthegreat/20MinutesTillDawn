package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Monster {
    private float posX;
    private float posY;
    private float health;
    private boolean dead = false;
    private CollisionRect rect;
    private float time = 0;
    private float speed;
    private float damage;
    private float stateTime = 0f;

    private Animation<TextureRegion> animation;

    public Monster(float posX, float posY, CollisionRect rect, float time, MonsterType monsterType) {
        this.posX = posX;
        this.posY = posY;
        this.health = monsterType.getHp();
        this.rect = rect;
        this.time = time;
        this.speed = 15;
        this.damage = 10;
        this.animation = monsterType.getAnimation();
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
        }

        stateTime += deltaTime;
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

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, posX, posY, 40, 40); // 40x40 size as you used before
    }

    public boolean isDead() {
        return dead;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
}
