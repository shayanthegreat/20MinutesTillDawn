package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tillDawn.Controller.WorldController;

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
    private float shootTimer = 0f;
    private MonsterType monsterType;
    private Animation<TextureRegion> animation;
    private float dashCooldown = 5f;
    private float dashTimer = 0f;
    private boolean isDashing = false;
    private float dashDuration = 0.5f;
    private float dashTimeLeft = 0f;
    private float dashDirX = 0;
    private float dashDirY = 0;
    private Animation<TextureRegion> normalAnimation;
    private Animation<TextureRegion> dashAnimation;
    public Monster(float posX, float posY, float time, MonsterType monsterType) {
        this.posX = posX;
        this.posY = posY;
        this.health = monsterType.getHp();
        this.rect = new CollisionRect(posX, posY, monsterType.getWidth(), monsterType.getHeight());
        this.time = time;
        this.speed = 15;
        this.damage = 10;
        this.monsterType = monsterType;
        this.animation = monsterType.getAnimation();
        if(monsterType == MonsterType.Shub){
            this.speed = 20;
            normalAnimation = new Animation<>(1f,
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_0.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_2.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_3.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_4.png"))));
            dashAnimation = new Animation<>(0.3f,
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_5.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_6.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_7.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_8.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_9.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_10.png"))));
        }
    }

    public void update(float deltaTime, float playerX, float playerY) {
        stateTime += deltaTime;

        if (monsterType == MonsterType.Shub) {
            dashTimer += deltaTime;

            if (!isDashing && dashTimer >= dashCooldown) {
                isDashing = true;
                dashTimeLeft = dashDuration;
                dashTimer = 0f;

                // Calculate and fix dash direction once here
                dashDirX = playerX - posX;
                dashDirY = playerY - posY;
                float length = (float) Math.sqrt(dashDirX * dashDirX + dashDirY * dashDirY);
                if (length != 0) {
                    dashDirX /= length;
                    dashDirY /= length;
                }
            }

            if (isDashing) {
                dashTimeLeft -= deltaTime;

                // Move in fixed dash direction
                posX += dashDirX * speed * 50f * deltaTime;
                posY += dashDirY * speed * 50f * deltaTime;

                if (rect != null) {
                    rect.move(posX, posY);
                }

                if (dashTimeLeft <= 0f) {
                    isDashing = false;
                }

                return;  // Skip normal movement while dashing
            }
        }

        // Default movement (not dashing)
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
    }

    public void damage(Player player) {
        player.updateHealth(-damage);
    }

    public void updateHealth(float x) {
        this.health -= x;
        if (this.health <= 0) {
            if(monsterType == MonsterType.EyeBat){
                WorldController.getInstance().addEgg(new Egg(
                    new Sprite(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_EM.png"))),
                    posX, posY
                ));
            }
            else if(monsterType == MonsterType.Tentacle){
                WorldController.getInstance().addEgg(new Egg(
                    new Sprite(new Texture(Gdx.files.internal("tentacle/BrainMonster_Em.png"))),
                    posX, posY
                ));
            }

            dead = true;
        }
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        if(monsterType == MonsterType.Shub){
            if(isDashing){
                currentFrame = dashAnimation.getKeyFrame(stateTime, true);
            }
            else{
                currentFrame = normalAnimation.getKeyFrame(stateTime, true);
            }
        }
        if (monsterType == MonsterType.Shub && isDashing) {
            batch.setColor(1f, 0.3f, 0.3f, 1f); // Red tint
        }

        batch.draw(currentFrame, posX, posY, monsterType.getWidth(), monsterType.getHeight());

        if (monsterType == MonsterType.Shub && isDashing) {
            batch.setColor(1f, 1f, 1f, 1f); // Reset color
        }
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
    public void updateShootTimer(float delta) {
        shootTimer += delta;
    }

    public void resetShootTimer() {
        shootTimer = 0f;
    }

    public boolean canShoot() {
        return shootTimer >= 3f;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }
}
